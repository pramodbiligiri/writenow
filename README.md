# WriteNow
A text editor that streams events.

WriteNow is an experiment with Kafka and Java Swing. It sends text inserted into the Swing editor widget to a Kafka topic, and other editor widgets read from that topic. Currently, only insertion is supported.

Usage:
- Download Confluent Open Source from https://www.confluent.io/download/ and unzip. Start the services with the confluent start command, as described in the [Confluent Quickstart](https://docs.confluent.io/current/quickstart.html) guide
- Clone this repository and run the app using:

    mvn clean compile exec:java -Dexec.mainClass=com.pramodb.writenow.EditorApp

Note that all the configs are currently hardcoded in the Java files.

You should see 3 editor windows popup (overlaying each other). Re-arrange them so that all three are visible. Start typing in the window titled "Write Now", and you will see the same text appearing in Write Now 1 and Write Now 2.


![Screencast of WriteNow](write-now-screencast.gif?raw=true "WriteNow in Action")
