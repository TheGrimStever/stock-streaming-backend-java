# stock-streaming-backend-java
A project that will simulate stock market data to practice ingesting and processing streams of data.

This project is split into two subprojects: *Producer-Simulator* and *App*.

*Producer-Simulator* will generate fake trade data and write that to a Kafka stream with the topic: `trades`.
*App* will read from that Kafka stream and process that data, and make metric statistics available via REST 
endpoints.

Use `./scripts/dev-start.sh`, this will start Kafka, check to make sure it's running, start the trade producer, 
and start Spring Boot.

Use `./scripts/dev-stop.sh` to shut down everything.

Alternatively, if you want to run your IDE's debugger, run `./scripts/producer-start.sh` to just start Kafka and 
`producer-simulator`, and start your IDE's debugger separately.

If you just want to start the `producer-simulator` then just run `./gradlew :producer-simulator:run`