# WriteNow
A text editor that streams events.

WriteNow is an experiment with Kafka and Java Swing. It is a text editor that streams editor events to Kafka as and when they occur. Other applicatons can connect to the Kafka topic of an editor to process the events.

Usage:
- Download Confluent Open Source from https://www.confluent.io/download/ and unzip. Start the services with the confluent start command, as described in the [Confluent Quickstart](https://docs.confluent.io/current/quickstart.html) guide
- Clone this repository and run the app using:
mvn exec:java -Dexec.mainClass=com.pramodb.writenow.EditorApp

Note that all the configs are currently hardcoded in the Java files.

You should see 3 editor windows popup (overlaying each other). Re-arrange them so that all three are visible. Start typing in the window titled "Write Now 1", and you will see the same text appearing in Write Now 2 and Write Now 3.
