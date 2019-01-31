1) service-one and service-one-new are identical service. 
2) service-one and service-one-new are running in different port as if multiple instances are running of the same service.
3) service-two and service-two-new are identical service. 
4) service-two and service-two-new are running in different port as if multiple instances are running of the same service.
5) gateway-service is responsible to redirect the requests to the above two services.
6) gateway-service has impletented zull for routing the request to desired services.
7) gateway-service is also a ribbon client for load balancing.
8) load balancing rule used is round robin.
9) registry-service is an eureka server.
10) all the above service instances registered themselves with eureka.
11) zuul use ribbon internally so no ribbon dependency needs to be added in gateway-service.
12) ribbon usign eureka client for discovering service instance.

This example is a complete set of how we can hide access to the application services from outside world by gateway pattern. Also, we are dynamically fetching the list of services from eureka server for load balancing. This is an example of ribbon dynamic server details load balancing.