package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = 
            "SELECT tb_sales.id, tb_sales.date, tb_sales.amount ,tb_seller.name AS sellerName " +
            "FROM tb_sales " +
            "JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(tb_seller.name) LIKE UPPER(CONCAT('%',:sellerName, '%'))")
    Page<SaleReportProjection> salesReport(String minDate, String maxDate, String sellerName, Pageable pageable);

    @Query(nativeQuery = true, value =
            "SELECT SUM(tb_sales.amount) AS total, tb_seller.name AS sellerName " + 
            "FROM tb_sales " +
            "JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.id " +
            "ORDER BY tb_seller.name")
    List<SaleSummaryProjection> salesSummary(String minDate, String maxDate);
}
