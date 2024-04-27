package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleReportDTO> findById(@PathVariable Long id) {
		SaleReportDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(
			@RequestParam(value="minDate", defaultValue = "") String minDate,
			@RequestParam(value="maxDate", defaultValue = "") String maxDate,
			@RequestParam(value="name", defaultValue = "")String sellerName,
			Pageable pageable) {
		
		Page<SaleReportDTO> result = service.getReport(minDate, maxDate, sellerName, pageable);
		return ResponseEntity.ok(result);
	}

	// 1. [IN] O usuário informa, opcionalmente, data inicial, data final.
	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate
	) {
		List<SaleSummaryDTO> result = service.getSummary(minDate, maxDate);
		return ResponseEntity.ok(result);
	}
}
