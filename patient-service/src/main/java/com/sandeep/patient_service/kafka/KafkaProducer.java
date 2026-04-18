package com.sandeep.patient_service.kafka;

import com.sandeep.patient_service.model.Patient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import patient.events.PatientEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class KafkaProducer {
    private static final Logger logger = Logger.getLogger(KafkaProducer.class.getName());

    private  KafkaTemplate<String,byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String,byte[]> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }
    public void sendEvent(Patient patient){
        PatientEvent event= PatientEvent.newBuilder().
                setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try{
            kafkaTemplate.send("patient1",event.toByteArray());
            //System.out.println("Message sent");
        }catch (Exception e){
            logger.log(Level.SEVERE, "Error sending PatientCreated event: " + event, e);
        }
    }

}
