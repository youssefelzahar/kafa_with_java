package io.conductor.demos.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ProducerDemoConsumerShutDown {
    private static  final Logger log= (Logger) LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());
    public static void main(String[] args) {
      //  log.info("hello");

        //create producerprop

        String bootstrapServers = "::1:9092";
        String groupId = "my-fourth-application";
        String topic = "java";



        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String,String>consumer=new KafkaConsumer<>(properties);
        final Thread mainthread=Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
            log.info("Detected a shutdown, let's exit by calling consumer.wakeup()...");
            consumer.wakeup();
            try{
                mainthread.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

        }});
        try {
            consumer.subscribe(Arrays.asList(topic));
            while (true){
                ConsumerRecords<String,String>records=consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String,String>record:records){
                    log.info("Key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", Offset:" + record.offset());
                }
            }
        }catch (WakeupException e){
            log.info("Wake up exception");
        }catch (Exception e){
            log.error("Unexpected exception",e);
        }finally {
            consumer.close();
            log.info("the consumer is closed");
        }

        }







}
