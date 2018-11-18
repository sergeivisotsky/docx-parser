package org.sergei.parser.validators;

import org.sergei.parser.model.FileUpload;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Sergei Visotsky, 2018
 * <pre>
 *    Class to perform file validation so that form wasn't empty
 * </pre>
 */
@Component
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return FileUpload.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FileUpload fileUpload = (FileUpload) target;

        CommonsMultipartFile commonsMultipartFile = fileUpload.getFile();

        if (commonsMultipartFile.getSize() == 0) {
            errors.reject("file", "missing.file");
        }
    }
}
