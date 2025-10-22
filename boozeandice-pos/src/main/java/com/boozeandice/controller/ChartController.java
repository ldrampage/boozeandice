package com.boozeandice.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.repository.ProductBestSellerRepository;
import com.boozeandice.repository.ReportChartRepository;
import com.bozeandice.vo.MonthlyRecapChartVO;
import com.bozeandice.vo.ProductBestSellerVO;

@Controller
@RequestMapping(path="/chart")
@Secured({"ROLE_ADMIN","ROLE_SUPERVISOR"})
public class ChartController {
	
	private static final Logger logger = LogManager.getLogger(ChartController.class);
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private ReportChartRepository reportChartRepo;
	
	@Autowired
	private ProductBestSellerRepository productBestSellerRepo;
	
	@PostMapping(path="/")
	public String chartPageProcess(Model model, @RequestParam Map<String,String> parameters) {
		if(parameters.get("mrcfty_date_submit") != null) {
			logger.debug(parameters.get("mrcfty_date"));
			model.addAttribute("mrcfty_date", parameters.get("mrcfty_date"));
		}
		if(parameters.get("mbsp_date_submit") != null) {
			logger.debug(parameters.get("mbsp_date"));
			model.addAttribute("mbsp_date", parameters.get("mbsp_date"));
		}
		return chartPage(model);
	}
	
	@GetMapping(path="/")
	public String chartPage(Model model) {
		//Monthly Recap Chart Start
		List<MonthlyRecapChartVO> monthlyRecapChartVoList = null;
		if(model.getAttribute("mrcfty_date") != null) {
			monthlyRecapChartVoList = reportChartRepo.getMonthlyRecapChartByYear(model.getAttribute("mrcfty_date").toString());
		} else {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			model.addAttribute("mrcfty_date", year);
			monthlyRecapChartVoList = reportChartRepo.getMonthlyRecapChartByYear(String.valueOf(year));
		}
		
		Double totalRevenue = 0.0;
		Double totalCost = 0.0;
		Double totalProfit = 0.0;
		
		for(MonthlyRecapChartVO mrcVO : monthlyRecapChartVoList) {
			if(mrcVO.getMonth() == 1) 
				model.addAttribute("monthlyRecapJanuary", mrcVO);
			else if (mrcVO.getMonth() == 2)
				model.addAttribute("monthlyRecapFebruary", mrcVO);
			else if (mrcVO.getMonth() == 3)
				model.addAttribute("monthlyRecapMarch", mrcVO);
			else if (mrcVO.getMonth() == 4)
				model.addAttribute("monthlyRecapApril", mrcVO);
			else if (mrcVO.getMonth() == 5)
				model.addAttribute("monthlyRecapMay", mrcVO);
			else if (mrcVO.getMonth() == 6)
				model.addAttribute("monthlyRecapJune", mrcVO);
			else if (mrcVO.getMonth() == 7)
				model.addAttribute("monthlyRecapJuly", mrcVO);
			else if (mrcVO.getMonth() == 8)
				model.addAttribute("monthlyRecapAugust", mrcVO);
			else if (mrcVO.getMonth() == 9)
				model.addAttribute("monthlyRecapSeptember", mrcVO);
			else if (mrcVO.getMonth() == 10)
				model.addAttribute("monthlyRecapOctober", mrcVO);
			else if (mrcVO.getMonth() == 11)
				model.addAttribute("monthlyRecapNovember", mrcVO);
			else if (mrcVO.getMonth() == 12)
				model.addAttribute("monthlyRecapDecember", mrcVO);
			
			totalRevenue = totalRevenue + Double.valueOf(mrcVO.getRevenue());
			totalCost = totalCost + Double.valueOf(mrcVO.getCost());
			totalProfit = totalProfit + Double.valueOf(mrcVO.getProfit());
			
			model.addAttribute("totalRevenue", String.format("%,.2f", totalRevenue));
			model.addAttribute("totalCost", String.format("%,.2f", totalCost));
			model.addAttribute("totalProfit", String.format("%,.2f", totalProfit) );
			
			logger.debug("month: " + mrcVO.getMonth());
			logger.debug("revenue: " + mrcVO.getRevenue());
			logger.debug("cost: " + mrcVO.getCost());
			logger.debug("profit: " + mrcVO.getProfit());
		}
		//Monthly Recap Chart End
		
		// All Time Best Selling Product Start
		List<ProductBestSellerVO> pbsVO = productBestSellerRepo.getBestSellingProductAllTime();
		model.addAttribute("productAllTimeBestSeller", pbsVO);
		// All Time Best Selling Product End
		
		//Monthly Best Selling Product Start
		List<ProductBestSellerVO> mbspList = null;
		if(model.getAttribute("mbsp_date") != null) {
			mbspList = productBestSellerRepo.monthlyBestSellingProduct(model.getAttribute("mbsp_date").toString());
		} else {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			String mbsp_date = month + "-" + year;
			logger.debug(mbsp_date);
			mbspList = productBestSellerRepo.monthlyBestSellingProduct(mbsp_date);
			model.addAttribute("mbsp_date", mbsp_date);
			
		}
		model.addAttribute("mbspList", mbspList);
		//Monthly Best Selling Product End
		return pageController.chartPage(model);
		
	}

}
