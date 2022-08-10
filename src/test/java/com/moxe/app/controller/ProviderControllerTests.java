package com.moxe.app.controller;

import com.moxe.app.model.HospitalProvider;
import com.moxe.app.model.Patient;
import com.moxe.app.model.Provider;
import com.moxe.app.model.ProviderPatient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class ProviderControllerTests {

    @Autowired
    ProviderController controller;
    @Autowired
    PatientController patientController;

    final String providerId = "fa99ff91-b3a4-49ba-9288-f698629504db";
    final UUID providerUuid = UUID.fromString(providerId);
    final String hospitalId = "42460d02-b1b1-4415-aa4f-ba54d933e6b8";
    final UUID hospitalUuid = UUID.fromString(hospitalId);
    final String hospitalProviderId = "c20f0adf-953f-4f47-b930-c47cdb46fbe6";
    final UUID hospitalProviderUuid = UUID.fromString(hospitalProviderId);
    final String patientId = "82968bc2-b5a2-4528-9c52-fb612986dd52";
    final UUID patientUuid = UUID.fromString(patientId);
    final String providerPatientId = "c3f9eecb-af19-4feb-b929-a9b107de5694";
    final UUID providerPatientUuid = UUID.fromString(providerPatientId);
    final String secondproviderPatientId = "12b68363-7b4e-41ad-8a05-205cdc23f6c0";
    final UUID secondproviderPatientUuid = UUID.fromString(secondproviderPatientId);
    final String secondPatientId = "a79c837d-df98-48c6-824d-246ca50a3eff";
    final UUID secondPatientUuid = UUID.fromString(secondPatientId);
    final String firstName = "Adam";
    final String middleName = "David";
    final String lastName = "Jones";
    final String newFirstName = "NewFirstName";
    final String newMiddleName = "NewMiddleName";
    final String newLastName = "NewLastName";

    @Test
    public void getAllProvidersTest() {
        final List<Provider> providers = controller.getAllProviders();
        assertEquals(8, providers.size());
        Provider provider = providers.get(0);
        assertNotNull(provider);
        assertEquals(providerUuid, provider.getId());
        assertEquals(firstName, provider.getFirstName());
        assertEquals(middleName, provider.getMiddleName());
        assertEquals(lastName, provider.getLastName());
    }

    @Test
    public void getProvidersByHospitalIdTest() {
        final List<Provider> providers = controller.getProvidersByHospitalId(hospitalId);
        assertEquals(4, providers.size());
        Provider provider = providers.get(3);
        assertNotNull(provider);
        assertEquals(providerUuid, provider.getId());
        assertEquals(firstName, provider.getFirstName());
        assertEquals(middleName, provider.getMiddleName());
        assertEquals(lastName, provider.getLastName());
    }

    @Test
    public void getProviderByIdTest() {
        final Provider provider = controller.getProviderById(providerId);
        assertNotNull(provider);
        assertEquals(providerUuid, provider.getId());
        assertEquals(firstName, provider.getFirstName());
        assertEquals(middleName, provider.getMiddleName());
        assertEquals(lastName, provider.getLastName());
    }

    @Test
    public void postProviderByNameTest() {
        Provider newProvider = Provider.builder()
                .firstName(newFirstName)
                .middleName(newMiddleName)
                .lastName(newLastName)
                .build();

        final Provider provider = controller.createProvider(newProvider);
        assertNotNull(provider);
        assertNotNull(provider.getId());
        assertEquals(newFirstName, provider.getFirstName());
        assertEquals(newMiddleName, provider.getMiddleName());
        assertEquals(newLastName, provider.getLastName());
    }

    @Test
    public void getAllHospitalProvidersTest() {
        final List<HospitalProvider> hospitalProviders = controller.getAllHospitalProviders();
        assertEquals(14, hospitalProviders.size());
        HospitalProvider hospitalProvider = hospitalProviders.get(0);
        assertNotNull(hospitalProvider);
        assertEquals(hospitalProviderUuid, hospitalProvider.getId());
        assertEquals(hospitalUuid, hospitalProvider.getHospitalId());
        assertEquals(providerUuid, hospitalProvider.getProviderId());
    }

    @Test
    public void getAllHospitalProvidersByHospitalIdTest() {
        final List<HospitalProvider> hospitalProviders = controller.getAllHospitalProvidersByHospitalId(hospitalId);
        assertEquals(4, hospitalProviders.size());
        HospitalProvider hospitalProvider = hospitalProviders.get(0);
        assertNotNull(hospitalProvider);
        assertEquals(hospitalProviderUuid, hospitalProvider.getId());
        assertEquals(hospitalUuid, hospitalProvider.getHospitalId());
        assertEquals(providerUuid, hospitalProvider.getProviderId());
    }

    @Test
    public void getAllHospitalProviderByIdTest() {
        final HospitalProvider hospitalProvider = controller.getHospitalProviderById(hospitalProviderId);
        assertNotNull(hospitalProvider);
        assertEquals(hospitalProviderUuid, hospitalProvider.getId());
        assertEquals(hospitalUuid, hospitalProvider.getHospitalId());
        assertEquals(providerUuid, hospitalProvider.getProviderId());
    }

    @Test
    public void createHospitalProviderTest() {
        final UUID newHospitalUuid = UUID.fromString("42460d02-b1b1-4415-aa4f-ba54d933e6b8");
        final UUID newProviderUuid = UUID.fromString("f4881b43-33dc-4b9f-854f-cb8a704a91b5");
        HospitalProvider newHospitalProvider = HospitalProvider.builder()
                .hospitalId(newHospitalUuid)
                .providerId(newProviderUuid)
                .build();

        final HospitalProvider hospitalProvider = controller.createHospitalProvider(newHospitalProvider);
        assertNotNull(hospitalProvider);
        assertNotNull(hospitalProvider.getId());
        assertEquals(newHospitalUuid, hospitalProvider.getHospitalId());
        assertEquals(newProviderUuid, hospitalProvider.getProviderId());
    }

    @Test
    public void getAllProviderPatientsTest() {
        final List<ProviderPatient> providerPatients = controller.getAllProviderPatients(null);
        assertEquals(47, providerPatients.size());
        ProviderPatient providerPatient = providerPatients.get(1);
        assertNotNull(providerPatient);
        assertEquals(secondproviderPatientUuid, providerPatient.getId());
        assertEquals(secondPatientUuid, providerPatient.getPatientId());
        assertEquals(hospitalProviderUuid, providerPatient.getHospitalProviderId());
        assertTrue(providerPatient.isActive());
    }

    @Test
    public void getAllProvidersForHospitalProviderPatientTest() {
        final List<ProviderPatient> providerPatients = controller.getAllProviderPatients(hospitalProviderId);
        assertEquals(4, providerPatients.size());
        ProviderPatient providerPatient = providerPatients.get(0);
        assertNotNull(providerPatient);
        assertEquals(providerPatientUuid, providerPatient.getId());
        assertEquals(patientUuid, providerPatient.getPatientId());
        assertEquals(hospitalProviderUuid, providerPatient.getHospitalProviderId());
        assertTrue(providerPatient.isActive());
    }

    @Test
    public void createPatientTest() {

        final String existingPatientId = "22ae2661-638b-482d-a6e8-ca8819748aea";
        Patient patient = patientController.getPatientById(existingPatientId);
        assertNotNull(patient);
        assertEquals(UUID.fromString(existingPatientId), patient.getId());

        ProviderPatient providerPatient = controller.createProviderPatient(patient, hospitalProviderId);
        assertNotNull(providerPatient);
        assertNotNull(providerPatient.getId());
        assertEquals(UUID.fromString(existingPatientId), providerPatient.getPatientId());
        assertEquals(hospitalProviderUuid, providerPatient.getHospitalProviderId());
        assertTrue(providerPatient.isActive());
    }

    @Test
    public void createPatientWithoutIdTest() {
        String firstName = "firstName";
        String middleName = "middleName";
        String lastName = "lastName";

        Patient patient = Patient.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .build();

        ProviderPatient providerPatient = controller.createProviderPatient(patient, hospitalProviderId);
        assertNotNull(providerPatient);
        assertNotNull(providerPatient.getId());
        UUID newPatientId = providerPatient.getPatientId();
        assertNotNull(newPatientId);
        assertEquals(hospitalProviderUuid, providerPatient.getHospitalProviderId());
        assertTrue(providerPatient.isActive());

        Patient newPatient = patientController.getPatientById(newPatientId.toString());
        assertNotNull(newPatient);
        assertEquals(newPatientId, newPatient.getId());
        assertEquals(firstName, newPatient.getFirstName());
        assertEquals(middleName, newPatient.getMiddleName());
        assertEquals(lastName, newPatient.getLastName());
    }

    @Test
    public void getProviderPatientByIdTest() {
        final ProviderPatient providerPatient = controller.getProviderPatientById(providerPatientId);
        assertNotNull(providerPatient);
        assertEquals(providerPatientUuid, providerPatient.getId());
        assertEquals(patientUuid, providerPatient.getPatientId());
        assertEquals(hospitalProviderUuid, providerPatient.getHospitalProviderId());
        assertTrue(providerPatient.isActive());
    }

    @Test
    public void deactivateProviderPatientTest() {
        final ProviderPatient providerPatient = controller.getProviderPatientById(providerPatientId);
        assertNotNull(providerPatient);
        assertEquals(providerPatientUuid, providerPatient.getId());
        assertEquals(patientUuid, providerPatient.getPatientId());
        assertEquals(hospitalProviderUuid, providerPatient.getHospitalProviderId());
        assertTrue(providerPatient.isActive());

        controller.deactivateProviderPatient(providerPatientId);

        final ProviderPatient updatedProviderPatient = controller.getProviderPatientById(providerPatientId);
        assertNotNull(updatedProviderPatient);
        assertEquals(providerPatientUuid, updatedProviderPatient.getId());
        assertEquals(patientUuid, updatedProviderPatient.getPatientId());
        assertEquals(hospitalProviderUuid, updatedProviderPatient.getHospitalProviderId());
        assertFalse(updatedProviderPatient.isActive());
    }
}
