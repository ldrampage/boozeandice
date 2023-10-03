package com.boozeandice.local.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bozeandice.vo.ProductBestSellerVO;

@Repository
public class ProductBestSellerRepository {
	
	private static final Logger logger = LogManager.getLogger(ProductBestSellerRepository.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<ProductBestSellerVO> getBestSellingProductAllTime(){
		RowMapper<ProductBestSellerVO> rowMapper = (rs, rowNum) -> {
			
			ProductBestSellerVO pbsVO = new ProductBestSellerVO();
			pbsVO.setProductId(rs.getString("product_real_id"));
			pbsVO.setImgLocation(rs.getString("img_location"));
			pbsVO.setItemsSold(rs.getString("totalQuantity"));
			pbsVO.setProductName(rs.getString("name"));
			pbsVO.setRevenue(rs.getString("revenue"));
			
			return pbsVO;
			
		};
		
		return jdbcTemplate.query("select p.img_location, ti.product_real_id, SUM(ti.quantity) as totalQuantity, p.name, SUM(ti.product_price * ti.quantity) as revenue from boozeandice.transaction_item ti "
				+ "join boozeandice.product p on ti.product_real_id = p.id "
				+ "where transaction_id in (select id from boozeandice.transaction where transaction_status = 'paid') "
				+ "group by ti.product_real_id order by totalQuantity DESC;", rowMapper);
	}

}
