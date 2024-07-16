package com.example.forum.service;

import com.example.forum.entity.Response;
import com.example.forum.entity.Topic;
import com.example.forum.repository.ResponseRepository;
import com.example.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ResponseRepository responseRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    public Response createResponse(Long topicId, Response response) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent()) {
            response.setTopic(topic.get());
            return responseRepository.save(response);
        } else {
            throw new RuntimeException("Topic not found");
        }
    }

    public List<Response> getResponsesByTopicId(Long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent()) {
            return topic.get().getResponses();
        } else {
            throw new RuntimeException("Topic not found");
        }
    }
}
