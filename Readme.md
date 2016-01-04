# Pdf Generator microservice for yaas

**pdfgen** is a restful api that generates pdf files from stored jasper templates in a mongodb database. It is based on yaas service SDK, and offers resources to store jasper templates and generate pdf files : 

## Overview :

 The api stores jrxml (jasperreport file) templates in a  mongodb database. The templates are used later in the /generate resource to generate
 pdf files using a json input that represents jasperreport parameters and fields.


## Resources
 - /pdfgen/templates : to handle crud operations on templates : 
    - Get all templates
    - Create a template
    - Store a jrxml file with the template
    - Delete a template
    - Update a template
    
 - /pdfgen/generate : to generate pdf files
    - Generate as many pdf report as we want from a json input and a stored jrxml file in DB

## Installation

To install pdfgen in localhost, you must follow the steps:

  - prerequisites : install maven and run a mongodb server.
(default configuration for mongodb are in  `default.properties` file :
    `MONGODB_HOST=localhost
    MONGODB_PORT=27017
    MONGODB_DBNAME=pdfgenDB`)
  - download pdfgen repository and run `mvn clean install` in the pdfgen folder
  - run the jetty server : `mvn:jetty:run`


## Examples
In the following examples We'll use curl to perform Rest operations. (You can use Api console to view results )

### Simple static template :

 - Create template1 
 
    `curl -i -H  "Content-Type: application/json" -X POST -d '{ "title":"template1","description":"SimpleTemplate" }'  http://localhost:8080/templates`
    
    (the id of the created template is returned in the http header `Location` ) 
    
 - add the jrxml file ( see: pdfgen/src/test/resources/jrxmls/templateFile1.jrxml) to the template : 
 
    `curl -F "file=@PATH_TO_templateFile1.jrxml"  http://localhost:8080/templates/ID_OF_THE_CREATED_TEMPLATE`
 
 - Generate the pdf file :
  
    `curl -H "Content-Type: application/json" -X POST -d '{ "id":"ID_OF_THE_CREATED_TEMPLATE" }' -O  http://localhost:8080/generate `
  
### Template with parameters :

- Create template2
 
    `curl -i -H  "Content-Type: application/json" -X POST -d '{ "title":"template2","description":"Template with parameters" }'  http://localhost:8080/templates`

    
 - add the jrxml file ( see: pdfgen/src/test/resources/jrxmls/templateFile2.jrxml) to the template : 
 
    `curl -F "file=@PATH_TO_templateFile2.jrxml"  http://localhost:8080/templates/ID_OF_THE_CREATED_TEMPLATE`
 
 - Generate the pdf file :
 
    Here we send a json object which contains parm1 and parm2 already defined in the jrxml file (templateFile2.jrxml)
    
    `curl -H "Content-Type: application/json" -X POST -d '{ "id":"ID_OF_THE_CREATED_TEMPLATE","context" : {"parameters": {"param1":"value1","param2":"value2"}}}' -O  http://localhost:8080/generate`
      
### Template with parameters and fields :

- Create template3
 
    `curl -i -H  "Content-Type: application/json" -X POST -d '{ "title":"template2","description":"Template with parameters and fields" }'  http://localhost:8080/templates`

    
 - add the jrxml file ( see: pdfgen/src/test/resources/jrxmls/templateFile3.jrxml) to the template : 
 
    `curl -F "file=@PATH_TO_templateFile2.jrxml"  http://localhost:8080/templates/ID_OF_THE_CREATED_TEMPLATE`
 
 - Generate the pdf file :
 
    Here we send a more complexe json objects with parameters and fields already defined in the jrxml file (templateFile3.jrxml)
    
    `curl -H "Content-Type: application/json" -X POST -d '{ "id":"ID_OF_THE_CREATED_TEMPLATE","context" : {"parameters": {"param1":"value1","param2":"value2"} ,"fields": [{"field1":"value1","field2":"value1"},{"field1":"value2","field2":"value2"},{"field1":"value3","field2":"value3"}]  }}' -O  http://localhost:8080/generate`
      
### complete example
TODO

### Todos
 - improve CRUD opertions