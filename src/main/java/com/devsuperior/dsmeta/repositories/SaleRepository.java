package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(se.name, SUM(sa.amount) ) " +
            "FROM Sale sa  JOIN sa.seller se " +
            "WHERE sa.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY se.name")
    List<SaleSummaryDTO> getSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(sa.id, sa.date, sa.amount, se.name) " +
            "FROM Sale sa  JOIN sa.seller se " +
            "WHERE UPPER(se.name) LIKE UPPER(CONCAT('%', :sellerName, '%')) " +
            "AND sa.date BETWEEN :minDate AND :maxDate" )
    Page<SaleReportDTO> getReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("sellerName") String sellerName, Pageable pageable);

}
