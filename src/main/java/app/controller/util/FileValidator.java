package app.controller.util;

import app.controller.pojo.FileUpload;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
