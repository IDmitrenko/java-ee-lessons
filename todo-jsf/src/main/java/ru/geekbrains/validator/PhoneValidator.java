package ru.geekbrains.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("phoneValidator")
public class PhoneValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(PhoneValidator.class);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        logger.info("Validating submitted phone - " + value.toString());
        Pattern pattern = Pattern.compile("^\\+[1-9]?[0-9]\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$");
        Matcher matcher = pattern.matcher(value.toString());

        if (!matcher.matches()) {
            FacesMessage msg =
                    new FacesMessage(" Phone validation failed.",
                            "Please provide Phone number in this format: +7(495)618-01-09 или +38(044)292-67-39");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }
    }
}
