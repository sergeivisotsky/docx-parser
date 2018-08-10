package org.sergei.parser.controller;

import org.sergei.parser.controller.pojo.FileUploadVO;
import org.sergei.parser.controller.util.FileValidator;
import org.sergei.parser.model.xmlparser.fileuploader.FileOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {

    @Autowired
    private FileValidator fileValidator;

    @Autowired
    private FileOperations fileOperations;

    // Method to display start page
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView shouldFileLoader() {
        ModelAndView model = new ModelAndView("upload_page");
        model.addObject("formUpload", new FileUploadVO());

        return model;
    }

    // Servlet form listener
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView upload(@ModelAttribute(value = "formUpload") FileUploadVO fileUploadVO,
                               BindingResult result) {
        fileValidator.validate(fileUploadVO, result);

        if (result.hasErrors()) {
            return new ModelAndView("upload_page");
        }

        return new ModelAndView("uploaded", "fileName", processUpload(fileUploadVO));
    }

    // File upload processing method
    private String processUpload(FileUploadVO fileUploadVO) {
        String fileName;

        CommonsMultipartFile multipartFile = fileUploadVO.getFile();

        fileOperations.setMultipartFile(multipartFile);
        fileOperations.serverUpload();
        fileName = multipartFile.getOriginalFilename();

        return fileName;
    }
}