package com.moxe.app.controller;

import com.moxe.app.model.Hospital;
import com.moxe.app.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Hospital Controller contains endpoints associated with Hospital Maintenance
 */
@RestController
public class HospitalController {

    @Autowired
    private HospitalService service;

    @GetMapping("/hospital")
    public List<Hospital> getAllHospitals() {
        return service.getAllHospitals();
    }

    @GetMapping("/hospital/{id}")
    public Hospital getHospitalById(@PathVariable String id) {
        return service.getHospitalById(id);
    }

    @GetMapping("/hospital/name/{name}")
    public Hospital getHospitalByName(@PathVariable String name) {
        return service.getHospitalByName(name);
    }

    @PostMapping("/hospital")
    public Hospital addHospital(@RequestBody Hospital hospital) {
        return  service.addHospital(hospital);
    }
}
