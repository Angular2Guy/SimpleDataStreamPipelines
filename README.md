# SimpeDataStreamPipelines

---

This is a project shows howto create DataStream Pipelines based on Spring Boot and Kafka 
that are simple, efficient and based on Jdk 25+. This project is a simpler alternative to the Spring Cloud Data Flow 
architecture. A DataStream Pipeline has a source that
receives changes/requests and transforms them to events that are send to Kafka. The 
Kafka events are received by the sink and transformed to be send to the receiving
system. A DataStream is one Spring Boot application providing the source and sink.
The scalability and efficiency is provided by Jdk 25+ Virtual Threads(pinned Threads issue resolved).  

Author: Sven Loesekann

Technologies: Java, Kotlin, Spring Boot, Jpa, Postgresql, Soap, Rest, Kafka, Maven,
Kubernetes, Helm, Docker

[![CodeQL](https://github.com/Angular2Guy/SimpleDataStreamPipelines/actions/workflows/codeql.yml/badge.svg)](https://github.com/Angular2Guy/SimpleDataStreamPipelines/actions/workflows/codeql.yml)

## Articles

[Simple Efficient Spring/Kafka DataStreams](https://angular2guy.wordpress.com/2025/02/24/simple-efficient-spring-kafka-datastreams/)

[Using KRaft Kafka for development and Kubernetes deployment](https://angular2guy.wordpress.com/2024/08/17/using-kraft-kafka-for-development-and-kubernetes-deployment/)

## Features

1. SourceSink Application to create changes/events/requests to be processed by the DataStreams implemented in Kotlin.
2. DatabaseToRest DataStream that uses Debezium to receive the changes in a table and send them to a rest endpoint.
3. SoapToDb DataStream that receives Soap requests and stores them in a database with Jpa.
4. EventToFile DataStream that receives Kafka events and stores them in files on the filesystem.
5. A Helm chart to deploy Kafka(KRaft), Postgresql and all the applications of the system to a Minikube Kubernetes cluster.

## Mission Statement

The project shows howto create and run DataStreams based on Jdk 25+. DataStreams are 
a single application that contains the data source that receives the requests/events/changes 
and turns them into Kafka events and sends them to Kafka. The sink of the DataStream 
receives the Kafka events and sends/stores them in the target system. The Virtual Threads 
make efficient use of the system resources and the single application saves memory 
because of the shared JVM. To show this architecture this SourceSink Spring Boot application
creates database changes/Soap requests/Kafka events to trigger the DataStreams and receives 
Rest calls of one DataStream. The SimpleDataStreamPipelines project has a Maven build
to build all of the applications. 

For deployment the SimpleDataStreamPipelines provides Dockerfiles that are used to 
create Docker images that can be deployed in a Minikube Kubernetes cluster. To 
configure/deploy the applications in the Docker images a Helm chart is provided. 

This project has been created to show howto create simple efficient DataStreams. 
The goal is to reduce complexity and use code instead of configuration. To do that a 
little bit of code duplication was accepted to enable the simplicity. The architecture 
is easy to understand and because of that easy to extend. 

## Setup

For development Intellij CE was used because it supports Java/Kotlin very well. 
To run it for development the files 'runKafka.sh' and 'runPostgresql.sh' can be used 
to run the both as local Docker images. 

To create the DataStreams the commands in the 'buildDocker.sh' files in the directories
can be used. The images need to be pushed to the DockerHub and then can be used with the 
Helm Chart in Minikube.