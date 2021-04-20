# Addressbook Api [![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/rodrigolimasd/qideas-register-api/blob/master/LICENSE)

This is the api for registering addresses and searching for geolocation.

It is a RESTful API built with Java 11 + Spring Boot + MongoDB + Google Geocoding API.

The main purpose of this API is to record addresses on a MongoDB basis and find the geolocation with the Google Geocoding API through the information provided in the address, retrieving the latitude and longitude of the provided address.

## Getting Started

### Prerequisites

To run this project in development mode, you will need to have a basic environment with Java JDK 11+ and Maven 3.5.4+ installed. To use the database, you will need to have MongoDB installed and running on your machine on the default port (27017).

### Installing

**Cloning the Repository**
````
$ git clone https://github.com/rodrigolimasd/addressbook-geolocation.git
$ cd addressbook-geolocation
````
### Running the Development environment

**Running with Maven**

To make the API run, you need to set the GOOGLE_MAPS_API_KEY environment variable, which is a key with access to the Google Geocoding API, execute the command with the key:

```
$ export GOOGLE_MAPS_API_KEY={GOOGLE_KEY}
```
Where GOOGLE_KEY is the key provided for access.

With Database running and the environment properly configured, you can now run the server:

**Running API**

```
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

To run the tests use the following command

**Running the tests**

```
$ mvn clean test
```

### Consuming the API

**Create An Address**

```
$ curl --location --request POST 'http://localhost:8080/v1/adresses' \
--header 'Content-Type: application/json' \
--data-raw '{
    "streetName": "Alameda Itapecuru",
    "number": 214,
    "complement": "ap .. torre ..",
    "neighbourhood": "Alphaville Industrial",
    "city": "Barueri",
    "state": "SP",
    "country": "Brazil",
    "zipcode": "06454080"
}'
```
Response Status Code: **201**
```
$ {"id":"607f094093802527420fac91","streetName":"Alameda Itapecuru","number":214,"complement":"ap .. torre ..","neighbourhood":"Alphaville Industrial","city":"Barueri","state":"SP","country":"Brazil","zipcode":"06454080","latitude":-23.496431,"longitude":-46.8448125}%
```

**Update An Address**

```
$ curl --location --request PUT 'http://localhost:8080/v1/adresses' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "607f094093802527420fac91",
    "streetName": "Alameda Itapecuru",
    "number": 214,
    "complement": "ap .. block ..",
    "neighbourhood": "Alphaville Industrial",
    "city": "Barueri",
    "state": "SP",
    "country": "Brazil",
    "zipcode": "06454080",
    "latitude": -23.496431,
    "longitude": -46.8448125
}'
```
Response Status Code **204** No Content 

**Find Address By ID**

```
$ curl --location --request GET 'http://localhost:8080/v1/adresses/607f094093802527420fac91'
```
Response Status Code **200**
```
$ {"id":"607f094093802527420fac91","streetName":"Alameda Itapecuru","number":214,"complement":"ap .. block ..","neighbourhood":"Alphaville Industrial","city":"Barueri","state":"SP","country":"Brazil","zipcode":"06454080","latitude":-23.496431,"longitude":-46.8448125}%
```

**Delete An Address**

```
$ curl --location --request DELETE 'http://localhost:8080/v1/adresses/607f094093802527420fac91'
```
Response Status Code **204**

### Deploy to Docker

To deploy the API, you need to set the GOOGLE_MAPS_API_KEY environment variable:
```
$ export GOOGLE_MAPS_API_KEY={GOOGLE_KEY}
```

Build the app:
```
$ mvn clean package
```

To deploy to the docker using the docker compose it is necessary to configure the docker compose before

```
$ docker-compose up
```

### Checking service

To check if the service is up, make a call to the endpoint.
```
$ curl --location --request GET 'http://localhost:8090/actuator/health'
```
Response Status Code **200**
```
$ {"status":"UP"}%
```

### Consuming the API with swagger

It is also possible to test and consume the API with Swagger.

**Swagger** Endpoint: http://localhost:8090/swagger-ui.html

## Contributing

E-mail: rodrigolimasd@gmail.com

Connect with me at [LinkedIn](https://www.linkedin.com/in/rodrigolimasd/)

Thank you!

