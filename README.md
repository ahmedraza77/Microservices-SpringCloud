# Microservices-SpringCloud
Microservice with Spring Boot and Spring Cloud

Start Rabbit and Running zipkin java jar in D microservices folder

set RABBIT_URI=amqp://localhost
java -jar zipkin-server-2.5.2-exec.jar
java -jar zipkin-server-2.19.3-exec.jar


Spring Cloud Config Server (Centralized Configuration) (Port 8888)
-> git-localconfig-repo
  -MS-dev.properties
  -MS-qa.properties
Limits MS (2 instances, diff environments) (Port 8801,8802)
-> Spring Cloud Bus(all instances updated with latest git configuration)
just refresh once to update all instance, (/actuater/bus-refresh)
-> Fault Tolerance (with Hystrix, fallback method to handle failure)

Currency Calculation Service & Currency Exchange Service(2 instances)
Feign Client (Communication b/w MS)
Ribbon Load Balancing (distribute calls b/w diff instances)
Eureka Naming Server (Service Registration, Service Discovery)
-> Ribbon talk to naming server and retrieves all instances available
Zuul API Gateway(every call going through gateway)
Distributed Tracing (Spring Cloud Sleuth + Zipkin Server)
->Sleuth, to assign unique ID to every request flowing 
->Zipkin, centralized logging at one place (using RabbitMQ)

PORT
centralized logging = localhost:9411/zipkin/
zuul = 8765
Eureka = 8761
C-Exchange-S = 8001,8002
C-Conversion-S = 8101
