package com.moxe.app.controller;

import com.moxe.app.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class PatientControllerTests {

    @Autowired
    PatientController controller;

    final String patientId = "82968bc2-b5a2-4528-9c52-fb612986dd52";
    final UUID patientUuid = UUID.fromString(patientId);
    final String hospitalProviderId = "c20f0adf-953f-4f47-b930-c47cdb46fbe6";
    final String firstName = "Damien";
    final String middleName = "Robert";
    final String lastName = "Johnson";
    final String newFirstName = "NewFirstName";
    final String newMiddleName = "NewMiddleName";
    final String newLastName = "NewLastName";

    @Test
    public void getAllPatientsTest() {
        final List<Patient> patients = controller.getAllPatients();
        assertEquals(39, patients.size());
        Patient patient = patients.get(0);
        assertNotNull(patient);
        assertEquals(patientUuid, patient.getId());
        assertEquals(firstName, patient.getFirstName());
        assertEquals(middleName, patient.getMiddleName());
        assertEquals(lastName, patient.getLastName());
    }
    @Test
    public void getAllPatientsByHospitalProviderIdTest() {
        final List<Patient> patients = controller.getAllPatientsByHospitalProviderId(hospitalProviderId);
        assertEquals(4, patients.size());
        Patient patient = patients.get(0);
        assertNotNull(patient);
        assertEquals(patientUuid, patient.getId());
        assertEquals(firstName, patient.getFirstName());
        assertEquals(middleName, patient.getMiddleName());
        assertEquals(lastName, patient.getLastName());
    }

    @Test
    public void getPatientByIdTest() {
        final Patient patient = controller.getPatientById(patientId);
        assertNotNull(patient);
        assertEquals(patientUuid, patient.getId());
        assertEquals(firstName, patient.getFirstName());
        assertEquals(middleName, patient.getMiddleName());
        assertEquals(lastName, patient.getLastName());
    }

    @Test
    public void createPatientTest() {
        Patient newPatient = Patient.builder()
                .firstName(newFirstName)
                .middleName(newMiddleName)
                .lastName(newLastName)
                .build();

        final Patient patient = controller.createPatient(newPatient);
        assertNotNull(patient);
        assertNotNull(patient.getId());
        assertEquals(newFirstName, patient.getFirstName());
        assertEquals(newMiddleName, patient.getMiddleName());
        assertEquals(newLastName, patient.getLastName());
    }

    @Test
    public void updatePatientTest() {
        final Patient patient = controller.getPatientById(patientId);
        assertNotNull(patient);
        assertEquals(patientUuid, patient.getId());
        assertEquals(firstName, patient.getFirstName());
        assertEquals(middleName, patient.getMiddleName());
        assertEquals(lastName, patient.getLastName());

        patient.setFirstName(newFirstName);
        final Patient updatedPatient = controller.updatePatient(patient);
        assertNotNull(updatedPatient);
        assertEquals(patientUuid, updatedPatient.getId());
        assertEquals(newFirstName, updatedPatient.getFirstName());
        assertEquals(middleName, updatedPatient.getMiddleName());
        assertEquals(lastName, updatedPatient.getLastName());
    }
}
