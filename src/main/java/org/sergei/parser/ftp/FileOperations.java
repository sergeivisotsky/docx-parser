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

/**
 * @author Sergei Visotsky
 */
@Component
public class FileOperations {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileOperations.class);

    @Value("${ftp.server}")
    private String SERVER;

    @Value("${ftp.port}")
    private int PORT;

    @Value("${ftp.username}")
    private String USERNAME;

    @Value("${ftp.password}")
    private String PASSWORD;

    private final FTPClient ftpClient;
    private final ParsingWrapper parsingWrapper;

    @Autowired
    public FileOperations(FTPClient ftpClient, ParsingWrapper parsingWrapper) {
        this.ftpClient = ftpClient;
        this.parsingWrapper = parsingWrapper;
    }

    /**
     * Method to upload file on the server
     *
     * @param multipartFile file to be uploaded as an input argument
     */
    public void serverUpload(CommonsMultipartFile multipartFile) {
        try {
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(multipartFile.getOriginalFilename());
            // Multipart file transfer to a regular file
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

    /**
     * Method to download file from the server
     */
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
