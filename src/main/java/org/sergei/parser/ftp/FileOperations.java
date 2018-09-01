package org.sergei.parser.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.sergei.parser.xmlparser.util.ParsingWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

@Component
public class FileOperations {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${ftp.server}")
    private String SERVER;

    @Value("${ftp.port}")
    private int PORT;

    @Value("${ftp.username}")
    private String USERNAME;

    @Value("${ftp.password}")
    private String PASSWORD;

    @Autowired
    private ParsingWrapper parsingWrapper;

    private CommonsMultipartFile multipartFile;

    public void setMultipartFile(CommonsMultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    private FTPClient ftpClient = new FTPClient();

    // Method to perform file download
    public void serverUpload() {
        try {
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(multipartFile.getOriginalFilename());
            multipartFile.transferTo(localFile);
            String remoteFile = multipartFile.getOriginalFilename();
            InputStream inputStream = new FileInputStream(localFile);
            boolean done = ftpClient.storeFile(remoteFile, inputStream);

            if (done) {
                LOGGER.info("File uploaded to the server");
            } else {
                LOGGER.error("File not uploaded to the server");
            }

            inputStream.close();
            parsingWrapper.documentServicesCaller(localFile);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    // Method to process file upload from the server
    public void serverDownload() {
        try {
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFile = "/template.docx";
            File local = new File("D:/Users/Sergei/Documents/JavaProjects/docxParserServlet/" +
                    "src/main/resources/static" + remoteFile);

            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(local));
            boolean done = ftpClient.retrieveFile(remoteFile, outputStream);

            if (done) {
                LOGGER.info("File downloaded from the server");
            } else {
                LOGGER.error("Failed to download file from the server");
            }

            outputStream.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
