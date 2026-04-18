package com.sandeep.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.logging.Logger;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Service
@EnableKafka
public class KafkaConsumer {


    @KafkaListener(topics = "patient1",groupId = "analytics-service-v3" )
    public void consumeEvent(ConsumerRecord<String, byte[]> record){

        byte[] event = record.value();

        System.out.println("🔥 RAW RECEIVED: " + new String(event));

        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            System.out.println("✅ PARSED EVENT:");
            System.out.println("ID: " + patientEvent.getPatientId());
            System.out.println("Name: " + patientEvent.getName());
            System.out.println("Email: " + patientEvent.getEmail());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
