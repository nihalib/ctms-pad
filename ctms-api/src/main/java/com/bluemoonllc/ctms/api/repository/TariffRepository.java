package com.bluemoonllc.ctms.api.repository;

import com.bluemoonllc.ctms.api.dao.TariffDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<TariffDao, Long> {
    Optional<TariffDao> findByLocation(String location);
}
