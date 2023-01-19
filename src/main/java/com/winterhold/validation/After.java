package com.winterhold.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = AfterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Afters.class)
public @interface After {

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message();
    String previousDateField();
    String subsequentDateField();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        After[] value();
    }
}
