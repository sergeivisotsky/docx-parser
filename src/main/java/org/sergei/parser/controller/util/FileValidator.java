package org.sergei.parser.controller.util;

import org.sergei.parser.controller.pojo.FileUploadVO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return FileUploadVO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FileUploadVO fileUploadVO = (FileUploadVO) target;

        CommonsMultipartFile commonsMultipartFile = fileUploadVO.getFile();

        if (commonsMultipartFile.getSize() == 0) {
            errors.reject("file", "missing.file");
        }
    }
}
