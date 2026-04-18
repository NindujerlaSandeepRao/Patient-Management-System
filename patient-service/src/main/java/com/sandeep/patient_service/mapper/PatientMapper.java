package com.sandeep.patient_service.mapper;

import com.sandeep.patient_service.dto.PatientRequestDto;
import com.sandeep.patient_service.dto.PatientResponseDTO;
import com.sandeep.patient_service.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientResponseDTO=new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDTO;
    }
    public static  Patient toModel(PatientRequestDto patientRequestDto){
        Patient patient=new Patient();
        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDto.getRegisterDate()));
        return patient;
    }
}
