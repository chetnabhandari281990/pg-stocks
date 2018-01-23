Stocks monitoring application developed with SpringBoot

# pq-stocks
1. Start Mongo db on your localhost , port : 27017.
   Configure spring.data.mongodb.uri in application.properties file if different from localhost:27017

2.  In Mongo CLI : execute the following : stock_init_script  
            
3. Run Application as Spring Boot Applicaton

4. Access UI : http://localhost:8080

5. Access Swagger : http://localhost:8080/swagger-ui.html


The application is not secure yet, we can secure the addStock and modifyStock endpoints using JWT.
