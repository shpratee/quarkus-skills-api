# skills-api project

Integrates with MongoDB to save and retrieve the skills of a developer

## Scaffold
```
$ mvn io.quarkus:quarkus-maven-plugin:1.7.1.Final:create -DprojectGroupId=com.demo.api.skills -DprojectArtifactId=skills-api -DclassName=SkillsResource -Dextensions="resteasy-jackson,rest-client,quarkus-vault,quarkus-kubernetes,quarkus-smallrye-health,mongodb-client" -Dpath="/skills"
```

## Setting up MongoDB

# Getting the MongoDB Docker container created for local testing and development

1. Pre-requisite - a docker daemon or runtime should already be available
Create the MongoDB container

```
$ docker run -d -p 27017:27017 -v ~/data:/data/db mongo:4.0
Status: Downloaded newer image for mongo:4.0
38342ae87f933264631210413f9bf9b2a918a407431a964aca2c18a0e4898c89
```
2. Verify the installation
```
$ curl -i http://localhost:27017
HTTP/1.0 200 OK
Connection: close
Content-Type: text/plain
Content-Length: 85

It looks like you are trying to access MongoDB over HTTP on the native driver port.
```

3. Get into MongoDB container
```
$ docker ps
$ docker exec -it 38342ae87f93 sh
```

4. Start playing with Mongo - like creation of Database or Collection
```
# mongo
# use myDB
# db.createCollection("skills")
# db.skills.insert({"documentId":"DEV_1","skills":[{"name":"Java","proficiency":"4"},{"name":"Groovy","proficiency":"4"}]})
# show collections
skills
# db.skills.find()
{ "_id" : ObjectId("5f515478a1f10c848f16035e"), "documentId" : "DEV_1", "skills" : [ { "name" : "Java", "proficiency" : "4" }, { "name" : "Groovy", "proficiency" : "4" } ] }
```

# Spinning up MongoDB in Kubernetes
Follow: https://kubernetes.io/blog/2017/01/running-mongodb-on-kubernetes-with-statefulsets/

Note: I have used Minikube here, which provides a default StorageClass - standard, so I did not have to create a StorageClass. You can create a local-storage utilizing local disk as storage.

I have created a MongoDB Statefulset in namespace "mongo" using configuration files kept in mongo-config.

## Customizing code to work with MongoDB

1. Add "mongodb-client" extension
```
./mvnw quarkus:add-extension -Dextensions="mongodb-client"
```

2. Properties
```
quarkus.mongodb.connection-string = mongodb://localhost:27017
```

3. Injecting MongoClient
```
@Inject
MongoClient mongoClient;

private MongoCollection<Document> getCollection(){
    return mongoClient.getDatabase("myDB").getCollection("skills");
}
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `skills-api-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/skills-api-1.0-SNAPSHOT-runner.jar`.
