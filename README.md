# docxParserServlet
Sometimes it is required to parse MS Office documents word documents more often to put data into a database.

Every word document is a .docx file which is an archieve consisting XML files.

A lot of API exists to parse word based documents in Java as an example Apache POI and docx4j

But in this case xPath is used to parse XML due to every XML node needs to be put into a database in specific column but Apache POI or docx4j parses by paragraph.
