package br.com.alura.api.forum.service.interfaces;

import br.com.alura.api.forum.dto.AddedTopicDTO;
import br.com.alura.api.forum.dto.InsertTopicDTO;

public interface ITopicService {

    AddedTopicDTO insert(InsertTopicDTO insertTopicDTO);
}
