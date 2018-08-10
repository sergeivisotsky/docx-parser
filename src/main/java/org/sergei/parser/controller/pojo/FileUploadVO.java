package org.sergei.parser.controller.pojo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class FileUploadVO {
    private CommonsMultipartFile file;

    public FileUploadVO(CommonsMultipartFile file) {
        this.file = file;
    }

    public FileUploadVO() {

    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
}
