package com.moxe.app.controller;

import com.moxe.app.model.HospitalProvider;
import com.moxe.app.model.Patient;
import com.moxe.app.model.Provider;
import com.moxe.app.model.ProviderPatient;
import com.moxe.app.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProviderContraller provides endpoints to maintoain provider information +
 * patient information relative to providers.
 */
@RestController
public class ProviderController {

    @Autowired
    ProviderService service;

    @GetMapping("/provider")
    public List<Provider> getAllProviders() {
        return service.getAllProviders();
    }

    @GetMapping("/provider/{id}")
    public Provider getProviderById(@PathVariable String id) {
        return service.getProviderById(id);
    }

    @PostMapping("/provider")
    public Provider createProvider(@RequestBody Provider provider) {
        return service.addNewProvider(provider);
    }

    @GetMapping("/hospitalProvider")
    public List<HospitalProvider> getAllHospitalProviders() {
        return service.getAllHospitalProviders();
    }

    @GetMapping("/hospitalProvider/{id}")
    public HospitalProvider getHospitalProviderById(@PathVariable String id) {
        return service.getHospitalProviderById(id);
    }

    @PostMapping("/hospitalProvider")
    public HospitalProvider createHospitalProvider(@RequestBody HospitalProvider hospitalProvider) {
        return service.addHospitalProvider(hospitalProvider);
    }

    @GetMapping("/providerPatient")
    public List<ProviderPatient> getAllProviderPatients(@RequestParam String hospitalProviderId) {
        return service.getAllPatientForProviders(hospitalProviderId);
    }

    @GetMapping("/providerPatient/{id}")
    public ProviderPatient getProviderPatientById(@PathVariable String id) {
        return service.getProviderPatientById(id);
    }

    @PostMapping("/providerPatient/{hospitalProviderId}")
    public ProviderPatient createProviderPatient(@RequestBody Patient patient, @PathVariable String hospitalProviderId) {
        return service.addPatientForHospitalProviderById(patient, hospitalProviderId);
    }

    @DeleteMapping("/providerPatient/{id}")
    public void deactivateProviderPatient(@PathVariable String id) {
        service.deactivatePatientForHospitalProvider(id);
    }
}
