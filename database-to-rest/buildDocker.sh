#!/bin/sh
#./mvnw clean install -Ddocker=true -Dnpm.test.script=test-chromium
../mvnw clean install
docker build -t angular2guy/dbtorest:latest --build-arg JAR_FILE=dbtorest-0.0.1-SNAPSHOT.jar --no-cache .
docker run -p 8080:8080 --memory="384m" --network="host" angular2guy/dbtorest:latest