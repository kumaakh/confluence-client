# Atlassian Confluence Java Client

## Setting up oauth access to Confulence:
* Follow same steps as described [here](https://developer.atlassian.com/jiradev/jira-apis/jira-rest-apis/jira-rest-api-tutorials/jira-rest-api-example-oauth-authentication#JIRARESTAPIExample-OAuthauthentication-step1Step1:ConfiguringJIRA).
* Make a copy of ConflClient.sample.xml as ConflClient.xml.  
* Update ConflClient.xml with following details:
 - baseURL : base URL for your confluence instance e.g. https://foobar.atlassian.net/wiki or http://localhost:8090/wiki
 - CONSUMER_KEY : e.g. "hardcoded-consumer" from above
 - CONSUMER_PRIVATE_KEY : if you follow the above setup as-is it should be 
`MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxZDzGUGk6rElyPm0iOua0lWg84nOlhQN1gmTFTIu5WFyQFHZF6OA4HX7xATttQZ6N21yKMakuNdRvEudyN/coUqe89r3Ae+rkEIn4tCxGpJWX205xVF3Cgsn8ICj6dLUFQPiWXouoZ7HG0sPKhCLXXOvUXmekivtyx4bxVFD9Zy4SQ7IHTx0V0pZYGc6r1gF0LqRmGVQDaQSbivigH4mlVwoAO9Tfccf+V00hYuSvntU+B1ZygMw2rAFLezJmnftTxPuehqWu9xS5NVsPsWgBL7LOi3oY8lhzOYjbMKDWM6zUtpOmWJA52cVJW6zwxCxE28/592IARxlJcq14tjwYwIDAQAB'. One can always generated their own key-pair.

## Fetching the accessToken
To use the confluence API we need its access token fetched via oauth protocol which requires interactive login to confluence.
Call ConflClient.fetchAuthToken() method. It launches a browser instance to ask you to login to atlassian and fetches the access token on you behalf. 
Fetched token is printed to console. Usually the access token works for several hours.   
To avoid fetching it multiple times the token is automatically stored in ConflClient.xml so that future runs may benefit from it


## Usage 
Usage of the confluence client can be seen from  ConflClientTest
```
		client= new ConflClient();
		client.setSpaceKey("TEST");
		//all properties are loaded from xml file
		if(StringUtils.isEmpty(client.getAccessToken()))
		{
			client.fetchAuthToken();
		}
		client.addPage(....)
```

## Features
* Add page
* Fetch page content
* Update Page
* Trash Page: deletePage(<page id>, false) moves the page to trash
* Trash Page: deletePage(<page id>, true) purges the page forever, even if it was never trashed 
* Make one or more attachments to a page
* Simple API for page construction:
  - All types of page sections 
  - All types of text formating
  - Text colors
  - Headings
  - Tables
  	- with row/column headers
  	- with different cell styles
  	- with merged cells
  - Links
    - to internet pages
    - other wiki pages 
    - attachments
    - including pages and attachments from other spaces
  - Images
    - from attachments
    - from web
    - with various properties
    - with advanced effects
    
  	
  
* All errors are published as HttpResponseExceptions with code and message 

## TBD
* Tree of children
* TOC
* List of attachments


 
  