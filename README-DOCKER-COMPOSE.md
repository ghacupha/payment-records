# JHipster generated Docker-Compose configuration

## Usage

Launch all your infrastructure by running: `docker-compose up -d`.

## Configured Docker services

### Service registry and configuration server:

- [JHipster Registry](http://localhost:8761)

### Applications and dependencies:

- paymentRecords (microservice application)
- paymentRecords's postgresql database
- paymentRecords's elasticsearch search engine
- paymentRecordsGate (gateway application)
- paymentRecordsGate's postgresql database

### Additional Services:

- Kafka
- Zookeeper
- [JHipster Console](http://localhost:5601)
- [Zipkin](http://localhost:9411)
