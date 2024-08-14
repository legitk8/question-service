package com.mik.questionservice.aspects;

import com.mik.questionservice.exception.InvalidRequestBodyException;
import com.mik.questionservice.models.Question;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {

    @Before("execution(* com.mik.questionservice.controllers.QuestionController.*(..)) " +
            "&& args(question, ..)")
    public void validateQuestionDTO(Question question) {
        if(question.getId() <= 0)
            throw new InvalidRequestBodyException("Question ID must be greater than 0");

        if(question.getQuestionTitle() == null || question.getQuestionTitle().trim().isEmpty())
            throw new InvalidRequestBodyException("Question Title cannot be blank");

        if(question.getOption1() == null || question.getOption1().trim().isEmpty())
            throw new InvalidRequestBodyException("Option 1 cannot be blank");

        if(question.getOption2() == null || question.getOption2().trim().isEmpty())
            throw new InvalidRequestBodyException("Option 2 cannot be blank");

        if(question.getOption3() == null || question.getOption3().trim().isEmpty())
            throw new InvalidRequestBodyException("Option 3 cannot be blank");

        if(question.getOption4() == null || question.getOption4().trim().isEmpty())
            throw new InvalidRequestBodyException("Option 4 cannot be blank");

        if(question.getCorrectAnswer() == null || question.getCorrectAnswer().trim().isEmpty())
            throw new InvalidRequestBodyException("Correct answer cannot be blank");

        if(question.getCategory() == null || question.getCategory().trim().isEmpty())
            throw new InvalidRequestBodyException("Category cannot be blank");
    }

    @Before("execution(* com.mik.questionservice.controllers.QuestionController.*(..)) " +
            "&& args(questionId, ..)")
    public void validateQuestionId(int questionId) {
        if(questionId <= 0)
            throw new InvalidRequestBodyException("Question ID must be greater than 0");
    }

}
