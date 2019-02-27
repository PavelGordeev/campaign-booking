## Spring Boot "Campaign-booking" Example Project

* The service is just a simple campaigns review REST service. It uses an in-memory database (H2) to store the data. You can call some REST endpoints defined in com.gordeev.campaignbooking.web.controller on port 8080. (see below)

## How to Run

**Clone this repository**
Make sure you are using JDK 1.8 and Maven 3.x
You can build the project and run the tests by running mvn clean package
Once successfully built, you can run the service by one of these two methods:
```bash
        java -jar target/campaign-booking-0.0.1-SNAPSHOT.jar
```        
or
```bash
        mvn spring-boot:run
```
Check the stdout to make sure no exceptions are thrown
Once the application runs you should see something like this
```bash
2019-02-27 12:58:09.887   INFO 28622 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-02-27 12:58:09.912   INFO 28622 --- [  restartedMain] c.g.c.CampaignBookingApplication         : Started CampaignBookingApplication in 11.99 seconds (JVM running for 12.939)
```

## Here are some endpoints you can call:


**Show campaign by id**
----
Returns json data about a single campaign
* **URL**

    /campaign/:id
* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
       ``` 
                {
                  "id":1,
                  "name":"First test Campaign",
                  "status":"PLANNED",
                  "startDate":"2019-06-18T00:00:00",
                  "endDate":"2019-07-18T00:00:00",
                  "ads":[
                    	    {
                  	          "id":1,
                  	           "name":"first test ad",
                  	           "status":"PLANNED",
                  	           "platforms":["WEB","IOS"],
                  	           "assetUrl":"asset url",
                  	           "campaignId":1
                  	        },
                  	        {
                  	           "id":2,
                  	           "name":"second test ad",
                  	           "status":"PLANNED",
                  	          "platforms":["ANDROID","IOS"],
                  	           "assetUrl":"asset2 url",
                  	           "campaignId":1
                  	        }
                  	    ]
                }
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

**Show ad by id**
----
Returns json data about a single campaign
* **URL**

    /ad/:id
* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `id=[integer]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
       ``` 
        {
           "id":1,
            "name":"first test ad",
            "status":"PLANNED",
            "platforms":["WEB","IOS"],
            "assetUrl":"asset url",
            "campaignId":1
        }
                  
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
  

**Show a list of summaries**
----
Returns json data about a list of summaries
* **URL**

    /summaries?page=1&status=0&sortField=name&sortOrder=asc
* **Method:**

  `GET`
  
*  **URL Params**
    **Optional:**
    
   `page=[integer]`<br />
   `status=[integer]`<br />
   `name=[string]`<br />
   `sortField=[string]`<br />
   `sortOrder=[string]`<br />

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
       ``` 
                  [
                    {
                        "id":1,
                        "name":"First test Campaign",
                        "status":"PLANNED",
                        "advertCount":2
                    },
                    {
                        "id":2,
                        "name":"Second test Campaign",
                        "status":"PLANNED",
                        "advertCount":2
                    }
                  ]
       
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
  

**Create a campaign**
----
Returns json data about a created campaign
* **URL**

    /campaign
* **Method:**

  `POST`
  
*  **URL Params**

   `None`

* **Data Params**

  ``` 
                  {
                    "id":1,
                    "name":"First test Campaign",
                    "status":"PLANNED",
                    "startDate":"2019-06-18T00:00:00",
                    "endDate":"2019-07-18T00:00:00",
                    "ads":[
                      	    {
                    	       "id":1,
                    	       "name":"first test ad",
                    	       "status":"PLANNED",
                    	       "platforms":["WEB","IOS"],
                    	       "assetUrl":"asset url",
                    	       "campaignId":1
                    	    },
                    	    {
                    	       "id":2,
                    	       "name":"second test ad",
                    	       "status":"PLANNED",
                    	       "platforms":["ANDROID","IOS"],
                    	       "assetUrl":"asset2 url",
                    	        "campaignId":1
                    	    }
                    	  ]
                  }

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
       ``` 
                {
                  "id":1,
                  "name":"First test Campaign",
                  "status":"PLANNED",
                  "startDate":"2019-06-18T00:00:00",
                  "endDate":"2019-07-18T00:00:00",
                  "ads":[
                    	    {
                  	           "id":1,
                  	           "name":"first test ad",
                  	           "status":"PLANNED",
                  	            "platforms":["WEB","IOS"],
                  	            "assetUrl":"asset url",
                  	           "campaignId":1
                  	        },
                  	        {
                  	           "id":2,
                  	           "name":"second test ad",
                  	           "status":"PLANNED",
                  	           "platforms":["ANDROID","IOS"],
                  	           "assetUrl":"asset2 url",
                  	           "campaignId":1
                  	        }
                  	     ]
                }
 

**Create an ad**
----
Returns json data about a created ad
* **URL**

    /ad
* **Method:**

  `POST`
  
*  **URL Params**

   `None`

* **Data Params**

  ``` 
                  {
                      "id":1,
                      "name":"first test ad",
                      "status":"PLANNED",
                      "platforms":["WEB","IOS"],
                      "assetUrl":"asset url",
                      "campaignId":1
                  }

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
       ``` 
                  {
                      "id":1,
                      "name":"first test ad",
                      "status":"PLANNED",
                      "platforms":["WEB","IOS"],
                      "assetUrl":"asset url",
                      "campaignId":1
                  }
 

**Update a campaign**
----
Returns json data about a updated campaign
* **URL**

    /campaign/:id
* **Method:**

  `PUT`
  
*  **URL Params**

   **Required:**
    
      `id=[integer]`

* **Data Params**

  ``` 
                  {
                    "id":1,
                    "name":"First test Campaign",
                    "status":"PLANNED",
                    "startDate":"2019-06-18T00:00:00",
                    "endDate":"2019-07-18T00:00:00",
                    "ads":[
                        	{
                        	    "id":1,
                        	    "name":"first test ad",
                        	    "status":"PLANNED",
                        	    "platforms":["WEB","IOS"],
                        	    "assetUrl":"asset url",
                        	    "campaignId":1
                    	    },
                    	    {
                        	    "id":2,
                        	    "name":"second test ad",
                           	    "status":"PLANNED",
                        	    "platforms":["ANDROID","IOS"],
                           	    "assetUrl":"asset2 url",
                         	    "campaignId":1
                    	    }
                    	  ]
                  }

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
       ``` 
                {
                  "id":1,
                  "name":"First test Campaign",
                  "status":"PLANNED",
                  "startDate":"2019-06-18T00:00:00",
                  "endDate":"2019-07-18T00:00:00",
                  "ads":[
                         	{
                         	    "id":1,
                        	    "name":"first test ad",
                        	    "status":"PLANNED",
                        	    "platforms":["WEB","IOS"],
                        	    "assetUrl":"asset url",
                  	            "campaignId":1
                       	    },
                    	    {
                        	    "id":2,
                  	            "name":"second test ad",
                  	            "status":"PLANNED",
                        	    "platforms":["ANDROID","IOS"],
                  	            "assetUrl":"asset2 url",
                  	            "campaignId":1
                  	        }
                	    ]
                }
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

**Update an ad**
----
Returns json data about a updated ad
* **URL**

    /ad/:id
* **Method:**

  `PUT`
  
*  **URL Params**

   **Required:**
    
   `id=[integer]`

* **Data Params**

  ``` 
                    {
                        "id":1,
                        "name":"first test ad",
                        "status":"PLANNED",
                        "platforms":["WEB","IOS"],
                        "assetUrl":"asset url",
                        "campaignId":1
                    }

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
       ``` 
                  {
                     "id":1,
                     "name":"first test ad",
                     "status":"PLANNED",
                     "platforms":["WEB","IOS"],
                     "assetUrl":"asset url",
                     "campaignId":1
                  }
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

**Delete a campaign**
----

* **URL**

    /campaign/:id
* **Method:**

  `DELETE`
  
*  **URL Params**

   **Required:**
    
      `id=[integer]`

* **Data Params**

    `None`

* **Success Response:**

  * **Code:** 200 <br />
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

**Delete an ad**
----

* **URL**

    /ad/:id
* **Method:**

  `DELETE`
  
*  **URL Params**

   **Required:**
    
   `id=[integer]`

* **Data Params**

    `None` 
* **Success Response:**

  * **Code:** 200 <br />
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />

