# BibsonomyToTriple
Retrieve bibsonomy items and return as triple with AFEL vocabulary in json.

Available as commandline application and REST webservice.

##Coding Enviroment
- Ubuntu 16.04
- java 8
- Intellij 14.1.4

## Installation

To setup the API follow these steps:

```js
> git clone https://github.com/ran-yu/BibsonomyToTriple.git

First change the bibsonomy username and API key in class GetBibsonomy.java

> _bib = new Bibsonomy("username", "api_key");

Then

> cd BibsonomyToTriple
> mvn compile
> mvn war:war
```
This will build the war file in the target directory.
Copy the war into the deployment directory of your installed Java Servlet (e.g. apache-tomcat/webapps/) container.

##Methods
The individual methods are accessible through URL once the REST API setup succeeded. 

The URL is formatted as: http://tmocatserver.xxx.yyy.zz:8080/bib_data-2.0/GetBib/getPosts?start=2000&end=3000

###Query for users within the range [start,end]:
Method name: getUsers

Parameters: {start, end} , integer

###Query for posts within the range [start,end]:
Method name: getPosts

Parameters: {start, end} , integer

###Query for user detail:
Method name: getUserDetail

Parameters: {username} , string

Outputs: A string in JASON format as the example in the end of this readme.`

##Result

The returned string "res" is in json format, e.g.
```js
{
"afel:userName":"_:yu",
"rdf:type":"afel:User",
"afel:platform":"",
"afel:fullName":"\"Ran Yu\"",
"afel:email":"\"yu@l3s.de\"",
"afel:homepage":""
}

{"afel:userName":"_:yu","rdf:type":"afel:User","afel:platform":"","afel:fullName":"\"Ran Yu\"","afel:email":"\"yu@l3s.de\"","afel:homepage":""}
```
  
