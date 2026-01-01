package com.pm.patientService.mapper;

import java.time.LocalDate;

import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.model.Patient;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {

        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }

    public static Patient toModel(PatientRequestDTO patientDTO) {

        Patient patient = new Patient();
        updateModel(patient, patientDTO);
        patient.setRegisteredDate(LocalDate.parse(patientDTO.getRegisteredDate()));

        return patient;
    }

    public static Patient updateModel(Patient patient, PatientRequestDTO patientDTO) {

        patient.setName(patientDTO.getName());
        patient.setAddress(patientDTO.getAddress());
        patient.setEmail(patientDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientDTO.getDateOfBirth()));

        return patient;
    }
}
