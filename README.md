REST API written in Java with SpringBoot+RabbitMQ

This is a Java SpringBoot assignment built with Maven, RabbitMQ and embedded Tomcat server.
The API accepts three different parameters in input and based on the parameters makes calls to three different APIs (Track,Shipment,Pricing)which runs on a
 
API Methods Implemented:
Endpoint
Methods
/aggregation
GET
 
API Endpoint :
http://localhost:8081/aggregation?pricing=KO,VG,CD&track=123456782,123456783,123456784&shipments=123456781,123456787,123456785

RUN Instructions:
1. Pull Docker image of Track,ship,pricing APIs https://hub.docker.com/r/xyzassessment/backend-services
2. Execute docker run and publish in port 8080 and start the container
3. Pull docker image of RabbitMQ docker pull rabbitmq
4. Execute docker run and publish in port 5672 and start the container
5. Start spring boot application 
6. TNT API is exposed in port 8081 and  execute requests via postman
 
 
 

