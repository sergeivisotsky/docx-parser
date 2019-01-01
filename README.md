# docx-parser
[![Build Status](https://travis-ci.com/sergeivisotsky/docx-parser.svg?branch=master)](https://travis-ci.com/sergeivisotsky/docx-parser)

## Task description
* Servlet with form to upload file should be created
* After .docx file is uploaded it should be stored on the server using FTP
* During the upload document.xml file should be extracted from the .docx file
* After document.xml should be parsed in memory to extract all the necessary at put it to the relational database
* It should be able to download data from the server via FTP

## Specification
Sometimes it is required to parse MS Office Word documents to put data into the database.

Every word document is a .docx file which is an archieve consisting XML files.

A lot of API exists to parse word based documents in Java as an example Apache POI and docx4j

But in this case no tool can be used and consequently xPath is used to parse XML due to every XML node needs to be put into a database in specific column but Apache POI or docx4j parses by paragraph.

## Technologies
* Java 8
* Spring Boot 2
* JPA
* Thymeleaf
* Apache Maven
* xPath
* PostgreSQL

## Program setup on the local machine
* JDK & JRE 1.8 required
* Add you database dependency in pom.xml example for PostgreSQL:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```
* Open app.properties file
* In this file setup your FTP and database credentials
* Change property's `spring.datasource.driverClassName` value to your database driver class name
* Change property's `spring.datasource.url` value to your database name and port
* Next set your database username and password
* Change property's `spring.jpa.properties.hibernate.hbm2ddl.auto` value to create-drop if you want to drop the table after program is stopped of update if not
* Change property's `spring.jpa.properties.hibernate.dialect` value to your database SQL dialect
* To run project perform `mvn spring-boot:run`

INFO: Example .docx to parse as well as unarchived .docx are located in /resources/static/docs folder and template.docx file should be located on the FTP server.