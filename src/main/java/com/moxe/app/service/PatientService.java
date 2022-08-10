package com.moxe.app.service;

import com.moxe.app.model.Patient;
import com.moxe.app.model.Provider;
import com.moxe.app.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    PatientRepository repository;

    /**
     * Get all patients independent of provider
     *
     * @return
     */
    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    /**
     * Get all patients given hospital provider id
     *
     * @param id
     * @return List<Patient>
     */
    public List<Patient> getAllPatientsByHospitalProviderId(String id) {
        final UUID hospitalProviderId = UUID.fromString(id);
        return repository.findAllPatientsByHospitalProviderId(hospitalProviderId);
    }

    /**
     * Get Patient by id
     *
     * @param id
     * @return Patient
     */
    public Patient getPatientById(String id) {
        final UUID patientId = UUID.fromString(id);
        return repository.findOneById(patientId);
    }

    /**
     * Add Patient
     *
     * @param patient
     * @return Patient
     */
    public Patient addPatient(Patient patient) {
        final UUID id = repository.create(patient);

        // We need to retrieve item after inserting, as returning object is not handled by
        // creation from spring
        return repository.findOneById(id);
    }

    /**
     * Update Patient
     *
     * @param patient
     * @return Patient
     */
    public Patient updatePatient(Patient patient) {
        repository.update(patient);

        // We need to retrieve item after inserting, as returning object is not handled by
        // creation from spring
        return repository.findOneById(patient.getId());
    }
}
