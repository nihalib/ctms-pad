package com.bluemoonllc.ctms.api.repository;

import com.bluemoonllc.ctms.api.dao.TariffDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<TariffDao, Long> {
    Optional<TariffDao> findByLocation(String location);
    @Query("select t from TariffDao t where t.isTestData = :isTestData")
    Page<TariffDao> findTariffDaoByIsTestData(@Param("isTestData") boolean isTestData, Pageable pageRequest);
}
