package com.boozeandice.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Customer;
import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.entity.Transaction;
import com.boozeandice.entity.TransactionItem;
import com.boozeandice.entity.User;
import com.boozeandice.enums.PaymentMethod;
import com.boozeandice.enums.TransactionStatus;
import com.boozeandice.enums.TransactionType;
import com.boozeandice.service.CashDrawerService;
import com.boozeandice.service.CategoryService;
import com.boozeandice.service.CustomerService;
import com.boozeandice.service.ProductService;
import com.boozeandice.service.TransactionItemService;
import com.boozeandice.service.TransactionService;
import com.boozeandice.service.UserService;
import com.boozeandice.utility.Utilities;

@Controller
public class IndexController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(IndexController.class);

	private static int invoiceCounter = 0;

	private Set<Product> productToPurchaseList = new HashSet<>();

	private String categoryFilterId = "all";

	@Autowired
	private PageController pageController;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService productCatService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionItemService transactionItemServ;

	@Autowired
	private CashDrawerService cashDrawerService;

	@Autowired
	private UserService userService;

	@Autowired
	private Utilities utility;

	@Value("${senior_citizen_discount}")
	private Double seniorCitizenDiscount;

	@Value("${productSilogCategory}")
	private String productSilogCategory;

	@GetMapping(path = "/")
	public String index(Model model) {

		Set<Product> productList = productService.getAllNonZeroStock();
		CashDrawer cashDrawerToday = cashDrawerService.getByToday();
		List<Set<Product>> productsDisplay = utility.organizeProductsDisplay(productList);

		Set<ProductCategory> productCategoryList = productCatService.getAll();

		List<Customer> customerList = customerService.getAll();

		productToPurchaseList.clear();

		model.addAttribute("seniorCitizenDiscount", seniorCitizenDiscount);
		model.addAttribute("customerList", customerList);
		model.addAttribute("productCategoryList", productCategoryList);
		model.addAttribute("cashDrawerToday", cashDrawerToday);
		model.addAttribute("productsDisplay", productsDisplay);
		return pageController.index(model);
	}

	@PostMapping(path = "/")
	public String posProcesses(Model model, @RequestParam Map<String, String> parameters) {
		logger.debug("Start posProcesses()");
		Set<Product> productList = productService.getAllNonZeroStock();
		CashDrawer cashDrawerToday = cashDrawerService.getByToday();

		List<Set<Product>> productsDisplay = utility.organizeProductsDisplay(productList);
		Set<ProductCategory> productCategoryList = productCatService.getAll();
		List<Customer> customerList = customerService.getAll();

		logger.debug(parameters.get("purchased_quantity"));
		if (parameters.get("purchase") != null && !"".equals(parameters.get("purchased_quantity")) && Integer.valueOf(parameters.get("purchased_quantity")) > 0
				&& utility.isNumeric(parameters.get("purchased_quantity"))) {

			// products to display
			if (!"all".equalsIgnoreCase(this.categoryFilterId)) {
				ProductCategory productCategory = productCatService.getById(Long.valueOf(this.categoryFilterId));
				productList = productService.getByProductCategoryAndProductStockGreaterThan(productCategory);
				productsDisplay = utility.organizeProductsDisplay(productList);
				model.addAttribute("categoryFilterValue", Long.valueOf(this.categoryFilterId));
			} else {
				this.categoryFilterId = "all";
			}

			// Products in the purchase list
			if (parameters.get("productId") != null) {
				Product product = productService.getById(Long.valueOf(parameters.get("productId")));

				// if the input quantity is more than stocks -> set the purchased product to the
				// maximum stock available
				if (Long.valueOf(parameters.get("purchased_quantity")) > product.getStocks()) {
					product.setQtyToPurchase(product.getStocks());
				} else {
					product.setQtyToPurchase(Long.valueOf(parameters.get("purchased_quantity")));
				}
				productToPurchaseList.add(product);
			}
		}

		if (parameters.get("removePurchase") != null) {

			// products to display
			if (!"all".equalsIgnoreCase(this.categoryFilterId)) {
				ProductCategory productCategory = productCatService.getById(Long.valueOf(this.categoryFilterId));
				productList = productService.getByProductCategoryAndProductStockGreaterThan(productCategory);
				productsDisplay = utility.organizeProductsDisplay(productList);
				model.addAttribute("categoryFilterValue", Long.valueOf(this.categoryFilterId));
			} else {
				this.categoryFilterId = "all";
			}

			// Products in the purchase list
			Product product = productService.getById(Long.valueOf(parameters.get("productId")));
			productToPurchaseList.remove(product);
		}

		if (parameters.get("fetchByCategory") != null) {
			if (!parameters.get("categoryId").equalsIgnoreCase("all")) {
				ProductCategory productCategory = productCatService.getById(Long.valueOf(parameters.get("categoryId")));
				this.categoryFilterId = parameters.get("categoryId");
				productList = productService.getByProductCategoryAndProductStockGreaterThan(productCategory);
				productsDisplay = utility.organizeProductsDisplay(productList);
				model.addAttribute("categoryFilterValue", Long.valueOf(parameters.get("categoryId")));
			} else {
				this.categoryFilterId = "all";
			}
		}
		
		if(parameters.get("searchByProductName") != null) {
			productList = productService.getByNameContainingAndStocksGreaterThan(parameters.get("searchByProductName"));
			productsDisplay = utility.organizeProductsDisplay(productList);
		}

		if (parameters.get("quantityHandler") != null) {
			logger.debug("Product ID: " + parameters.get("productId_QH"));
			logger.debug("Value: " + parameters.get("value_QH"));
			for (Product product : productToPurchaseList) {
				if (product.getId().equals(Long.valueOf(parameters.get("productId_QH")))) {
					product.setQtyToPurchase(Long.valueOf(parameters.get("value_QH")));
					logger.debug(product.getQtyToPurchase());
				}
			}

		}

		if (parameters.get("addMoreToPurchase") != null) {
			Transaction transaction = transactionService.getById(Long.valueOf(parameters.get("transactionId")));
			Set<TransactionItem> transactionItems = transactionItemServ.getByTransaction(transaction);

			productToPurchaseList.clear();
			for (TransactionItem transactionItem : transactionItems) {
				transactionItem.getProduct().setQtyToPurchase(transactionItem.getQuantity());
				productToPurchaseList.add(transactionItem.getProduct());
			}

			if (transaction.getCustomer() != null)
				model.addAttribute("customerFilterValue", Long.valueOf(transaction.getCustomer().getId()));

			transactionItemServ.deleteAll(transactionItems);
			transactionService.delete(transaction);
		}

		model.addAttribute("customerList", customerList);
		model.addAttribute("seniorCitizenDiscount", seniorCitizenDiscount);
		model.addAttribute("productCategoryList", productCategoryList);
		model.addAttribute("productToPurchaseList", productToPurchaseList);
		model.addAttribute("cashDrawerToday", cashDrawerToday);
		model.addAttribute("productsDisplay", productsDisplay);
		return pageController.index(model);
	}

	@PostMapping(path = "/checkout")
	public String checkoutProcess(Model model, @RequestParam Map<String, String> parameters) {
		logger.debug("Start checkoutProcess()");
		if (parameters.get("checkoutCreateTransaction") != null) {
			logger.debug("Entered checkoutCreateTransaction");
			String invoiceNumber = generateInvoiceNumber();
			Double shipping = 0.0;
			Double packaging = 0.0;
			Double discount = 0.0;
			Double subTotal = 0.0;
			Double total = 0.0;
			Double vatableSales = 0.0;
			Double vatAmount = 0.0;
			Double vat = 0.12;
			logger.debug(invoiceNumber);

			// consolidate subtotal
			for (Product product : productToPurchaseList) {
				subTotal = subTotal + (product.getPrice() * product.getQtyToPurchase());
			}

			Transaction transaction = new Transaction();

			// take out process
			if (parameters.get("takeout") != null && parameters.get("takeout").equalsIgnoreCase("on")) {
				logger.debug("Take out: " + parameters.get("takeout"));

				// any product with packaging fee will be consolidated
				for (Product product : productToPurchaseList) {
					if (product.getPackagingFee() != null) {
						packaging = packaging + (product.getPackagingFee() * product.getQtyToPurchase());
					}
				}

				transaction.setTakeOut(true);
			}

			// apply discount
			if (parameters.get("discount") != null && parameters.get("discount").length() > 0) {
				logger.debug("Discount: " + parameters.get("discount"));
				discount = subTotal * Double.valueOf(parameters.get("discount"));
				subTotal = subTotal - discount;
			}
			
			// consolidate the total and vat
			total = (subTotal + shipping + packaging);

			vatAmount = subTotal * vat;
			vatableSales = subTotal - vatAmount;

			logger.debug("subTotal: " + subTotal);
			logger.debug("Vatable Sales: " + vatableSales);
			logger.debug("Vat amount: " + vatAmount);

			// check if existing customer then set
			if (parameters.get("customerId") != null && parameters.get("customerId").length() > 0) {
				Customer customer = customerService.getById(Long.valueOf(parameters.get("customerId")));
				transaction.setCustomer(customer);
			}

			// setup the staff
			User cashier = userService.getByUsername("lxbordo"); // TODO edit this once login is setup

			transaction.setInvoiceNumber(invoiceNumber);
			transaction.setTransactionStatus(TransactionStatus.PENDING);
			transaction.setSubTotal(subTotal);
			transaction.setTotal(total);
			transaction.setShipping(shipping);
			transaction.setPackaging(packaging);
			transaction.setDiscount(discount);
			transaction.setVatableSales(vatableSales);
			transaction.setVatAmount(vatAmount);
			transaction.setTransactionDateTime(new Timestamp(System.currentTimeMillis()));
			transaction.setCashier(cashier);

			transaction = transactionService.save(transaction);

			// Map the productToPurchaseList to TransactionItem
			Set<TransactionItem> transactionItemList = new HashSet<>();
			TransactionItem transactionItem = null;
			for (Product product : productToPurchaseList) {
				transactionItem = new TransactionItem();
				transactionItem.setProduct(product);
				transactionItem.setQuantity(product.getQtyToPurchase());
				transactionItem.setTransaction(transaction);
				transactionItem.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				transactionItemList.add(transactionItem);
				transactionItemServ.save(transactionItem);
			}

			transaction.setTransactionItem(transactionItemList);
			model.addAttribute("transaction", transaction);
		}

//		for (Map.Entry<String, String> map : parameters.entrySet()) {
//			logger.debug(map.getKey() + ", " + map.getValue());
//		}

		if (parameters.get("pay_later_btn") != null) {
			logger.debug("Entered pay_later_btn");
			logger.debug(parameters.get("tableno"));
			Transaction transaction = transactionService.getById(Long.valueOf(parameters.get("transactionId")));
			if (parameters.get("soldTo") != null && !parameters.get("soldTo").isEmpty())
				transaction.setSoldTo(parameters.get("soldTo"));

			if (parameters.get("registeredCustomer") != null && !parameters.get("registeredCustomer").isEmpty()) {
				Customer customer = customerService.getById(Long.valueOf(parameters.get("registeredCustomer")));
				transaction.setCustomer(customer);
				transaction.setSoldTo(customer.getFname() + " " + customer.getLname());
			}

			transaction.setTableNo(parameters.get("tableno"));
			transactionService.save(transaction);
			return "redirect:/transaction";
		}

		return pageController.checkoutPage(model);
	}

	@GetMapping(path = "/checkout/{transactionId}")
	public String checkoutProcess(Model model, @PathVariable("transactionId") String transactionId) {
		Transaction transaction = transactionService.getById(Long.valueOf(transactionId));
		model.addAttribute("transaction", transaction);
		return pageController.checkoutPage(model);
	}

	@PostMapping(path = "/invoice")
	public String invoiceProcess(Model model, @RequestParam Map<String, String> parameters) {
		Transaction transaction = transactionService.getById(Long.valueOf(parameters.get("transactionId")));
//		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
//			logger.debug(parameter.getKey() + ": " + parameter.getValue());
//		}

		if (parameters.get("cash_payment") != null
				&& transaction.getTransactionStatus().equalsIgnoreCase(TransactionStatus.PENDING.getDescription())) {
			transaction.setTransactionStatus(TransactionStatus.PAID.getDescription());
			transaction.setPaymentMethod(PaymentMethod.CASH.getDescription());
			transaction.setCashReceived(Double.valueOf(parameters.get("cash_received")));
			transaction.setTransactionType(TransactionType.SALE.getDescription());

			// set the buyer info
			if (parameters.get("soldTo") != null && parameters.get("soldTo").trim().length() > 0) {
				transaction.setSoldTo(parameters.get("soldTo"));
			} else {
				transaction.setSoldTo("null");
			}

			if (parameters.get("registeredCustomer") != null) {
				if (parameters.get("registeredCustomer") != null && !parameters.get("registeredCustomer").isEmpty()) {
					Customer customer = customerService.getById(Long.valueOf(parameters.get("registeredCustomer")));
					transaction.setCustomer(customer);
					transaction.setSoldTo(customer.getFname() + " " + customer.getLname());
				}
			}

			transaction = this.processFinalInvoiceSteps(transaction, parameters);

			// Set the UI Display
			model.addAttribute("transaction", transaction);

		}

		if (parameters.get("gcash_payment") != null
				&& transaction.getTransactionStatus().equalsIgnoreCase(TransactionStatus.PENDING.getDescription())) {
			transaction.setTransactionStatus(TransactionStatus.PAID.getDescription());
			transaction.setPaymentMethod(PaymentMethod.GCASH.getDescription());
			transaction.setCashReceived(Double.valueOf(parameters.get("cash_received")));
			transaction.setTransactionType(TransactionType.SALE.getDescription());
			transaction.setPaymentReference(parameters.get("reference_number"));
			transaction.setPhoneNumber(parameters.get("phone_number"));

			// set the buyer info
			if (parameters.get("soldTo") != null && parameters.get("soldTo").trim().length() > 0) {
				transaction.setSoldTo(parameters.get("soldTo"));
			} else {
				transaction.setSoldTo("null");
			}

			if (parameters.get("registeredCustomer") != null) {
				if (parameters.get("registeredCustomer") != null && !parameters.get("registeredCustomer").isEmpty()) {
					Customer customer = customerService.getById(Long.valueOf(parameters.get("registeredCustomer")));
					transaction.setCustomer(customer);
					transaction.setSoldTo(customer.getFname() + " " + customer.getLname());
				}
			}
			
			transaction = this.processFinalInvoiceSteps(transaction, parameters);

			// Set the UI Display
			model.addAttribute("transaction", transaction);
		}

		return pageController.invoicePage(model);
	}

	/*
	 * 
	 * Start: Utility Methods
	 * 
	 */

	public Transaction processFinalInvoiceSteps(Transaction transaction, Map<String, String> parameters) {
		logger.debug("Start processFinalInvoiceSteps()");
		// deduct stock in the product
		if (transaction.getTransactionStatus().equalsIgnoreCase(TransactionStatus.PAID.getDescription())) {
			for (TransactionItem transactionItem : transaction.getTransactionItem()) {
				Product product = transactionItem.getProduct();
				Long quantity = transactionItem.getQuantity();
				Long newQuantity = product.getStocks() - quantity;
				product.setStocks(newQuantity);
				productService.save(product);
			}
		}

		// associate the transaction with cash drawer
		logger.debug("Associate transaction with cashdrawer: ");
		CashDrawer cashDrawer = cashDrawerService.getByToday();
		Set<Transaction> transactionList = cashDrawer.getTransactions();
		if (cashDrawer != null) {
			if (transactionList == null) {
				transactionList = new HashSet<>();
			}
			transactionList.add(transaction);
		}
		// save the state
		logger.debug("transactionList size: " + transactionList.size());
		cashDrawer.setTransactions(transactionList);
		transaction.setCashdrawer(cashDrawer);
		transaction.setTableNo(parameters.get("tableno"));
		cashDrawerService.save(cashDrawer);
		transaction = transactionService.save(transaction);

		// Generate Order Slip and Open the Cash Drawer
		String orderSlipMessage = utility.composeOrderSlip(transaction);
		utility.openCashDrawer();
		utility.printOrderSlip(orderSlipMessage);
		logger.debug("End processFinalInvoiceSteps()");
		
		return transaction;
	}

	public static String generateInvoiceNumber() {
		// Create a timestamp-based identifier
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = dateFormat.format(new Date());

		// Increment the invoice counter
		invoiceCounter++;

		// Combine the prefix, timestamp, and counter
		String invoiceNumber = "INV" + timestamp + String.format("%04d", invoiceCounter);

		return invoiceNumber;
	}

}
