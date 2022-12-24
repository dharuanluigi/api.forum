package br.com.alura.api.forum.service;

import br.com.alura.api.forum.dto.AddedTopicDTO;
import br.com.alura.api.forum.dto.InsertTopicDTO;
import br.com.alura.api.forum.dto.TopicDetailsDTO;
import br.com.alura.api.forum.dto.UpdateTopicDTO;
import br.com.alura.api.forum.entity.Topic;
import br.com.alura.api.forum.exceptions.UpdateForbiddenException;
import br.com.alura.api.forum.repository.CourseRepository;
import br.com.alura.api.forum.repository.TopicRepository;
import br.com.alura.api.forum.repository.UserRepository;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import br.com.alura.api.forum.service.interfaces.ITopicService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService implements ITopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest requestHttp;

    @Autowired
    private ITokenService tokenService;

    @Override
    public AddedTopicDTO insert(InsertTopicDTO insertTopicDTO) {
        var course = courseRepository.findByName(insertTopicDTO.courseName());
        if (course != null) {
            var token = requestHttp.getHeader("Authorization");
            var userEmail = tokenService.validate(token);
            var user = userRepository.findByEmail(userEmail);
            var topic = new Topic(insertTopicDTO.title(), insertTopicDTO.message(), course, user);
            topicRepository.save(topic);
            return new AddedTopicDTO(topic);
        }

        throw new EntityNotFoundException("Course not found");
    }

    @Override
    @Transactional
    public TopicDetailsDTO update(String id, UpdateTopicDTO updateTopicDTO) {
        var topic = tryGetTopic(id);
        if (isCurrentUserOwner(topic)) {
            topic.updateData(updateTopicDTO);
            return new TopicDetailsDTO(topic);
        }
        throw new UpdateForbiddenException("Current user is not the owner of topic, just owners can update it own topics");
    }

    @Override
    @Transactional
    public void close(String id) {
        var topic = tryGetTopic(id);

        if (!isCurrentUserOwner(topic) && !isCurrentUserModerator(topic)) {
            throw new UpdateForbiddenException("Just Moderators or topic owners can close your own topics");
        }

        topic.close();
    }

    private Topic tryGetTopic(String id) {
        var optional = topicRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Topic was not found");
        }
        return optional.get();
    }

    private Boolean isCurrentUserOwner(Topic topic) {
        return topic.isUserOwner(requestHttp, tokenService);
    }

    private Boolean isCurrentUserModerator(Topic topic) {
        return topic.isUserModerator(requestHttp, tokenService);
    }
}
