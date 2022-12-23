package br.com.alura.api.forum.service.interfaces;

import br.com.alura.api.forum.dto.AddedTopicDTO;
import br.com.alura.api.forum.dto.InsertTopicDTO;
import br.com.alura.api.forum.dto.TopicDetailsDTO;
import br.com.alura.api.forum.dto.UpdateTopicDTO;

public interface ITopicService {

    AddedTopicDTO insert(InsertTopicDTO insertTopicDTO);

    TopicDetailsDTO update(String id, UpdateTopicDTO updateTopicDTO);
}
