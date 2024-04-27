package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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
		LocalDate[] dates = getMinAndMaxDates(minDate, maxDate);
		
		LocalDate startDate = dates[0];
		LocalDate endDate = dates[1];

		Page<SaleReportDTO> pageDTO = repository.salesReport(startDate.toString(), endDate.toString(), sellerName, pageable)
				.map(x -> new SaleReportDTO(x));
		return pageDTO;
	}

	@Transactional(readOnly = true)
	public List<SaleSummaryDTO> getSummary(String minDate, String maxDate) {
		// verificando se as datas estão vazias para aplicar os valores default
		LocalDate[] dates = getMinAndMaxDates(minDate, maxDate);

		LocalDate startDate = dates[0];
		LocalDate endDate = dates[1];

		List<SaleSummaryDTO> listDTO = repository.salesSummary(startDate.toString(), endDate.toString())
				.stream().map(x -> new SaleSummaryDTO(x)).toList();

		return listDTO;
	}

	
	
	// verificando se as datas estão vazias para aplicar os valores default
	private LocalDate[] getMinAndMaxDates(String minDate, String maxDate) {
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
		return new LocalDate[]{startDate, endDate};
	}
}
