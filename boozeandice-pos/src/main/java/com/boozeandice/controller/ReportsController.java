package com.boozeandice.controller;

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

import com.boozeandice.service.ReportsService;
import com.bozeandice.vo.SalesReportVO;

@Controller
@RequestMapping(path = "/reports")
@Secured({ "ROLE_ADMIN", "ROLE_SUPERVISOR" })
public class ReportsController {

	private static final Logger logger = LogManager.getLogger(ReportsController.class);

	@Autowired
	private PageController pageController;
	
	@Autowired
	private ReportsService reportsService;

	@GetMapping(path = "/zreport")
	public String zReportsPage(Model model, @RequestParam(required = false) String zdate)
			throws Exception {
		reportsService.zReportsPage(model, zdate);
		return pageController.reportsPage(model);
	}

	@GetMapping(path = "/salesreport")
	public String salesReportPage(Model model) {
		// Sales Report Tab Start
		// Daily Sales Report
		reportsService.salesReportPage(model);
		// Sales Report Tab End
		return pageController.salesReportPage(model);
	}

	@GetMapping(path = "/monthlysalesreport")
	public String monthlySalesReportPage(Model model) {
		// Sales Report Tab Start
		// Daily Sales Report
		reportsService.monthlySalesReportPage(model);
		// Sales Report Tab End
		return pageController.monthlySalesReportPage(model);
	}

	@PostMapping(path = "/z-report-print")
	public String zreportPrintPage(Model model, @RequestParam Map<String, String> parameters) {
		reportsService.zreportPrintPage(model, parameters);
		return pageController.zreportPrintPage(model);
	}

}
