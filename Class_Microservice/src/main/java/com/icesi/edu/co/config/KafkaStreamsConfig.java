package com.icesi.edu.co.config;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.icesi.edu.co.model.ResumenEntrenamiento;

import java.time.Duration;

@Configuration
public class KafkaStreamsConfig {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {

        KStream<String, String> stream = streamsBuilder.stream("datos-entrenamiento");

        stream
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofDays(7))).aggregate(
                        ResumenEntrenamiento::new,
                        // Aggregator
                        (key, value, aggregate) -> aggregate.actualizar(value),
                        // Materialized
                        Materialized.as("resumen-entrenamiento-store")

                ).toStream()
                .to("resumen-entrenamiento");

        return stream;
    }
}
