package com.mik.questionservice.controllers;

import com.mik.questionservice.models.Question;
import com.mik.questionservice.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // get all questions
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // filter using categories
    @GetMapping("/")
    public ResponseEntity<List<Question>> getQuestionsByCategories(@RequestParam List<String> categories) {
        return questionService.findQuestionsByCategories(categories);
    }

    // find using questionId
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int questionId) {
        return questionService.getQuestionById(questionId);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getQuestionsByIds(@RequestParam List<Integer> questionIds) {
        return questionService.getQuestionsByIds(questionIds);
    }

    // add a question
    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // update a question if it exists
    @PutMapping("/update")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    // delete a question if it exists
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable int questionId) {
        return questionService.deleteQuestion(questionId);
    }

    // generate random questionIds
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> generateQuestionIds(@RequestParam List<String> categories, @RequestParam int count) {
        return questionService.generateQuestionIds(categories, count);
    }

}
