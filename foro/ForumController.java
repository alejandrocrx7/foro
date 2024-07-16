package com.example.forum.controller;

import com.example.forum.entity.Response;
import com.example.forum.entity.Topic;
import com.example.forum.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return forumService.getAllTopics();
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Optional<Topic> topic = forumService.getTopicById(id);
        return topic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/topics")
    public Topic createTopic(@RequestBody Topic topic) {
        return forumService.createTopic(topic);
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        forumService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/topics/{topicId}/responses")
    public ResponseEntity<Response> createResponse(@PathVariable Long topicId, @RequestBody Response response) {
        try {
            Response savedResponse = forumService.createResponse(topicId, response);
            return ResponseEntity.ok(savedResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/topics/{topicId}/responses")
    public ResponseEntity<List<Response>> getResponsesByTopicId(@PathVariable Long topicId) {
        try {
            List<Response> responses = forumService.getResponsesByTopicId(topicId);
            return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
