package com.bluemoonllc.ctms.api.repository;

import com.bluemoonllc.ctms.api.dao.StationDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<StationDao, Long> {
    Optional<StationDao> findByStationId(Long stationId);
    @Query("select s from StationDao s where s.isTestData = :isTestData")
    Page<StationDao> findStationDaoByIsTestData(@Param("isTestData") boolean isTestData, Pageable pageRequest);
}
