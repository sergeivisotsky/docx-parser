package app.controller;

import app.controller.pojo.FileUpload;
import app.model.service.repos.EmployeeService;
import app.model.xmlparser.parser.Parser;
import app.model.xmlparser.parser.XmlExtraction;
import app.model.xmlparser.util.ParserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
public class FileLoaderController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private Parser parser;

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
                               BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("upload_page");
        }

        return new ModelAndView("success", "fileName", processUpload(fileUpload));
    }

    // File upload processing method
    private String processUpload(FileUpload fileUpload) throws IOException {
        String fileName;

        CommonsMultipartFile multipartFile = fileUpload.getFile();
        FileCopyUtils.copy(multipartFile.getBytes(),
                new File(ParserConstants.UPL_DIR + multipartFile.getOriginalFilename()));
        fileName = multipartFile.getOriginalFilename();

        parser.setXmlExtractionService(new XmlExtraction(multipartFile));
        employeeService.readEmployeeData();

        return fileName;
    }
}