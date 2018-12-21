package org.sergei.parser.controller;

import org.sergei.parser.ftp.FileOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Sergei Visotsky
 */
@Controller
public class FileController {

    private final FileOperations fileOperations;

    @Autowired
    public FileController(FileOperations fileOperations) {
        this.fileOperations = fileOperations;
    }

    // Method to display start page
    @GetMapping("/")
    public String shouldFileLoader() {
        return "upload_page";
    }

    // Servlet form listener
    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("file") CommonsMultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) {
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
        }

        return new ModelAndView("uploaded", "fileName", processUpload(multipartFile));
    }

    // Servlet download process listener
    @GetMapping("/download")
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
    private String processUpload(CommonsMultipartFile commonsMultipartFile) {
        String fileName;

        fileOperations.setMultipartFile(commonsMultipartFile);
        fileOperations.serverUpload();
        fileName = commonsMultipartFile.getOriginalFilename();

        return fileName;
    }
}