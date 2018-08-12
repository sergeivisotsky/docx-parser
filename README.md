# docxParserServlet

## Task description
* Servlet with form to upload file should be created
* After .docx file is uploaded it should be stored on the server using FTP
* During the upload document.xml file should be extracted from the .docx file
* After document.xml should be parsed in memory to extract all the necessary at put it to the relational database
* It should be able to download data from the server via FTP (UNDER CONSTRUCTION)

## Specification
Sometimes it is required to parse MS Office documents word documents more often to put data into a database.

Every word document is a .docx file which is an archieve consisting XML files.

A lot of API exists to parse word based documents in Java as an example Apache POI and docx4j

But in this case xPath is used to parse XML due to every XML node needs to be put into a database in specific column but Apache POI or docx4j parses by paragraph.

## Program setup on the local machine
* Open app.properties file
* In this file setup your FTP and database credentials
* Open file log4j.properties
* Change path to your log file "log4j.appender.file.File"

INFO: Example .docx to parse as well as unarchived .docx are located in /resources/docs folder.