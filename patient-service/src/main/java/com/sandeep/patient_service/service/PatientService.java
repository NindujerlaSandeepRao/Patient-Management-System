package com.sandeep.patient_service.service;

import billing.BillingServiceGrpc;
import com.sandeep.patient_service.dto.PatientRequestDto;
import com.sandeep.patient_service.dto.PatientResponseDTO;
import com.sandeep.patient_service.exception.EmailAlredyExistsException;
import com.sandeep.patient_service.exception.PatientNotFoundException;
import com.sandeep.patient_service.grpc.BillingServiceGrpcClient;
import com.sandeep.patient_service.kafka.KafkaProducer;
import com.sandeep.patient_service.mapper.PatientMapper;
import com.sandeep.patient_service.model.Patient;
import com.sandeep.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private BillingServiceGrpcClient billingServiceGrpcClient;
    @Autowired
    private KafkaProducer kafkaProducer;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients= patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS=patients.stream().map(PatientMapper::toDTO).toList();
        return patientResponseDTOS;
    }

    public PatientResponseDTO createPatient(PatientRequestDto patientRequestDto) throws EmailAlredyExistsException {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailAlredyExistsException("A patient with this email already exists"+ patientRequestDto.getEmail());
        }
        Patient newPatient=patientRepository.save(PatientMapper.toModel(patientRequestDto));
        // an email address must be unique
        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(),newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO  updatePatient(UUID id,PatientRequestDto patientRequestDto){
        Patient patient=patientRepository.findById(id)
                .orElseThrow(()-> new PatientNotFoundException("Patient not found with the ID: "+id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(),id)){
            throw new EmailAlredyExistsException("A patient with this email already exists"+ patientRequestDto.getEmail());
        }

        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatedPatient=patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }


    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }
}
