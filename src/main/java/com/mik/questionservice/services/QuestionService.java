package com.mik.questionservice.services;

import com.mik.questionservice.models.Question;
import com.mik.questionservice.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // get all questions
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    // filter questions using categories
    public ResponseEntity<List<Question>> findQuestionsByCategories(List<String> categories) {
        List<Question> questions = questionRepository.findByCategoryIn(categories);

        if(!questions.isEmpty()) {
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // find question using questionId
    public ResponseEntity<Question> getQuestionById(int questionId) {
        return questionRepository.findById(questionId)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Question>> getQuestionsByIds(@RequestParam List<Integer> questionIds) {
        List<Question> questions = questionRepository.findAllById(questionIds);

        if(!questions.isEmpty()) {
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // add a question
    public ResponseEntity<Question> addQuestion(Question question) {
        return questionRepository.findById(question.getId())
                .map(_ -> new ResponseEntity<Question>(HttpStatus.CONFLICT))
                .orElseGet(() -> new ResponseEntity<>(questionRepository.save(question), HttpStatus.CREATED));
    }

    // update a question if it exists
    public ResponseEntity<Question> updateQuestion(Question question) {
        Optional<Question> questionOptional = questionRepository.findById(question.getId());

        if(questionOptional.isPresent()) {
            questionRepository.save(question);
            return new ResponseEntity<>(question, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete a question if it exists
    public ResponseEntity<Question> deleteQuestion(int questionId) {
        Optional<Question> question = questionRepository.findById(questionId);

        if (question.isPresent()) {
            questionRepository.delete(question.get());
            return new ResponseEntity<>(question.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // generate random questionIds
    public ResponseEntity<List<Integer>> generateQuestionIds(List<String> categories, int count) {
        List<Question> questions = questionRepository.findByCategoryIn(categories);
        Collections.shuffle(questions);

        return new ResponseEntity<>(questions.stream().limit(count).map(Question::getId).collect(Collectors.toList())
                , HttpStatus.OK);

    }

}
