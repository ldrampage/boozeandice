package com.boozeandice.controller;

import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.boozeandice.entity.Product;
import com.boozeandice.service.ProductService;

@Controller
@RequestMapping(path = "/product")
@Secured({ "ROLE_ADMIN", "ROLE_SUPERVISOR" })
public class ProductController {

	private static final Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	private PageController pageController;

	@Autowired
	private ProductService productService;

	@GetMapping(path = "/category")
	public String productCategory(Model model) {
		productService.getProductCategory(model);
		return pageController.productCategory(model);
	}

	@GetMapping(path = "/category/add")
	public String productCategoryCreatePage(Model model) {
		return pageController.productCategoryCreatePage(model);

	}

	@GetMapping(path = "/category/edit/{id}")
	public String productCategoryEditPage(Model model, @PathVariable String id) {
		productService.productCategoryEditPagePrep(model, id);
		return pageController.productCategoryEditPage(model);
	}
	
	@PostMapping(path = "/category/add")
	public String productCategoryCreateProcess(Model model, @RequestParam Map<String, String> parameters) {
		productService.productCategoryCreateProcessPrep(model, parameters);
		return pageController.productCategoryCreatePage(model);
	}

//	@GetMapping(path="/cateegory")
//	public String productCategoryEditProcess(Model model, @RequestParam Map<String, String> parameters) {
//		
//	}

	/**
	 * 
	 * Stocks Start
	 * 
	 */

	@GetMapping(path = "/stocks")
	public String productStocks(Model model) {
		productService.productStocks(model);
		return pageController.productStocks(model);
	}

//	@PostMapping(path = "/stocks/transfer/")
//	public String productStockTransferProcess(Model model, @RequestParam Map<String, String> parameters) {
//		logger.debug("In productStockTransferProcess()");
//		logger.debug("pstdid: " + parameters.get("pstdid"));
//		logger.debug("pstsid: " + parameters.get("pstsid"));
//		logger.debug("quantity: " + parameters.get("quantity"));
//		logger.debug("username: " + parameters.get("created_by"));
//		Map<String,String> message = new HashMap<>();
//		try {
//			if (parameters.get("processTransfer") != null) {
//				User staff = userService.getByUsername(parameters.get("created_by"));
//				Product stockDestination = productService.getById(Long.valueOf(parameters.get("pstdid")));
//				Product stockSource = productService.getById(Long.valueOf(parameters.get("pstsid")));
//				Long quantity = Long.valueOf(parameters.get("quantity"));
//				if (stockSource.getStocks() >= quantity) {
//					
//					//perform transfer
//					stockDestination.setStocks(stockDestination.getStocks() + quantity);
//					productService.save(stockDestination);
//					stockSource.setStocks(stockSource.getStocks() - quantity);
//					productService.save(stockSource);
//					//document
//					StockTransferTrace sts = new StockTransferTrace();
//					sts.setCreatedBy(staff);
//					sts.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//					sts.setStockDestinationId(stockDestination);
//					sts.setStockSourceId(stockSource);
//					sts.setNotes(parameters.get("notes"));
//					stsRepo.save(sts);
//					
//					message.put("status", "success");
//
//				} else {
//					throw new Exception("Stocks available is not enough for " + stockSource.getName());
//				}
//
//			}
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//			ex.printStackTrace();
//			message.put("status", "error");
//			message.put("message", ex.getMessage());
//		}
//
//		model.addAttribute("message",message);
//		return productStockTransfer(model, parameters.get("pstdid"));
//	}

	/**
	 * PSTS - Product stock transfer source PSTD - Product stock transfer
	 * destination
	 * 
	 * @param model
	 * @param productStockId
	 * @return
	 */
//	@GetMapping(path = "/stocks/transfer/")
//	public String productStockTransfer(Model model,
//			@RequestParam(value = "id", required = true) String productStockId) {
//		logger.debug("In productStockTransfer() id -> " + productStockId);
//
//		Product pstd = productService.getById(Long.valueOf(productStockId));
//
//		String originalName = pstd.getName();
//		String modifiedName = removeWordIgnoreCase("Retail", originalName);
//		modifiedName = removeWordIgnoreCase("Wholesale", modifiedName);
//		modifiedName = removeWordIgnoreCase("Whole sale", modifiedName);
//
//		logger.debug("modifiedName: " + modifiedName);
//
//		Set<Product> setPsts = productService.getByNameLike(modifiedName.trim());
//
//		if (setPsts != null && setPsts.size() > 0) {
//			setPsts.remove(pstd);
//			for (Product psts : setPsts) {
//				logger.debug(psts.getName());
//			}
//
//		} else {
//			setPsts = new HashSet<Product>();
//		}
//
//		model.addAttribute("pstd", pstd);
//		model.addAttribute("setPsts", setPsts);
//		return pageController.productStocksTransfer(model);
//
//	}


	@GetMapping(path = "/stocks/add")
	public String productStocksAdd(Model model, @RequestParam(value = "id", required = false) String productId) {
		logger.debug(productId);
		Set<Product> productList = productService.getAll();
		if (productId != null)
			model.addAttribute("productId", Long.valueOf(productId));

		model.addAttribute("productList", productList);
		return pageController.productStocksAdd(model);
	}

	@GetMapping(path = "/stocks/edit/{id}")
	public String productStocksEdit(Model model, @PathVariable(value = "id", required = false) String productStockId) {
		productService.productStocksEdit(model, productStockId);
		return pageController.productStocksEdit(model);
	}

	@GetMapping(path = "/stocks/printbarcode/{id}")
	public String productStockPrintBarcode(Model model,
			@PathVariable(value = "id", required = false) String productStockId) {
		productService.productStockPrintBarcode(model, productStockId);
		return pageController.productStockPrintBarcodePage(model);
	}
	

	@PostMapping(path = "/stocks/add")
	public String productStocksAddProcess(Model model, @RequestParam Map<String, String> parameters) {
		productService.productStocksAddProcessPrep(model, parameters);
		return pageController.productStocksAdd(model);
	}
	
	@PostMapping(path = "/stocks")
	public String productStocksSearch(Model model, @RequestParam String search) {
		productService.productStocksSearch(model, search);
		return pageController.productStocks(model);
	}

	/**
	 * 
	 * Stocks Start End
	 * 
	 */

	/**
	 * 
	 * Products
	 * 
	 */

	@GetMapping(path = "/products/view/{productStockId}")
	public String productView(Model model, @PathVariable("productStockId") Long productId) {
		productService.productView(model, productId);
		return pageController.productView(model);
	}

	@GetMapping(path = "/products")
	public String productPage(Model model) {
		Set<Product> productList = productService.getAll();
		model.addAttribute("productList", productList);
		return pageController.productPage(model);
	}

	@GetMapping(path = "/products/edit/{productId}")
	public String productEdit(Model model, @PathVariable("productId") Long productId) {
		productService.productEdit(model, productId);
		return pageController.productEdit(model);
	}

	@GetMapping(path = "/products/add")
	public String productAdd(Model model) {
		productService.productAdd(model);
		return pageController.productAdd(model);
	}

	@PostMapping(path = "/products/add")
	public String productAddProcess(Model model, @RequestParam Map<String, String> parameters,
			@RequestParam MultipartFile file) {
		productService.productAddProcess(model, parameters, file);
		return pageController.productAdd(model);
	}

	@PostMapping(path = "/products/edit")
	public String productEditProcess(Model model, @RequestParam Map<String, String> parameters,
			@RequestParam MultipartFile file) {
		productService.productEditProcess(model, parameters, file);
		return this.productEdit(model, Long.valueOf(model.getAttribute("id").toString()));
	}

	@PostMapping(path = "/products")
	public String productSearch(Model model, @RequestParam("search") String search) {
		logger.debug("productSearch -> search: " + search);
		Set<Product> productList = productService.searchByName(search);
		logger.debug(productList.size());
		model.addAttribute("search", search);
		model.addAttribute("productList", productList);
		return pageController.productPage(model);

	}

	/**
	 * Utility
	 * 
	 */
//	private String removeWordIgnoreCase(String word, String originalString) {
//		String regex = "(?i)\\b\\s*" + word + "\\s*\\b"; // (?i) for case-insensitive
//		return originalString.replaceAll(regex, "");
//	}

}
