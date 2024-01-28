package com.akshat.learning.docker.server.endpoint;

import com.akshat.learning.docker.server.entity.Feedback;
import com.akshat.learning.docker.server.entity.PublishStatus;
import com.akshat.learning.docker.server.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/")
public class Endpoints {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ModelAndView displayIndex(Model model) {
        model.addAttribute("feedback", Feedback.builder().build());
        return new ModelAndView("index");
    }
    @PostMapping("/feedback")
    public ModelAndView postFeedback(@ModelAttribute Feedback feedback, Model model) throws IOException {
        feedback.setTime(LocalDateTime.now());
        System.out.println("Submitted feedback:"+ feedback);
        feedbackService.saveFeedback(feedback);
        return new ModelAndView("submitted");
    }

    @PostMapping("/publish")
    public ModelAndView publishFeedback(Model model) {
        System.out.println("Publishing All Feedbacks");
        PublishStatus publishStatus;
        try {
            feedbackService.publishFeedbacks();
            System.out.println("Successfully Published All Feedbacks");
            publishStatus = PublishStatus.builder().status("Success").build();
        } catch (Exception e) {
            System.out.println("Encountered Error While Publishing All Feedbacks:\n"+ e);
            publishStatus = PublishStatus.builder().status("Fail").errorMessage(e.getMessage()).build();
        }
        model.addAttribute("status", publishStatus);
        return new ModelAndView("published");
    }
}
