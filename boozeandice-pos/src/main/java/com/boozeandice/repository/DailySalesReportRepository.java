package com.boozeandice.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bozeandice.vo.DailySalesReportVO;

@Repository
public class DailySalesReportRepository {
	
	private static final Logger logger = LogManager.getLogger(DailySalesReportRepository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<DailySalesReportVO> getAllDailySalesReportVO(){
		RowMapper<DailySalesReportVO> rowMapper = (rs, rowNum) -> {
			
			DailySalesReportVO dsrVO = new DailySalesReportVO();
			dsrVO.setDate(rs.getDate("date"));
			dsrVO.setTotal(rs.getDouble("total_sales"));
			dsrVO.setCashTransaction(rs.getDouble("total_cash_transactions"));
			dsrVO.setGcashTransaction(rs.getDouble("total_gcash_transactions"));
			dsrVO.setCardTransaction(rs.getDouble("total_paymaya_transactions"));
			dsrVO.setCost(rs.getDouble("total_cost"));
			dsrVO.setProfit(rs.getDouble("total_profit"));
			dsrVO.setTaxes(rs.getDouble("vat_amount"));
			dsrVO.setNoOfTransactions(rs.getLong("no_of_transactions"));
			dsrVO.setNoOfItems(rs.getLong("no_of_items"));


			
			return dsrVO;
			
		};
		
		return jdbcTemplate.query("select date(trans.transaction_date_time) as date, SUM(DISTINCT trans.total) as total_sales, "
				+ "SUM(CASE WHEN trans.payment_method = 'CASH' THEN (transItem.product_price * transItem.quantity) ELSE 0 END) as total_cash_transactions, "
				+ "SUM(CASE WHEN trans.payment_method = 'GCASH' THEN (transItem.product_price * transItem.quantity) ELSE 0 END) as total_gcash_transactions, "
				+ "SUM(CASE WHEN trans.payment_method = 'PAYMAYA' THEN (transItem.product_price * transItem.quantity) ELSE 0 END) as total_paymaya_transactions, "
				+ "SUM(CASE WHEN trans.id = transItem.transaction_id THEN (transItem.produc_cost * transItem.quantity) ELSE 0 END) as total_cost, "
				+ "SUM(CASE WHEN trans.id = transItem.transaction_id THEN (transItem.product_price * transItem.quantity - transItem.produc_cost * transItem.quantity) ELSE 0 END) as total_profit, "
				+ "SUM(DISTINCT trans.vat_amount) as vat_amount, "
				+ "count(DISTINCT trans.id) as no_of_transactions, "
				+ "SUM(transItem.quantity) as no_of_items "
				+ "from boozeandice.transaction trans join boozeandice.transaction_item transItem ON trans.id = transItem.transaction_id "
				+ "group by date(trans.transaction_date_time) "
				+ "order by date(trans.transaction_date_time);", rowMapper);
	}

}
