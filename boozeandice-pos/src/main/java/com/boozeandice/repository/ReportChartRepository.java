package com.boozeandice.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bozeandice.vo.DailySalesReportVO;
import com.bozeandice.vo.MonthlyRecapChartVO;

@Repository
public class ReportChartRepository {
	
	private static final Logger logger = LogManager.getLogger(ReportChartRepository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${schema_name}")
	private String schema;
	
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
		
		return jdbcTemplate.query("select date(trans.transaction_date_time) as date, SUM(trans.total) as total_sales, "
				+ "SUM(CASE WHEN trans.payment_method = 'CASH' THEN (transItem.product_price * transItem.quantity) ELSE 0 END) as total_cash_transactions, "
				+ "SUM(CASE WHEN trans.payment_method = 'GCASH' THEN (transItem.product_price * transItem.quantity) ELSE 0 END) as total_gcash_transactions, "
				+ "SUM(CASE WHEN trans.payment_method = 'PAYMAYA' THEN (transItem.product_price * transItem.quantity) ELSE 0 END) as total_paymaya_transactions, "
				+ "SUM(CASE WHEN trans.id = transItem.transaction_id THEN (transItem.produc_cost * transItem.quantity) ELSE 0 END) as total_cost, "
				+ "SUM(CASE WHEN trans.id = transItem.transaction_id THEN (transItem.product_price * transItem.quantity - transItem.produc_cost * transItem.quantity) ELSE 0 END) as total_profit, "
				+ "SUM(DISTINCT trans.vat_amount) as vat_amount, "
				+ "count(DISTINCT trans.id) as no_of_transactions, "
				+ "SUM(transItem.quantity) as no_of_items "
				+ "from "+ schema +".transaction trans join "+schema+".transaction_item transItem ON trans.id = transItem.transaction_id "
				+ "group by date(trans.transaction_date_time) "
				+ "order by date(trans.transaction_date_time);", rowMapper);
	}
	
	public List<MonthlyRecapChartVO> getMonthlyRecapChartByYear(String year) {
		
		RowMapper<MonthlyRecapChartVO> rowMapper = (rs, rowNum) -> {
			
			MonthlyRecapChartVO mrcVO = new MonthlyRecapChartVO();
			mrcVO.setMonth(rs.getInt("month"));
			mrcVO.setRevenue(rs.getString("revenue"));
			mrcVO.setCost(rs.getString("cost"));
			mrcVO.setProfit(rs.getString("profit"));
			return mrcVO;
		};
		
		return jdbcTemplate.query("select EXTRACT(month from transaction.transaction_date_time) as month, "
				+ "SUM(transaction.total) as revenue, "
				+ "SUM(transItem.produc_cost * transItem.quantity) as cost, "
				+ "SUM(transaction.total) - SUM(transItem.produc_cost * transItem.quantity) as profit "
				+ "from "+schema+".transaction transaction "
				+ "join "+schema+".transaction_item transItem "
				+ "on transaction.id  = transItem.transaction_id "
				+ "where "
				+ "extract(year from transaction.transaction_date_time) = " + year + " "
				+ "and transaction.transaction_status = 'PAID' " 
				+ "group by EXTRACT(month from transaction.transaction_date_time);", rowMapper);
		
	}

}
