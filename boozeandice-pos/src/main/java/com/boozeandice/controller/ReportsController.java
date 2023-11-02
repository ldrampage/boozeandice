package com.boozeandice.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.Discount;
import com.boozeandice.entity.Expense;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.entity.Transaction;
import com.boozeandice.entity.TransactionItem;
import com.boozeandice.enums.CardBrand;
import com.boozeandice.enums.PaymentMethod;
import com.boozeandice.enums.TransactionStatus;
import com.boozeandice.repository.ReportChartRepository;
import com.boozeandice.service.CategoryService;
import com.boozeandice.service.TransactionService;
import com.bozeandice.vo.SalesReportVO;
import com.bozeandice.vo.PayMayaPaymentBrkDown;
import com.bozeandice.vo.PaymentDetailsVO;
import com.bozeandice.vo.SalesTaxSummaryVO;

@Controller
@RequestMapping(path = "/reports")
@Secured({ "ROLE_ADMIN", "ROLE_SUPERVISOR" })
public class ReportsController {

	private static final Logger logger = LogManager.getLogger(ReportsController.class);

	@Autowired
	private PageController pageController;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private CategoryService productCatService;

	@Autowired
	private ReportChartRepository reportChartRepo;

	@GetMapping(path = "/zreport/")
	public String zReportsPage(Model model, @RequestParam(name = "zdate", required = false) String zdate)
			throws Exception {
		logger.debug("Start reportsPage() -> zdate: " + zdate);

		// Z Report Start
		Set<Transaction> transactionList = null;
		Set<Transaction> transactionListPaid = null;
		Date zdateformat = null;
		if (zdate != null) {
			zdateformat = new SimpleDateFormat("MM/dd/yyyy").parse(zdate);
			transactionList = transactionService.getByTransactionDate(zdateformat);
			transactionListPaid = this.filterPaidTransaction(transactionList);
			model.addAttribute("zdate", zdate);
			model.addAttribute("lastaction", "zreporttab");
		} else {
			transactionList = transactionService.getByToday();
			transactionListPaid = this.filterPaidTransaction(transactionList);
			SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");
			model.addAttribute("zdate", outputFormat.format(new Date()));
			zdateformat = outputFormat.parse(outputFormat.format(new Date()));
		}

		// Sales and Tax Summary Start
		SalesTaxSummaryVO sts = new SalesTaxSummaryVO();

		Double totalNetSales = 0.0;
		Double totalTax = 0.0;
		Double totalSales = 0.0;
		for (Transaction transaction : transactionListPaid) {
			totalNetSales = totalNetSales + transaction.getVatableSales();
			totalTax = totalTax + transaction.getVatAmount();
			totalSales = totalSales + transaction.getTotal();
		}

		sts.setTotalNetSales(totalNetSales);
		sts.setTotalTax(totalTax);
		sts.setTotalSales(totalSales);

		model.addAttribute("salesTaxSummaryVO", sts);
		// Sales and Tax Summary End

		// SalesCategory Start
		Set<ProductCategory> productCategoryList = productCatService.getAll();
		for (Transaction transaction : transactionListPaid) {

			for (TransactionItem transactionItem : transaction.getTransactionItem()) {
				for (ProductCategory productCategory : productCategoryList) {
					if (productCategory.equals(transactionItem.getProduct().getProductCategory())) {
						productCategory
								.setSoldCount(productCategory.getSoldCount() + (1 * transactionItem.getQuantity()));
						productCategory.setQuantityNetSales(productCategory.getQuantityNetSales()
								+ (transactionItem.getProduct().getPrice() * transactionItem.getQuantity()));
					}
				}

			}
		}

		Long totalSoldCount = Long.valueOf(0);
		Double totalQuantityNetSales = 0.0;

		for (ProductCategory productCategory : productCategoryList) {
			totalSoldCount = totalSoldCount + productCategory.getSoldCount();
			totalQuantityNetSales = totalQuantityNetSales + productCategory.getQuantityNetSales();
		}

		model.addAttribute("totalSoldCount", totalSoldCount);
		model.addAttribute("totalQuantityNetSales", totalQuantityNetSales);
		model.addAttribute("productCategoryList", productCategoryList);
		// SalesCategory End

		// Payment Details Start
		List<PaymentDetailsVO> paymentDetailsList = new ArrayList<>();
		PaymentDetailsVO cash = new PaymentDetailsVO();
		PaymentDetailsVO gcash = new PaymentDetailsVO();
		PaymentDetailsVO paymaya = new PaymentDetailsVO();
		Double totalPayments = 0.0;
		Double totalPaymentsMinusTotalSales = 0.0;
		for (Transaction transaction : transactionListPaid) {

			if (transaction.getPaymentMethod().equals(PaymentMethod.CASH.getDescription())) {
				cash.setPaymentMethod(PaymentMethod.CASH.getDescription());
				cash.setTotal(cash.getTotal() + transaction.getTotal());
			} else if (transaction.getPaymentMethod().equals(PaymentMethod.GCASH.getDescription())) {
				gcash.setPaymentMethod(PaymentMethod.GCASH.getDescription());
				gcash.setTotal(gcash.getTotal() + transaction.getTotal());
			} else if (transaction.getPaymentMethod().equals(PaymentMethod.PAYMAYA.getDescription())) {
				paymaya.setPaymentMethod(PaymentMethod.PAYMAYA.getDescription());
				paymaya.setTotal(paymaya.getTotal() + transaction.getTotal());
			}

			totalPayments = totalPayments + transaction.getTotal();

		}
		totalPaymentsMinusTotalSales = totalPayments - totalSales;
		if (cash.getPaymentMethod() != null)
			paymentDetailsList.add(cash);
		if (gcash.getPaymentMethod() != null)
			paymentDetailsList.add(gcash);
		if (paymaya.getPaymentMethod() != null)
			paymentDetailsList.add(paymaya);

		model.addAttribute("totalPayments", totalPayments);
		model.addAttribute("totalPaymentsMinusTotalSales", totalPaymentsMinusTotalSales);
		model.addAttribute("paymentDetailsList", paymentDetailsList);
		// Payment Details End

		// Paymaya Payment Breakdown, Total Discount Packaging start
		PayMayaPaymentBrkDown pmpbd = new PayMayaPaymentBrkDown();
		Double totalDiscounts = 0.0;
		Double totalPackaging = 0.0;
		Double totalExpenses = 0.0;
		for (Transaction transaction : transactionListPaid) {

			if (transaction.getCardBrand() != null
					&& CardBrand.AMEX.getDescription().equals(transaction.getCardBrand())) {
				pmpbd.setAmex(pmpbd.getAmex() + transaction.getTotal());
				pmpbd.setTotal(pmpbd.getTotal() + transaction.getTotal());
			}

			if (transaction.getCardBrand() != null
					&& CardBrand.VISA.getDescription().equals(transaction.getCardBrand())) {
				pmpbd.setVisa(pmpbd.getVisa() + transaction.getTotal());
				pmpbd.setTotal(pmpbd.getTotal() + transaction.getTotal());
			}

			if (transaction.getCardBrand() != null
					&& CardBrand.MASTERCARD.getDescription().equals(transaction.getCardBrand())) {
				pmpbd.setMastercard(pmpbd.getMastercard() + transaction.getTotal());
				pmpbd.setTotal(pmpbd.getTotal() + transaction.getTotal());
			}

			if (transaction.getDiscount() != null && transaction.getDiscount().size() > 0) {
				for (Discount dt : transaction.getDiscount()) {
					totalDiscounts = totalDiscounts + dt.getAmount();
				}
				// totalDiscounts = totalDiscounts + transaction.getDiscount();
			}

			if (transaction.getPackaging() != null && transaction.getPackaging() > 0) {
				totalPackaging = totalPackaging + transaction.getPackaging();
			}
		}
		if (transactionListPaid != null && transactionListPaid.size() > 0) {
			Transaction transaction = new ArrayList<>(transactionListPaid).get(0);
			for (Expense expense : transaction.getCashdrawer().getExpenses()) {
				totalExpenses = totalExpenses + expense.getExpense();
			}
		}
		model.addAttribute("totalPackaging", totalPackaging);
		model.addAttribute("totalDiscounts", totalDiscounts);
		model.addAttribute("totalExpenses", totalExpenses);
		model.addAttribute("pmpbd", pmpbd);
		// PAYMAYA Payment Breakdown, Total Discount Packaging end

		// Total discounts start

		// Total discounts end

		// Z Report End
		return pageController.reportsPage(model);
	}

	@GetMapping(path = "/salesreport")
	public String salesReportPage(Model model) {
		// Sales Report Tab Start
		// Daily Sales Report
		List<SalesReportVO> dailySalesReportList = reportChartRepo.getAllDailySalesReportVO();
		model.addAttribute("dailySalesReportList", dailySalesReportList);
		// Sales Report Tab End
		return pageController.salesReportPage(model);
	}

	@GetMapping(path = "monthlysalesreport")
	public String monthlySalesReportPage(Model model) {
		// Sales Report Tab Start
		// Daily Sales Report
		List<SalesReportVO> monthlySalesReportList = reportChartRepo.getAllMonthlySalesReportVO();
		model.addAttribute("monthlySalesReportList", monthlySalesReportList);
		// Sales Report Tab End
		return pageController.monthlySalesReportPage(model);
	}

	@PostMapping(path = "/z-report-print")
	public String zreportPrintPage(Model model, @RequestParam Map<String, String> parameters) {
		for (Map.Entry<String, String> param : parameters.entrySet()) {
			logger.debug(param.getKey() + ": " + param.getValue());
		}

		String zdate = parameters.get("zdate");
		Set<Transaction> transactionList = null;
		Set<Transaction> transactionListPaid = null;
		if (zdate != null) {
			Date zdateformat = null;
			try {
				zdateformat = new SimpleDateFormat("MM/dd/yyyy").parse(zdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			transactionList = transactionService.getByTransactionDate(zdateformat);
			transactionListPaid = this.filterPaidTransaction(transactionList);
		}

		// Sales and Tax Summary Start
		SalesTaxSummaryVO sts = new SalesTaxSummaryVO();

		Double totalNetSales = 0.0;
		Double totalTax = 0.0;
		Double totalSales = 0.0;
		for (Transaction transaction : transactionListPaid) {
			totalNetSales = totalNetSales + transaction.getVatableSales();
			totalTax = totalTax + transaction.getVatAmount();
			totalSales = totalSales + transaction.getTotal();
		}

		sts.setTotalNetSales(totalNetSales);
		sts.setTotalTax(totalTax);
		sts.setTotalSales(totalSales);

		model.addAttribute("salesTaxSummaryVO", sts);
		// Sales and Tax Summary End

		// SalesCategory Start
		Set<ProductCategory> productCategoryList = productCatService.getAll();
		for (Transaction transaction : transactionListPaid) {

			for (TransactionItem transactionItem : transaction.getTransactionItem()) {
				for (ProductCategory productCategory : productCategoryList) {
					if (productCategory.equals(transactionItem.getProduct().getProductCategory())) {
						productCategory
								.setSoldCount(productCategory.getSoldCount() + (1 * transactionItem.getQuantity()));
						productCategory.setQuantityNetSales(productCategory.getQuantityNetSales()
								+ (transactionItem.getProduct().getPrice() * transactionItem.getQuantity()));
					}
				}

			}
		}

		Long totalSoldCount = Long.valueOf(0);
		Double totalQuantityNetSales = 0.0;

		for (ProductCategory productCategory : productCategoryList) {
			totalSoldCount = totalSoldCount + productCategory.getSoldCount();
			totalQuantityNetSales = totalQuantityNetSales + productCategory.getQuantityNetSales();
		}

		model.addAttribute("totalSoldCount", totalSoldCount);
		model.addAttribute("totalQuantityNetSales", totalQuantityNetSales);
		model.addAttribute("productCategoryList", productCategoryList);
		// SalesCategory End

		// Payment Details Start
		List<PaymentDetailsVO> paymentDetailsList = new ArrayList<>();
		PaymentDetailsVO cash = new PaymentDetailsVO();
		PaymentDetailsVO gcash = new PaymentDetailsVO();
		PaymentDetailsVO paymaya = new PaymentDetailsVO();
		Double totalPayments = 0.0;
		Double totalPaymentsMinusTotalSales = 0.0;
		for (Transaction transaction : transactionListPaid) {

			if (transaction.getPaymentMethod().equals(PaymentMethod.CASH.getDescription())) {
				cash.setPaymentMethod(PaymentMethod.CASH.getDescription());
				cash.setTotal(cash.getTotal() + transaction.getTotal());
			} else if (transaction.getPaymentMethod().equals(PaymentMethod.GCASH.getDescription())) {
				gcash.setPaymentMethod(PaymentMethod.GCASH.getDescription());
				gcash.setTotal(gcash.getTotal() + transaction.getTotal());
			} else if (transaction.getPaymentMethod().equals(PaymentMethod.PAYMAYA.getDescription())) {
				paymaya.setPaymentMethod(PaymentMethod.PAYMAYA.getDescription());
				paymaya.setTotal(paymaya.getTotal() + transaction.getTotal());
			}

			totalPayments = totalPayments + transaction.getTotal();

		}
		totalPaymentsMinusTotalSales = totalPayments - totalSales;
		if (cash.getPaymentMethod() != null)
			paymentDetailsList.add(cash);
		if (gcash.getPaymentMethod() != null)
			paymentDetailsList.add(gcash);
		if (paymaya.getPaymentMethod() != null)
			paymentDetailsList.add(paymaya);

		model.addAttribute("totalPayments", totalPayments);
		model.addAttribute("totalPaymentsMinusTotalSales", totalPaymentsMinusTotalSales);
		model.addAttribute("paymentDetailsList", paymentDetailsList);
		// Payment Details End

		// Paymaya Payment Breakdown, Total Discount Packaging start
		PayMayaPaymentBrkDown pmpbd = new PayMayaPaymentBrkDown();
		Double totalDiscounts = 0.0;
		Double totalPackaging = 0.0;
		Double totalExpense = 0.0;
		for (Transaction transaction : transactionListPaid) {

			if (transaction.getCardBrand() != null
					&& CardBrand.AMEX.getDescription().equals(transaction.getCardBrand())) {
				pmpbd.setAmex(pmpbd.getAmex() + transaction.getTotal());
				pmpbd.setTotal(pmpbd.getTotal() + transaction.getTotal());
			}

			if (transaction.getCardBrand() != null
					&& CardBrand.VISA.getDescription().equals(transaction.getCardBrand())) {
				pmpbd.setVisa(pmpbd.getVisa() + transaction.getTotal());
				pmpbd.setTotal(pmpbd.getTotal() + transaction.getTotal());
			}

			if (transaction.getCardBrand() != null
					&& CardBrand.MASTERCARD.getDescription().equals(transaction.getCardBrand())) {
				pmpbd.setMastercard(pmpbd.getMastercard() + transaction.getTotal());
				pmpbd.setTotal(pmpbd.getTotal() + transaction.getTotal());
			}

			if (transaction.getDiscount() != null && transaction.getDiscount().size() > 0) {
				for (Discount dt : transaction.getDiscount()) {
					totalDiscounts = totalDiscounts + dt.getAmount();
				}
				// totalDiscounts = totalDiscounts + transaction.getDiscount();
			}

			if (transaction.getPackaging() != null && transaction.getPackaging() > 0) {
				totalPackaging = totalPackaging + transaction.getPackaging();
			}
			
			Set<Expense> expenseList = transaction.getCashdrawer().getExpenses();
			
			if(expenseList != null && expenseList.size() > 0) {
				for(Expense expense : expenseList) {
					totalExpense = totalExpense + expense.getExpense();
				}
			}

		}
		model.addAttribute("totalPackaging", totalPackaging);
		model.addAttribute("totalDiscounts", totalDiscounts);
		model.addAttribute("totalExpense", totalExpense);
		model.addAttribute("pmpbd", pmpbd);
		// PAYMAYA Payment Breakdown, Total Discount Packaging end

		model.addAttribute("zdate", parameters.get("zdate"));
		return pageController.zreportPrintPage(model);
	}

	private Set<Transaction> filterPaidTransaction(Set<Transaction> transactionList) {
		Set<Transaction> newTransactionList = new HashSet<Transaction>();
		for (Transaction transaction : transactionList) {
			if (transaction.getTransactionStatus().equals(TransactionStatus.PAID.getDescription())) {
				newTransactionList.add(transaction);
			}
		}
		return newTransactionList;

	}

}
