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
* Java 7
* Apache Tomcat 7+
* Apache Maven
* Spring MVC
* xPath
* PostgreSQL

## Program setup on the local machine
* JDK & JRE 1.8 required
* Add you database dependency in pom.xml example for PostgreSQL:
```xml
<dependency>
    <groupId>postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>8.3-606.jdbc4</version>
</dependency>
```
1. Open app.properties file
2. In this file setup your FTP and database credentials
3. Change property's `spring.datasource.driverClassName` value to your database driver class name
4. Change property's `spring.datasource.url` value to your database name and port
5. Next set your database username and password
6. Change property's `spring.jpa.hibernate.ddl-auto` value to create-drop if you want to drop the table after program is stopped of update if not
7. Change property's `spring.jpa.hibernate.dialect` value to your database SQL dialect
8. Open file log4j.properties
9. Change path to your log file "log4j.appender.file.File"
10. To create executable .war achieve perform `mvn clean package` command 

INFO: Example .docx to parse as well as unarchived .docx are located in /resources/static/docs folder and template.docx file should be located on the FTP server.