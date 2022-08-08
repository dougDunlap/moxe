package com.moxe.app.repository;

import com.moxe.app.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class HospitalRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Hospital> findAll() {
        final BeanPropertyRowMapper hospitalRowMapper = new BeanPropertyRowMapper<Hospital>(Hospital.class);
        List<Hospital> hospitals = jdbcTemplate.query("select * from hospital", hospitalRowMapper, null);
        return hospitals;
    }

    public Hospital findOneById(UUID id) {
        final BeanPropertyRowMapper hospitalRowMapper = new BeanPropertyRowMapper<Hospital>(Hospital.class);
        final List<Hospital> hospitals = jdbcTemplate.query("select * from hospital where id = ?", hospitalRowMapper, id);

        // We should verify list is only one item from list then return
        return hospitals.size() > 0 ? hospitals.get(0) : null;
    }

    public Hospital findOneByName(String name) {
        final BeanPropertyRowMapper hospitalRowMapper = new BeanPropertyRowMapper<Hospital>(Hospital.class);
        final List<Hospital> hospitals = jdbcTemplate.query("select * from hospital where name = ?", hospitalRowMapper, name);

        // We should verify list is only one item from list then return
        return hospitals.size() > 0 ? hospitals.get(0) : null;
    }


    public Hospital create(Hospital hospital) {
        final String updateStatement = "insert into hospital (name) values (?)";
        jdbcTemplate.update(updateStatement, hospital.getName());

        return findOneByName(hospital.getName());
    }
}
