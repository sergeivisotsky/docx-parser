/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

    /**
     * Method to display start page
     *
     * @return start page view
     */
    @GetMapping("/")
    public String shouldFileLoader() {
        return "upload_page";
    }

    /**
     * Method to upload file on the server
     *
     * @param multipartFile      file to be uploaded given as an input argument
     * @param redirectAttributes parameter to be invoked in case of failures
     * @return view of success
     */
    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("file") CommonsMultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) {
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
        }

        return new ModelAndView("uploaded", "fileName", processUpload(multipartFile));
    }

    /**
     * Method to process file download
     *
     * @return File download
     * @throws IOException input-output exception
     */
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

    /**
     * File upload processing method
     *
     * @param commonsMultipartFile file to be uploaded
     * @return name of the saved file
     */
    private String processUpload(CommonsMultipartFile commonsMultipartFile) {
        String fileName;

        fileOperations.serverUpload(commonsMultipartFile);
        fileName = commonsMultipartFile.getOriginalFilename();

        return fileName;
    }
}