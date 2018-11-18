package org.sergei.parser.controller;

import org.sergei.parser.ftp.FileOperations;
import org.sergei.parser.model.FileUpload;
import org.sergei.parser.validators.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Sergei Visotsky, 2018
 */
@Controller
public class FileController {

    private final FileValidator fileValidator;
    private final FileOperations fileOperations;

    @Autowired
    public FileController(FileValidator fileValidator, FileOperations fileOperations) {
        this.fileValidator = fileValidator;
        this.fileOperations = fileOperations;
    }

    // Method to display start page
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView shouldFileLoader() {
        ModelAndView model = new ModelAndView("upload_page");
        model.addObject("formUpload", new FileUpload());

        return model;
    }

    // Servlet form listener
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView upload(@ModelAttribute(value = "formUpload") FileUpload fileUpload,
                               BindingResult result) {
        fileValidator.validate(fileUpload, result);

        if (result.hasErrors()) {
            return new ModelAndView("upload_page");
        }

        return new ModelAndView("uploaded", "fileName", processUpload(fileUpload));
    }

    // Servlet download process listener
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> download() throws IOException {
        fileOperations.serverDownload();
        Path path = Paths.get("D:/Users/Sergei/Documents/JavaProjects/docx-parser/" +
                "src/main/resources/static/template.docx");
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(data.length)
                .body(resource);
    }

    // File upload processing method
    private String processUpload(FileUpload fileUpload) {
        String fileName;

        CommonsMultipartFile multipartFile = fileUpload.getFile();

        fileOperations.setMultipartFile(multipartFile);
        fileOperations.serverUpload();
        fileName = multipartFile.getOriginalFilename();

        return fileName;
    }
}