package com.moxe.app.repository;

import com.moxe.app.model.ProviderPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProviderPatientRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ProviderPatient> findAll() {
        final BeanPropertyRowMapper providerPatientRowMapper = new BeanPropertyRowMapper<ProviderPatient>(ProviderPatient.class);
        return jdbcTemplate.query("select * from provider_patient", providerPatientRowMapper, null);
    }

    public ProviderPatient findOneById(UUID id) {
        final String query = "select * from provider_patient where id = ?";
        final BeanPropertyRowMapper providerPatientRowMapper = new BeanPropertyRowMapper<ProviderPatient>(ProviderPatient.class);
        final List<ProviderPatient> providerPatients = jdbcTemplate.query("select * from provider_patient where id = ?", providerPatientRowMapper, id);

        // We should verify list is only one item from list then return
        return providerPatients.size() > 0 ? providerPatients.get(0) : null;
    }

    public ProviderPatient findOneByPatientIdHospitalId(UUID patientId, UUID hospitalProviderId) {
        final String query = "select * from provider_patient where patient_id = ? and hospital_provider_id = ?";
        final BeanPropertyRowMapper providerPatientRowMapper = new BeanPropertyRowMapper<ProviderPatient>(ProviderPatient.class);
        final List<ProviderPatient> providerPatients = jdbcTemplate.query(
                query, providerPatientRowMapper, patientId, hospitalProviderId);

        // We should verify list is only one item from list then return
        return providerPatients.size() > 0 ? providerPatients.get(0) : null;
    }

    public List<ProviderPatient> findAllForHospitalProviderId(UUID hospitalProviderId) {
        final BeanPropertyRowMapper providerPatientRowMapper = new BeanPropertyRowMapper<ProviderPatient>(ProviderPatient.class);
        return jdbcTemplate.query("select * from provider_patient where hospital_provider_id = ?", providerPatientRowMapper, hospitalProviderId);
    }

    public void update(boolean activeStatus, UUID id) {
        final int status = jdbcTemplate.update("update provider_patient set active = ? where id = ?", activeStatus, id);
        // need to add exception + handle
    }

    public void create(UUID patientId, UUID hospitalProviderId) {
        final int status = jdbcTemplate.update("insert into provider_patient(patient_id, hospital_provider_id) values(?, ?)", patientId, hospitalProviderId);
        // need to add exception + handle
    }
}
