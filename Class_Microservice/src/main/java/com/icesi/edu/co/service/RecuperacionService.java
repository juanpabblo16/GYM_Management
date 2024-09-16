package com.icesi.edu.co.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecuperacionService {

    private final KafkaConsumer<String, String> consumer;

    @Autowired
    public RecuperacionService(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public void iniciarProcesamiento() {
        consumer.subscribe(Arrays.asList("ocupacion-clases", "datos-entrenamientos"));

        Map<TopicPartition, Long> ultimoOffsetProcesado = cargarUltimoOffset();
        ultimoOffsetProcesado.forEach((tp, offset) -> consumer.seek(tp, offset));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                procesarRecord(record);
                guardarOffset(record.topic(), record.partition(), record.offset());
            });
        }
    }

    private void procesarRecord(ConsumerRecord<String, String> record) {
        System.out.printf("Processing record from topic %s with key %s and value %s%n", record.topic(), record.key(), record.value());
    }

    private void guardarOffset(String topic, int partition, long offset) {
        System.out.printf("Saving offset %d for topic %s partition %d%n", offset, topic, partition);
    }

    private Map<TopicPartition, Long> cargarUltimoOffset() {
        Map<TopicPartition, Long> offsets = new HashMap<>();
        offsets.put(new TopicPartition("ocupacion-clases", 0), 123L); 
        offsets.put(new TopicPartition("datos-entrenamientos", 0), 456L);
        return offsets;
    }
}
