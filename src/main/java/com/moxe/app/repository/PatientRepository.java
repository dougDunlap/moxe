package com.moxe.app.repository;

import com.moxe.app.model.Hospital;
import com.moxe.app.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PatientRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Patient> findAll() {
        final BeanPropertyRowMapper patientRowMapper = new BeanPropertyRowMapper<Patient>(Patient.class);
        return jdbcTemplate.query("select * from patient", patientRowMapper, null);
    }

    public List<Patient> findAllPatientsByHospitalProviderId(UUID hospital_provider_id) {
        // TODO : read query from file
        String query = "select p.id, p.first_name, p.middle_name, p.last_name ";
        query += "from provider_patient as pp, patient as p ";
        query += "where p.id = pp.patient_id and pp.hospital_provider_id = ?";
        final BeanPropertyRowMapper patientRowMapper = new BeanPropertyRowMapper<Patient>(Patient.class);
        List<Patient> patients = jdbcTemplate.query(query, patientRowMapper, hospital_provider_id);
        return patients;
    }

    public Patient findOneById(UUID id) {
        final BeanPropertyRowMapper patientRowMapper = new BeanPropertyRowMapper<Patient>(Patient.class);
        final List<Patient> patients = jdbcTemplate.query("select * from patient where id = ?", patientRowMapper, id);

        // We should verify list is only one item from list then return
        return patients.size() > 0 ? patients.get(0) : null;
    }

    public void update(Patient patient) {
        final String updateStatement = "update patient set first_name = ?, middle_name = ?, last_name = ? where id = ?";
        jdbcTemplate.update(
                updateStatement,
                patient.getFirstName(),
                patient.getMiddleName(),
                patient.getLastName(),
                patient.getId()
        );
    }

    public UUID create(Patient patient) {
        final String updateStatement = "insert into patient (id, first_name, middle_name, last_name) values (?, ?, ?, ?)";

        // uuid's are not passed back by spring ( only longs ).  There is limited risk here in
        // creating duplicate uuids, so we would need to consider how cautious we need to be.
        final UUID id = UUID.randomUUID();
        jdbcTemplate.update(
                updateStatement,
                id,
                patient.getFirstName(),
                patient.getMiddleName(),
                patient.getLastName()
        );

        return id;
    }
}
