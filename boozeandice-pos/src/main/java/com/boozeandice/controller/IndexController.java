package com.boozeandice.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.config.UserDetailsImpl;
import com.boozeandice.entity.Address;
import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Customer;
import com.boozeandice.entity.Discount;
import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.entity.Shipment;
import com.boozeandice.entity.Transaction;
import com.boozeandice.entity.TransactionItem;
import com.boozeandice.entity.User;
import com.boozeandice.entity.UserActivityLog;
import com.boozeandice.enums.PaymentMethod;
import com.boozeandice.enums.ShipmentCarrier;
import com.boozeandice.enums.ShipmentStatus;
import com.boozeandice.enums.TransactionStatus;
import com.boozeandice.enums.TransactionType;
import com.boozeandice.service.IndexService;
import com.boozeandice.service.UserService;

@Controller
@Secured({ "ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_CASHIER" })
public class IndexController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(IndexController.class);

	@Autowired
	private PageController pageController;

	@Autowired
	private IndexService indexService;

	@GetMapping(path = "/access_denied")
	public String accessDenied(Model model) {
		return pageController.accessDeniedPage(model);
	}

	@GetMapping(path = "/")
	public String index(Model model) {
		indexService.index(model);
		return pageController.index(model);
	}

	@GetMapping(path = "/invoiceprint")
	public String invoicePrintPage(Model model, @RequestParam(name = "id", required = true) String transactionId) {
		indexService.invoicePrintPagePrep(model, transactionId);
		return pageController.invoicePrintPage(model);
	}

	@GetMapping(path = "/processpayment/{transactionId}")
	public String getProcessPayment(Model model, @PathVariable String transactionId) {
		indexService.getProcessPayment(model, transactionId);
		return pageController.processPaymentPage(model);
	}

	@PostMapping(path = "/")
	public String posProcesses(Model model, @RequestParam Map<String, String> parameters) {
		indexService.posPrepProcess(model, parameters);
		return pageController.index(model);
	}

	@PostMapping(path = "/processpayment")
	public String processPayment(Model model, @RequestParam Map<String, String> parameters) {

		String direction = indexService.processPaymentPrep(model, parameters);
		if (direction != null) {
			return direction;
		}

		return pageController.processPaymentPage(model);
	}

	@PostMapping(path = "/invoice")
	public String invoiceProcess(Model model, @RequestParam Map<String, String> parameters) {
		indexService.invoiceProcessPrep(model, parameters);
		return pageController.invoicePage(model);
	}

}
