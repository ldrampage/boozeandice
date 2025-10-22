package com.boozeandice.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bozeandice.vo.SalesReportVO;
import com.bozeandice.vo.MonthlyRecapChartVO;

@Repository
public class ReportChartRepository {
	
	private static final Logger logger = LogManager.getLogger(ReportChartRepository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${schema_name}")
	private String schema;
	
	public List<SalesReportVO> getAllDailySalesReportVO(){
		RowMapper<SalesReportVO> rowMapper = (rs, rowNum) -> {
			
			SalesReportVO dsrVO = new SalesReportVO();
			dsrVO.setDate(rs.getDate("date"));
			dsrVO.setTotal(rs.getString("total_sales"));
			dsrVO.setCashTransaction(rs.getString("total_cash_transactions"));
			dsrVO.setGcashTransaction(rs.getString("total_gcash_transactions"));
			dsrVO.setCardTransaction(rs.getString("total_paymaya_transactions"));
			dsrVO.setCost(rs.getString("total_cost"));
			dsrVO.setProfit(rs.getString("total_profit"));
			dsrVO.setTaxes(rs.getString("vat_amount"));
			dsrVO.setExpenses(rs.getString("total_expense"));
			dsrVO.setNoOfTransactions(rs.getLong("no_of_transactions"));
			dsrVO.setNoOfItems(rs.getLong("no_of_items"));
			return dsrVO;
			 
		};
		
		return jdbcTemplate.query("select date(trans.transaction_date_time) as date, "
				+ "trans.cashdrawer_id, "
				+ "sum(trans.total) as total_sales, "
				+ "sum(case when trans.payment_method = 'CASH' THEN trans.total ELSE 0 END) as total_cash_transactions, "
				+ "sum(case when trans.payment_method = 'GCASH' then trans.total else 0 end) as total_gcash_transactions, "
				+ "sum(case when trans.payment_method = 'PAYMAYA' then trans.total else 0 end) as total_paymaya_transactions, "
				+ "sum(trans.cost) as total_cost, "
				+ "sum(trans.profit) as total_profit, "
				+ "sum(trans.vat_amount) as vat_amount, "
				+ "count(trans.id) as no_of_transactions, "
				+ "sum((select sum(quantity) from "+schema+".transaction_item transItem where trans.id = transItem.transaction_id)) as no_of_items, "
				+ "(select sum(expense) from "+schema+".expenses ex where ex.cash_drawer_id = trans.cashdrawer_id) as total_expense "
				+ "from "+schema+".transaction trans where trans.transaction_status='PAID' "
				+ "group by date(trans.transaction_date_time), trans.cashdrawer_id;", rowMapper);
	}
	
	
	public List<SalesReportVO> getAllMonthlySalesReportVO(){
		RowMapper<SalesReportVO> rowMapper = (rs, rowNum) -> {
			
			SalesReportVO dsrVO = new SalesReportVO();
			dsrVO.setDateString(rs.getString("date"));
			dsrVO.setTotal(rs.getString("total_sales"));
			dsrVO.setCashTransaction(rs.getString("total_cash_transactions"));
			dsrVO.setGcashTransaction(rs.getString("total_gcash_transactions"));
			dsrVO.setCardTransaction(rs.getString("total_paymaya_transactions"));
			dsrVO.setCost(rs.getString("total_cost"));
			dsrVO.setProfit(rs.getString("total_profit"));
			dsrVO.setTaxes(rs.getString("vat_amount"));
			dsrVO.setExpenses(rs.getString("total_expense"));
			dsrVO.setNoOfTransactions(rs.getLong("no_of_transactions"));
			dsrVO.setNoOfItems(rs.getLong("no_of_items"));
			return dsrVO;
			 
		};
		
		return jdbcTemplate.query("select date_format(trans.transaction_date_time, '%Y-%m') as date, "
				+ "sum(trans.total) as total_sales, "
				+ "sum(case when trans.payment_method = 'CASH' THEN trans.total ELSE 0 END) as total_cash_transactions, "
				+ "sum(case when trans.payment_method = 'GCASH' then trans.total else 0 end) as total_gcash_transactions, "
				+ "sum(case when trans.payment_method = 'PAYMAYA' then trans.total else 0 end) as total_paymaya_transactions, "
				+ "sum(trans.cost) as total_cost, "
				+ "sum(trans.profit) as total_profit, "
				+ "sum(trans.vat_amount) as vat_amount, "
				+ "count(trans.id) as no_of_transactions, "
				+ "sum((select sum(quantity) from "+schema+".transaction_item transItem where trans.id = transItem.transaction_id)) as no_of_items, "
				+ "(select sum(expense) from "+schema+".expenses ex where ex.cash_drawer_id = trans.cashdrawer_id) as total_expense "
				+ "from "+schema+".transaction trans where trans.transaction_status='PAID' "
				+ "group by date_format(trans.transaction_date_time, '%Y-%m');", rowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public List<MonthlyRecapChartVO> getMonthlyRecapChartByYear(String year) {
		
		RowMapper<MonthlyRecapChartVO> rowMapper = (rs, rowNum) -> {
			
			MonthlyRecapChartVO mrcVO = new MonthlyRecapChartVO();
			mrcVO.setMonth(rs.getInt("month"));
			mrcVO.setRevenue(rs.getString("revenue"));
			mrcVO.setCost(rs.getString("cost"));
			mrcVO.setProfit(rs.getString("profit"));
			return mrcVO;
		};
		
		return jdbcTemplate.query("select EXTRACT(month from trans.transaction_date_time) as month, "
				+ "sum(trans.total) as revenue, "
				+ "sum(trans.cost) as cost, "
				+ "sum(trans.profit) as profit "
				+ "from "+schema+".transaction trans where trans.transaction_status='PAID' "
				+ "and year(trans.transaction_date_time) = ? "
				+ "group by EXTRACT(month from trans.transaction_date_time);", new Object[] {year}, rowMapper);
		
	}
	
	

}
