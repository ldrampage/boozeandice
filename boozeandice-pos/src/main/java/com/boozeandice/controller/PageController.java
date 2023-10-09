package com.boozeandice.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.boozeandice.config.UserDetailsImpl;

@Component
public class PageController {
	
	private static final Logger logger = LogManager.getLogger(PageController.class);
	
	@Value("${business_name}")
	private String business_name;
	
	public String accessDeniedPage(Model model) {
		addAttributes(model);
		return "pages/accessDenied";
	}
	
	public String index(Model model) {
		addAttributes(model);
		if(model.getAttribute("validTerminal").toString() == "true") {
			return "pages/index";
		} else {
			return "pages/notValidTerminal";
		}
	}
	
	public String invoicePrintPage(Model model) {
		addAttributes(model);
		return "pages/order-slip-print";
		
	}
	
	public String processDeliveryPage(Model model) {
		addAttributes(model);
		return "pages/processdelivery";
	}
	
	public String processPaymentPage(Model model) {
		addAttributes(model);
		return "pages/processpayment";
	}
	
	public String invoicePage(Model model) {
		addAttributes(model);
		return "pages/invoice";
	}
	
	public String loginPage(Model model) {
		return "pages/login";
	}
	
	/**
	 * 
	 * Chart
	 * 
	 */
	
	public String chartPage(Model model) {
		addAttributes(model);
		return "pages/chartreport/chart";
	}
	
	/**
	 * 
	 * Cashdrawer
	 * 
	 */
	
	public String cashdrawerPage(Model model) {
		addAttributes(model);
		if(model.getAttribute("validTerminal").toString() == "true")
			return "pages/cashdrawer/cashdrawer";
		else 
			return "pages/notValidTerminal";
	}
	
	public String cashDrawerCreate(Model model) {
		addAttributes(model);
		if(model.getAttribute("validTerminal").toString() == "true")
			return "pages/cashdrawer/cashdrawer_create";
		else 
			return "pages/notValidTerminal";
	}
	
	/**
	 * 
	 * Transaction
	 * 
	 */
	
	public String transactionPage(Model model) {
		addAttributes(model);
		if(model.getAttribute("validTerminal").toString() == "true")
			return "pages/transaction/transaction";
		else 
			return "pages/notValidTerminal";
	}
	
	public String transactionViewPage(Model model) {
		addAttributes(model);
		if(model.getAttribute("validTerminal").toString() == "true")
			return "pages/transaction/transaction_view";
		else 
			return "pages/notValidTerminal";
	}
	
	/**
	 * 
	 * Product 
	 *
	 */
	
	public String productPage(Model model) {
		addAttributes(model);
		return "pages/product/product";
	}
	
	public String productEdit(Model model) {
		addAttributes(model);
		return "pages/product/product_edit";
	}
	
	public String productAdd(Model model) {
		addAttributes(model);
		return "pages/product/product_add";
	}
	
	public String productStocks(Model model) {
		addAttributes(model);
		return "pages/product/product_stock"; 
	}
	
	public String productStocksAdd(Model model) {
		addAttributes(model);
		return "pages/product/product_stock_add";
	}
	
	public String productStocksEdit(Model model) {
		addAttributes(model);
		return "pages/product/product_stock_edit";
	}
	
	public String productStockPrintBarcodePage(Model model) {
		addAttributes(model);
		return "pages/product/product_barcode_print.html";
	}
	
	public String productView(Model model) {
		addAttributes(model);
		return "pages/product/product_view";
	}
	
	public String productCategory(Model model) {
		addAttributes(model);
		return "pages/product/product_category";
	}
	
	public String productCategoryCreatePage(Model model) {
		addAttributes(model);
		return "pages/product/product_category_add";
	}
	
	public String productCategoryEditPage(Model model) {
		addAttributes(model);
		return "pages/product/product_category_edit";
	}
	
	/*
	 * 
	 * Customer 
	 * 
	 */
	
	public String customerPage(Model model) {
		addAttributes(model);
		return "pages/customer/customer";
	}
	
	public String customerEditPage(Model model) {
		addAttributes(model);
		return "pages/customer/customer_edit";
	}
	
	public String customerAddPage(Model model) {
		addAttributes(model);
		return "pages/customer/customer_add";
	}
	
	/**
	 * 
	 * Staff
	 * 
	 */
	
	public String profileViewPage(Model model) {
		addAttributes(model);
		return "pages/staff/profileview";
	}
	
	public String staffPage(Model model) {
		addAttributes(model);
		return "pages/staff/staff";
	}
	
	public String createAccountPage(Model model) {
		addAttributes(model);
		return "pages/staff/createaccount";
	}
	
	/**
	 * 
	 * Reports
	 * 
	 */
	
	public String reportsPage(Model model) {
		addAttributes(model);
		return "pages/reports/reports";
	}
	
	// Utility
	
	public Model addAttributes(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> userRoles = new ArrayList<>();
		
		model.addAttribute("fname", user.getFname());
		model.addAttribute("lname", user.getLname());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("imgProfileName", user.getImgProfileName());
		for(GrantedAuthority ga : user.getAuthorities()) {
			userRoles.add(ga.getAuthority());
		}
		model.addAttribute("userRoles",userRoles);
		model.addAttribute("remoteAddressess", user.getRemoteAddressess());
		
		boolean validTerminal = false;
		InetAddress localHostMachine = null;
		try {
			localHostMachine = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		logger.debug("localHostMachine: " + localHostMachine.getHostName());
		for(String remoteAddress : user.getRemoteAddressess()) {
			if(remoteAddress.trim().equals(localHostMachine.getHostName()) || remoteAddress.trim().equals(localHostMachine.getHostName())){
				validTerminal = true;
				break;
			}
		}
		
		model.addAttribute("validTerminal", validTerminal);
		
		logger.debug("fname: " + model.getAttribute("fname"));
		logger.debug("lname: " + model.getAttribute("lname"));
		logger.debug("username: " + model.getAttribute("username"));
		logger.debug("validTerminal: " + model.getAttribute("validTerminal"));
		return model;
	}

}
