package com.kafka.poc.producerapp.producer;

import java.util.Properties;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
@Component
public class ProducerPoc {
	
	public void init(){
		Properties props = new Properties();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.67.171.30:9092");
	    props.put(ProducerConfig.ACKS_CONFIG, "all");
	    props.put(ProducerConfig.RETRIES_CONFIG, 0);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

	    Producer<String, String> producer = new KafkaProducer<String, String>(props);
	    TestCallback callback = new TestCallback();
	    Random rnd = new Random();
	    for (long i = 0; i < 100 ; i++) {
	        ProducerRecord<String, String> data = new ProducerRecord<String, String>(
	                "test", "key-" + i, "message-"+i );
	        producer.send(data, callback);
	    }

	    producer.close();
		/* Properties props = new Properties();
	        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
	        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-broker1:9092");
	        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	 
	        StreamsBuilder builder = new StreamsBuilder();
	        KStream<String, String> textLines = builder.stream("TextLinesTopic");
	        KTable<String, Long> wordCounts = textLines
	            .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
	            .groupBy((key, word) -> word)
	            .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
	        wordCounts.toStream().to("WordsWithCountsTopic", Produced.with(Serdes.String(), Serdes.Long()));
	 
	        KafkaStreams streams = new KafkaStreams(builder.build(), props);
	        streams.start();*/
		
	}
	private static class TestCallback implements Callback {
	       @Override
	       public void onCompletion(RecordMetadata recordMetadata, Exception e) {
	           if (e != null) {
	               System.out.println("Error while producing message to topic :" + recordMetadata);
	               e.printStackTrace();
	           } else {
	               String message = String.format("sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
	               System.out.println(message);
	           }
	       }
	   }
	
}
