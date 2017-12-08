package com.pramodb.writenow;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

public class TextReceiver {

    private ReceiverEditor receiverEditor;

    public TextReceiver(ExecutorService executorService, String consumerId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", consumerId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("my-topic"));

        executorService.submit(() -> {
            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    String[] split = record.value().split(":", 2);
                    receiverEditor.addContent(Integer.parseInt(split[0]), split[1]);
                }
            }
        });
    }

    public void addReceiver(ReceiverEditor receiverEditor) {
        this.receiverEditor = receiverEditor;
    }
}
