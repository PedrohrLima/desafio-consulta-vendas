package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

    public List<SaleSummaryDTO> getSummary(LocalDate minDate, LocalDate maxDate) {
        if (maxDate == null) {
            LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
            maxDate = today;
        }
        if (minDate == null) {
            minDate = maxDate.minusYears(1L);
        }
        List<SaleSummaryDTO> result = repository.getSummary(minDate, maxDate);
        return result;
    }

    public List<SaleSummaryDTO> getSummary(String minDate, String maxDate) {
        LocalDate min = (minDate != null) ? LocalDate.parse(minDate) : null;
        LocalDate max = (maxDate != null) ? LocalDate.parse(maxDate) : null;
        return getSummary(min, max);
    }

    public Page<SaleReportDTO> getReport(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable) {
        if (maxDate == null) {
            LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
            maxDate = today;
        }
        if (minDate == null) {
            minDate = maxDate.minusYears(1L);
        }
        if (sellerName == null) {
            sellerName = "";
        }
        Page<SaleReportDTO> result = repository.getReport(minDate, maxDate, sellerName, pageable);
        return result;
    }

    public Page<SaleReportDTO> getReport(String minDate, String maxDate, String sellerName, Pageable pageable) {
        LocalDate min = (minDate != null) ? LocalDate.parse(minDate) : null;
        LocalDate max = (maxDate != null) ? LocalDate.parse(maxDate) : null;

        return getReport(min, max, sellerName, pageable);
    }
}
