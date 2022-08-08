package com.moxe.app.controller;

import com.moxe.app.model.Patient;
import com.moxe.app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Patient Contraller contains endpoints to maintain patient information
 */
@RestController
public class PatientController {

    @Autowired
    PatientService service;

    @GetMapping("/patient")
    public List<Patient> getAllPatients() {
        return service.getAllPatients();
    }

    @GetMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable String id) {
        return service.getPatientById(id);
    }

    @PostMapping("/patient/{name}")
    public Patient createPatient(@RequestBody Patient patient) {
        return  service.addPatient(patient);
    }

    @PutMapping("/patient")
    public Patient updatePatient(@RequestBody Patient patient) {
        return  service.updatePatient(patient);
    }
}
