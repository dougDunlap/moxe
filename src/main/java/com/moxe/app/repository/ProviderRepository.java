package com.moxe.app.repository;

import com.moxe.app.model.Hospital;
import com.moxe.app.model.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProviderRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Provider> findAll() {
        final BeanPropertyRowMapper providerRowMapper = new BeanPropertyRowMapper<Provider>(Provider.class);
        return jdbcTemplate.query("select * from provider", providerRowMapper, null);
    }

    public List<Provider> findAllProvidersByHospitalId(UUID hospitalId) {
        // TODO : read query from file
        String query = "select p.id, p.first_name, p.middle_name, p.last_name ";
        query += "from hospital as h, hospital_provider as hp, provider as p ";
        query += "where h.id = hp.hospital_id and hp.provider_id = p.id and h.id = ?";
        final BeanPropertyRowMapper providerRowMapper = new BeanPropertyRowMapper<Provider>(Provider.class);
        List<Provider> providers = jdbcTemplate.query(query, providerRowMapper, hospitalId);
        return providers;
    }

    public Provider findOneById(UUID id) {
        final BeanPropertyRowMapper providerRowMapper = new BeanPropertyRowMapper<Provider>(Provider.class);
        final List<Provider> providers = jdbcTemplate.query("select * from provider where id = ?", providerRowMapper, id);

        // We should verify list is only one item from list then return
        return providers.size() > 0 ? providers.get(0) : null;
    }

    public UUID create(Provider provider) {
        final String updateStatement = "insert into provider (id, first_name, middle_name, last_name) values (?, ?, ?, ?)";
        final UUID id = UUID.randomUUID();
        jdbcTemplate.update(
                updateStatement,
                id,
                provider.getFirstName(),
                provider.getMiddleName(),
                provider.getLastName()
        );

        return id;
    }

}
