package com.boozeandice.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boozeandice.repository.ReportChartRepository;
import com.bozeandice.vo.MonthlyRecapChartVO;

@Controller
@RequestMapping(path="/chart")
@Secured({"ROLE_ADMIN","ROLE_SUPERVISOR"})
public class ChartController {
	
	private static final Logger logger = LogManager.getLogger(ChartController.class);
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private ReportChartRepository reportChartRepo;
	
	
	@GetMapping(path="/")
	public String chartPage(Model model) {
		
		//Monthly Recap Chart Start
		
		List<MonthlyRecapChartVO> monthlyRecapChartVoList = reportChartRepo.getMonthlyRecapChartByYear("2023");
		
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
			
			logger.debug("month: " + mrcVO.getMonth());
			logger.debug("revenue: " + mrcVO.getRevenue());
			logger.debug("cost: " + mrcVO.getCost());
			logger.debug("profit: " + mrcVO.getProfit());
		}
		//Monthly Recap Chart End
		
		return pageController.chartPage(model);
		
	}

}
