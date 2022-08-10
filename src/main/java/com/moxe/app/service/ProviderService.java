package com.moxe.app.service;

import com.moxe.app.model.HospitalProvider;
import com.moxe.app.model.Patient;
import com.moxe.app.model.Provider;
import com.moxe.app.model.ProviderPatient;
import com.moxe.app.repository.PatientRepository;
import com.moxe.app.repository.ProviderRepository;
import com.moxe.app.repository.ProviderPatientRepository;
import com.moxe.app.repository.HospitalProviderRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProviderService {

    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    HospitalProviderRepository hospitalProviderRepository;
    @Autowired
    ProviderPatientRepository providerPatientRepository;
    @Autowired
    PatientRepository patientRepository;

    /**
     * Get Provider details independent of any hospital
     *
     * @return List<Provider>
     */
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    /**
     * Get providers for a hospital given hospital id
     *
     * @param id
     * @return List<Provider>
     */
    public List<Provider> getProvidersByHospitalId(String id) {
        final UUID hospitalId = UUID.fromString(id);
        return providerRepository.findAllProvidersByHospitalId(hospitalId);
    }

    /**
     * Get Provider by id
     *
     * @param id
     * @return Provider
     */
    public Provider getProviderById(String id) {
        final UUID providerId = UUID.fromString(id);
        return providerRepository.findOneById(providerId);
    }

    /**
     * Add new Provider
     * @param provider
     * @return Provider
     */
    public Provider addNewProvider(Provider provider) {
        final UUID id = providerRepository.create(provider);

        // We need to retrieve item after inserting, as returning object is not handled by
        // creation from spring
        return providerRepository.findOneById(id);
    }

    /**
     * Get all Hospital Providers for a given hospital
     * @return List<HospitalProvider>
     */
    public List<HospitalProvider> getAllHospitalProviders() {
        return hospitalProviderRepository.findAll();
    }

    /**
     * Get all Hospital Providers by hosital id
     *
     * @param id
     * @return List<HospitalProvider>
     */
    public List<HospitalProvider> getAllHospitalProvidersByHospitalId(String id) {
        final UUID hospitalId = UUID.fromString(id);
        return hospitalProviderRepository.findAllHospitaLProvidersByHospitalId(hospitalId);
    }

    /**
     * Get Hospital Provider by id
     *
     * @param id
     * @return HospitalProvider
     */
    public HospitalProvider getHospitalProviderById(String id) {
        final UUID hospitalProviderId = UUID.fromString(id);
        return hospitalProviderRepository.findOneById(hospitalProviderId);
    }

    /**
     * Add Hospital Provider
     *
     * @param hospitalProvider
     * @return HospitalProvider
     */
    public HospitalProvider addHospitalProvider(HospitalProvider hospitalProvider) {
        hospitalProviderRepository.create(hospitalProvider);
        return hospitalProviderRepository
                .findOneByHospitalIdAndProviderid(hospitalProvider.getHospitalId(), hospitalProvider.getProviderId());
    }

    /**
     * Patient details for a practice given hospital association
     *
     * @param hospitalProviderId
     * @return List<ProviderPatient>
     */
    public List<ProviderPatient> getAllPatientForProviders(String hospitalProviderId){
        if (StringUtils.isEmpty(hospitalProviderId)) {
            return providerPatientRepository.findAll();
        }
        final UUID hospitalProviderUuid = UUID.fromString(hospitalProviderId);
        return providerPatientRepository.findAllForHospitalProviderId(hospitalProviderUuid);
    }
    public List<ProviderPatient> getAllPatientForProviders() {
        return providerPatientRepository.findAll();
    }

    /**
     * Get Provider Patient by id
     *
     * @param id
     * @return ProviderPatient
     */
    public ProviderPatient getProviderPatientById(String id){
        final UUID uuid = UUID.fromString(id);
        return providerPatientRepository.findOneById(uuid);
    }

    /**
     * Add Patient for Hoospital Provider by id
     *
     * @param patient
     * @param id
     * @return ProviderPatient
     */
    public ProviderPatient addPatientForHospitalProviderById(Patient patient, String id) {
        final UUID hospitalProviderId = UUID.fromString(id);
        UUID patientId = patient.getId();

        // We can be passed a new patient who has never been in system.  We need to make sure to add them
        if (patientId == null) {
            patientId = patientRepository.create(patient);
        }
        providerPatientRepository.create(patientId, hospitalProviderId);

        // We need to retrieve item after inserting, as returning object is not handled by
        // creation from spring
        return providerPatientRepository.findOneByPatientIdHospitalId(patientId, hospitalProviderId);
    }

    /**
     * Deactivate Patient for Hosptial Provider.
     *
     * Soft deletes should probably be preferred over deletes.
     *
     * @param patientProviderId
     */
    public void deactivatePatientForHospitalProvider(String patientProviderId) {
        final UUID patientProviderUuid = UUID.fromString(patientProviderId);
        providerPatientRepository.update(false, patientProviderUuid);
    }
}
