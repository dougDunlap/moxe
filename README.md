# Moxe App

### Assumptions

- There would be many hospitals, more providers + more patients
- Providers could service multiple hospitals
- Patients could see multiple providers at multiple hospitals, even the same provider at different hospitals
- Hospital name is unique
- Patient name is not unique.  Patients can share the same name.
- Provider name is not unique   Providers can share the same name.

### ER Diagram

![](src/main/resources/ER%20Diagram.png)

### Test coverage ( Jacoco )

<img width="526" alt="Screen Shot 2022-08-08 at 4 53 59 PM" src="https://user-images.githubusercontent.com/420977/183512663-c86eeaa3-6c3c-4a67-b965-5dbda252f343.png">

Testing was done at Controller level to both verify all endpoints were working as expected + provide first wave of test coverage. Ideally tests would have more locality and be in same class as code.  This would be future improvement

### Database

H2 was used for speed + portability. Ultimately database use would need some thought about which database type to use, when + where.

Going forward we would need to be safter about database interactions. For example we would need to identify + handle exceptions better.  As an extension exceptions overall would need to be handled; retried, aborted or alerted.

### Install

`mvn clean install` - This will also run test coverage

### Operation

Get hosital details by name:
`GET /hospital/name/{name}`

Get providers by hospital id:
`GET /hospital/{hospitalId}/providers`

Get hospital provider by provider id:
`GET /hospital/{hospitalId}/providers`

Get hosptial providers by hospital id ( as hospital provider id will differ from provider id ) :
`GET /hospital/{hospitalId}/hospitalProvider`

Get patients by hospital id and provider id:
`GET hospitalProvider/{hospitalProviderId}/patient`
