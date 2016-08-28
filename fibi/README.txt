
Info about the application:
-----------------------------------

1) Tomcat is set to run on 9090

2) Context path is fibi

3) Credentials to access the service : user: fibi, pwd: fibi

4) As the application is integration with Swagger 2.0, we can see the generated REST Handlers and even we can try sending the requests from there
   
    URL to access : http://localhost:9090/fibi/swagger-ui.html

5) Before verifying the results for 'Sample' REST controller.
   -  MongoDB must be installed in your local machine
   - 'fibi' database along with collection and documents
    
     You can follow the below quick steps to create 'fibi'- database, 'sample'- collection and few documents under collection
     
       use fibi
       db.sample.insert({"name": "pragathes", "email": "pragu06@gmail.com"});
       db.sample.insert({"name": "alagu", "email": "alagunagappan@gmail.com"});
     
     
 
 -------------------------------------------------------------------------------------------------------------     
 