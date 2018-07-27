package app.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class FileUpload {
    private CommonsMultipartFile file;

    public FileUpload(CommonsMultipartFile file) {
        this.file = file;
    }

    public FileUpload() {

    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
}
