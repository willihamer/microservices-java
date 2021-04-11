# microservices-java
simple java spring project based on cloud microservices:

This project is a Java SpringBoot cloud project based on microservices and dockerizable, is CRUD about products and amount of items related to each product, with OAuth authentication and defined roles, this project contains next microservices:
    - common-service: a project that contains simple common classes used by other projects
    - config-server: a project that contains the connection properties for databases, and general props related to all projects
    - docker-compose: this folder contains the compose.yml and some commands necessaries to run the project in docker containers
    - eureka-server: This service is the main server where all the microservices are registered and can communicate with each other
    - items-service: this service contains the logic to count all the items for each product and also connect with products-service through eureka-server
    - OAuth-service: this service contains all the logic for the login, register of users and managing of roles, encryption of passwords, protection of endpoints, and a couple of things more. This project connects with user-services using eureka-server
    - products-service: this service contains a CRUD for products, connect with MySQL using properties in the config-server, is possible to run multiple instances of this project the port is configured to support that dynamically and because of zuul server there will be used also dynamically
    - user-commons: project that contains simple common objects used by other projects
    - users-service: project that contains a CRUD  for users, connect with Postgres using properties in the config-server, is possible to run multiple instances of this project the port is configured to support that dynamically and because of zuul server there will be used also dynamically
    - Zipkin-server: this folder just contains the jar and the SQL configuration used in this project, Zipkin allows to track the traffic in each petition saying which projects are involved, more information in https://zipkin.io/
    - zuul-server: this is the balancer all the petition must go through here, and this service will select the fast/best option for the consumption.

Other supports:
    - rabbit-MQ: also is possible to run an instance of rabbitMq it can be downloaded or ran in Docker, this is a messaging broker, Zipkin and the services are already set up for use like producers and consumers.