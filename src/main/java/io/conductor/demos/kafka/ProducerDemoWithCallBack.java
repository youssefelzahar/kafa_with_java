package io.conductor.demos.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallBack {
    private static  final Logger log= (Logger) LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());
    public static void main(String[] args) {
        log.info("hello");

        //create producerprop

        String bootstrapServers = "::1:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        //create the producer
        KafkaProducer<String,String> producer=new KafkaProducer<>(properties);
        for(int i=0;i<10;i++)
        {
            String topic = "java";
            String value = "hello world " +i;
            String key = "id_" +i;
            //create producer record
            ProducerRecord<String,String>producerRecord=new ProducerRecord<>(topic,key,value);

            //send data
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {


                if(e==null){
                    log.info("Received new metadata. \n" +
                            "Key:" + producerRecord.key() + "\n" +
                            "Topic:"+recordMetadata.topic()+"\n"+
                            "Partition:"+recordMetadata.partition()+"\n"+
                            "Offist:"+recordMetadata.offset()+"\n"+
                            "Timestamp:"+recordMetadata.timestamp());
                }
                else {
                    log.error("Error while producing",e );
                }
            }
        });

        }



        //tell the producer send all data
         producer.flush();

         //close the producer
        producer.close();




    }
}
