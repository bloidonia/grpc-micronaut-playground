## Brief GRPC example

For my own learning, this takes [the GRPC Route example](https://github.com/grpc/grpc-java/tree/master/examples/src/main/java/io/grpc/examples/routeguide) and converts it to a Micronaut server, and a Micronaut CLI client.

When you run the server with `./gradlew :server:run` it will start a webapp on port 8080 and a GRPC service on 8082.

Then running the client with `./gradlew :client:run` it will make several requests to the server and print the responses.

There is currently no SSL or auth.

