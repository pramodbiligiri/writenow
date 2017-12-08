package com.pramodb.writenow;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.util.Properties;

public class TextTransmitter implements DocumentListener {

    private final Producer<String, String> producer;

    private static final Logger LOG = LoggerFactory.getLogger(EditorApp.class);

    public TextTransmitter() {
        this.producer = createKafkaProducer();
    }

    private static KafkaProducer<String, String> createKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 1);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(props);
    }

    public void insertUpdate(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(e.getOffset(), e.getLength());
            LOG.debug("insert: {}", text);
            producer.send(new ProducerRecord<String, String>("my-topic", null, e.getOffset() + ":" + text));
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    public void removeUpdate(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(e.getOffset(), e.getLength());
            LOG.debug("remove: {}", text);
            producer.send(new ProducerRecord<String, String>("my-topic", null, text));
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    public void changedUpdate(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(e.getOffset(), e.getLength());
            LOG.debug("changed: {}", text);
            producer.send(new ProducerRecord<String, String>("my-topic", null, text));
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }
}
