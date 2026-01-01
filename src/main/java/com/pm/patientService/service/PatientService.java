package com.pm.patientService.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.exception.EmailAlreadyExistsException;
import com.pm.patientService.exception.PatientNotFoundException;
import com.pm.patientService.mapper.PatientMapper;
import com.pm.patientService.model.Patient;
import com.pm.patientService.repository.PatientRepository;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients () {
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(PatientMapper::toDTO).toList();
        return patientResponseDTOs;
    }

    public PatientResponseDTO createPatient (PatientRequestDTO patientDTO) {
        if (patientRepository.existsByEmail(patientDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists: " + patientDTO.getEmail());
        }

        Patient patient = PatientMapper.toModel(patientDTO);
        this.patientRepository.save(patient);


        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient (UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
            () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists: " + patientRequestDTO.getEmail());
        }
        PatientMapper.updateModel(patient, patientRequestDTO);

        Patient updatedPatient = patientRepository.save(patient);


        return PatientMapper.toDTO(updatedPatient);
    } 

    public void deletePatient (UUID id) {
        patientRepository.deleteById(id);
    } 
}
