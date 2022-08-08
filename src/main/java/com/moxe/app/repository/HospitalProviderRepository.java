package com.moxe.app.repository;

import com.moxe.app.model.HospitalProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class HospitalProviderRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<HospitalProvider> findAll() {
        final BeanPropertyRowMapper hospitalProviderRowMapper = new BeanPropertyRowMapper<HospitalProvider>(HospitalProvider.class);
        return jdbcTemplate.query("select * from hospital_provider", hospitalProviderRowMapper, null);
    }

    public HospitalProvider findOneById(UUID id) {
        final BeanPropertyRowMapper hospitalProviderRowMapper = new BeanPropertyRowMapper<HospitalProvider>(HospitalProvider.class);
        final List<HospitalProvider> hospitalProviders = jdbcTemplate.query("select * from hospital_provider where id = ?", hospitalProviderRowMapper, id);

        // We should verify list is only one item from list then return
        return hospitalProviders.size() > 0 ? hospitalProviders.get(0) : null;
    }

    public HospitalProvider findOneByHospitalIdAndProviderid(UUID hospitalId, UUID providerId) {
        String query = "select * from hospital_provider where hospital_id = ? and provider_id = ?";
        final BeanPropertyRowMapper hospitalProviderRowMapper =
                new BeanPropertyRowMapper<HospitalProvider>(HospitalProvider.class);
        final List<HospitalProvider> hospitalProviders =
                jdbcTemplate.query(query, hospitalProviderRowMapper, hospitalId, providerId);

        // We should verify list is only one item from list then return
        return hospitalProviders.size() > 0 ? hospitalProviders.get(0) : null;
    }

    public void create(HospitalProvider provider) {
        final String updateStatement = "insert into hospital_provider (hospital_id, provider_id) values (?, ?)";
        jdbcTemplate.update(
                updateStatement,
                provider.getHospitalId(),
                provider.getProviderId()
        );
    }
}
