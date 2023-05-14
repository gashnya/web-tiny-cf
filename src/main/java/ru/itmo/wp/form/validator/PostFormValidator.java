package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.itmo.wp.form.PostForm;

@Component
public class PostFormValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return PostForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        PostForm postForm = (PostForm) target;
        if (postForm.getTags().split("\\s+").length > 5) {
            errors.rejectValue("tags", "tags.invalid-number-of-tags", "Maximum 5 tags by post are allowed");
        }
    }
}
