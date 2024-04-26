package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleMinProjection;

public class SaleMinDTO {

	private Long id;
	private Double amount;
	private LocalDate date;
	private String name;
	
	public SaleMinDTO(Long id, Double amount, LocalDate date, String seller) {
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.name = seller;
	}
	
	public SaleMinDTO(Sale entity) {
		id = entity.getId();
		amount = entity.getAmount();
		date = entity.getDate();
		name = entity.getSeller().getName();
	}	
	
	public SaleMinDTO(SaleMinProjection projection) {
		id = projection.getId();
		amount = projection.getAmount();
		date = projection.getDate();
		name = projection.getName();
	}

	public Long getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getSeller() {
		return name;
	}
}
