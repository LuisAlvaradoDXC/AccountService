package com.AccountService.constraint;

import org.springframework.validation.annotation.Validated;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {DateParser.Validator.class})
public @interface DateParser {
    String message() default "El formato de la fecha es incorrecto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<DateParser, Date> {
        private static Pattern pattern;
        private static Matcher matcher;

        // Expresión regular para validar la fecha en formato dd/mm/yyyy
        private static final String DATE_PATTERN =
                "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$";

        @Override
        public void initialize(DateParser constraintAnnotation) {
            pattern = Pattern.compile(DATE_PATTERN);
        }

        @Override
        public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = sdf.format(date);
            matcher = pattern.matcher(dateString);
            return matcher.matches();
        }
    }
}
