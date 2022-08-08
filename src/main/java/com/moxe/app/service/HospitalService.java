package com.moxe.app.service;

import com.moxe.app.model.Hospital;
import com.moxe.app.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HospitalService {

    @Autowired
    HospitalRepository repository;

    /**
     * Get all hospitals
     *
     * @return List<Hospital>
     */
    public List<Hospital> getAllHospitals() {
            return repository.findAll();
    }

    /**
     * Get Hospital by id
     *
     * @param id
     * @return Hospital
     */
    public Hospital getHospitalById(String id) {
        final UUID hospitalId = UUID.fromString(id);

        // We need to retrieve item after inserting, as returning object is not handled by
        // creation from spring
        return repository.findOneById(hospitalId);
    }

    /**
     * Get Hospital by name
     *
     * @param name
     * @return Hospital
     */
    public Hospital getHospitalByName(String name) {
        return repository.findOneByName(name);
    }

    /**
     * Add Hospital
     *
     * @param hospital
     * @return Hospital
     */
    public Hospital addHospital(Hospital hospital) { return repository.create(hospital);}
}
