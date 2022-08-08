package com.moxe.app.controller;

import com.moxe.app.model.Hospital;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class HospitalControllerTest {

    @Autowired
    private HospitalController controller;

    private final String hospitalId = "be5f08d8-428e-4f32-8344-f7e1e796218a";
    private final UUID hospitalUuid = UUID.fromString(hospitalId);
    private final String hospitalName = "Tufts";

    @Test
    public void getAllHospitalsTest() {
        final List<Hospital> hospitals = controller.getAllHospitals();
        assertEquals(4, hospitals.size());
        Hospital hospital = hospitals.get(0);
        assertNotNull(hospital);
        assertNotNull(hospital.getId());
        assertNotNull(hospital.getName());
    }

    @Test
    public void getHospitalByIdTest() {
        final Hospital hospital = controller.getHospitalById(hospitalId);
        assertNotNull(hospital);
        assertEquals(hospitalUuid, hospital.getId());
        assertEquals(hospitalName, hospital.getName());
    }

    @Test
    public void getHospitalByNameTest() {
        final Hospital hospital = controller.getHospitalByName(hospitalName);
        assertNotNull(hospital);
        assertEquals(hospitalUuid, hospital.getId());
        assertEquals(hospitalName, hospital.getName());
    }

    @Test
    public void addHospitalTest() {
        final String newHospitalName = "New Hospital Name";
        final Hospital newHospital = Hospital.builder().name(newHospitalName).build();

        final Hospital originalHospital = controller.getHospitalByName(newHospitalName);
        assertNull(originalHospital);

        final Hospital hospital = controller.addHospital(newHospital);
        assertNotNull(hospital);
        assertNotNull(hospital.getId());
        assertEquals(newHospitalName, hospital.getName());
    }
}
