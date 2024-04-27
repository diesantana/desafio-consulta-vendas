package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.projections.SaleReportProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	@Transactional(readOnly = true)
	public SaleReportDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleReportDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<SaleReportDTO> getReport(String minDate, String maxDate, String sellerName, Pageable pageable) {
		
		// verificando se as datas estão vazias para aplicar os valores default
		LocalDate startDate;
		LocalDate endDate;
		
		if (!maxDate.isEmpty()) {
			endDate = LocalDate.parse(maxDate);
		} else {
			endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		
		if (!minDate.isEmpty()) {
			startDate = LocalDate.parse(minDate);
		} else {
			startDate = endDate.minusYears(1L);
		}
		Page<SaleReportProjection> pageSeller = repository.salesReport(startDate.toString(), endDate.toString(), sellerName, pageable);
		Page<SaleReportDTO> dto = pageSeller.map(x -> new SaleReportDTO(x));
		return dto;
	}
}
