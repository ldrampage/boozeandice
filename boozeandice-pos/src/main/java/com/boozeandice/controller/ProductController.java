package com.boozeandice.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.entity.ProductStock;
import com.boozeandice.entity.User;
import com.boozeandice.service.CategoryService;
import com.boozeandice.service.ProductService;
import com.boozeandice.service.ProductStockService;
import com.boozeandice.service.UserService;

@Controller
@RequestMapping(path = "/product")
public class ProductController {

	private static final Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	private PageController pageController;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductStockService productStockService;

	@Autowired
	private UserService userService;

	@Value("${upload.product.directory}")
	private String uploadDirectory;

	@GetMapping(path = "/category")
	public String productCategory(Model model) {
		Set<ProductCategory> productCategoryList = categoryService.getAll();
		model.addAttribute("productCategoryList", productCategoryList);
		return pageController.productCategory(model);
	}

	@GetMapping(path = "/category/add")
	public String productCategoryCreatePage(Model model) {
		return pageController.productCategoryCreatePage(model);

	}

	@PostMapping(path = "/category/add")
	public String productCategoryCreateProcess(Model model, @RequestParam Map<String, String> parameters) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setName(parameters.get("category_name"));
		productCategory.setDescription(parameters.get("category_description"));
		Map<String, String> message = new HashMap<String, String>();

		try {
			categoryService.save(productCategory);
			message.put("status", "success");
		} catch (Exception e) {
			message.put("status", "error");
			message.put("message", e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("message", message);
		return pageController.productCategoryCreatePage(model);
	}
	
	@GetMapping(path = "/category/edit/{id}")
	public String productCategoryEditPage(Model model, @PathVariable("id") String id) {
		
		ProductCategory category = categoryService.getById(Long.valueOf(id));
		
		model.addAttribute("category", category);
		return pageController.productCategoryEditPage(model);
	}
	
//	@GetMapping(path="/cateegory")
//	public String productCategoryEditProcess(Model model, @RequestParam Map<String, String> parameters) {
//		
//	}

	/**
	 * 
	 * Stocks
	 * 
	 */

	@GetMapping(path = "/stocks")
	public String productStocks(Model model) {
		List<ProductStock> productStockList = productStockService.getAll();
		model.addAttribute("productStockList", productStockList);
		return pageController.productStocks(model);
	}

	@PostMapping(path = "/stocks")
	public String productStocksSearch(Model model, @RequestParam("search") String search) {
		logger.debug("productStocksSearch -> search: " + search);
		Set<Product> productList = productService.searchByName(search);
		List<ProductStock> productStockList = new ArrayList<ProductStock>();

		ProductStock productStock = null;
		for (Product product : productList) {
			productStockList.addAll(productStockService.getByProduct(product));
		}

		model.addAttribute("search", search);
		model.addAttribute("productStockList", productStockList);
		return pageController.productStocks(model);
	}

	@GetMapping(path = "/stocks/add")
	public String productStocksAdd(Model model, @RequestParam(value = "id", required = false) String productId) {
		logger.debug(productId);
		Set<Product> productList = productService.getAll();
		if (productId != null)
			model.addAttribute("productId", Long.valueOf(productId));

		model.addAttribute("productList", productList);
		return pageController.productStocksAdd(model);
	}

	@PostMapping(path = "/stocks/add")
	public String productStocksAddProcess(Model model, @RequestParam Map<String, String> parameters) {
		Map<String, String> message = new HashMap<String, String>();
		ProductStock productStock = new ProductStock();

		try {
			Product product = productService.getById(Long.valueOf(parameters.get("product")));
			User user = userService.getByUsername("lxbordo");
			Date purchaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(parameters.get("purchasedate").trim());

			productStock.setProduct(product);
			productStock.setQuantity(Long.valueOf(parameters.get("quantity")));
			productStock.setCost(Double.valueOf(parameters.get("cost")));
			productStock.setPurchaseDate(purchaseDate);
			productStock.setNotes(parameters.get("notes"));
			productStock.setCreatedBy(user);
			productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));

			Long newStockBatch = productStock.getQuantity();
			product.setStocks(product.getStocks() + newStockBatch);

			productStockService.save(productStock);
			productService.save(product);

			message.put("status", "success");
		} catch (Exception ex) {
			message.put("status", "error");
			message.put("message", ex.getMessage());
			ex.printStackTrace();
		}

		Set<Product> productList = productService.getAll();
		model.addAttribute("productList", productList);
		model.addAttribute("message", message);
		return pageController.productStocksAdd(model);
	}

	/**
	 * 
	 * Products
	 * 
	 */

	@GetMapping(path = "/products/view/{productStockId}")
	public String productView(Model model, @PathVariable("productStockId") Long productId) {
		Product product = productService.getById(productId);
		List<ProductStock> productStockList = null;
		Double totalCost = 0.0;
		if (product.getProductStock() != null) {
			productStockList = productStockService.getByProduct(product);
			for (ProductStock ps : productStockList) {
				totalCost = totalCost + ps.getCost();
			}
		}

		// TODO to add trasactions in the products view

		model.addAttribute("totalCost", totalCost);
		model.addAttribute("productStockList", productStockList);
		model.addAttribute("product", product);
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
		Product product = productService.getById(productId);
		Set<ProductCategory> productCategoryList = categoryService.getAll();
		logger.debug(product.getName());
		logger.debug(product.getImgLocation());
		model.addAttribute("product", product);
		model.addAttribute("selectedValue", product.getProductCategory().getId());
		model.addAttribute("categoryList", productCategoryList);
		return pageController.productEdit(model);
	}

	@GetMapping(path = "/products/add")
	public String productAdd(Model model) {
		Map<String, String> message = new HashMap<String, String>();
		Set<ProductCategory> productCategoryList = categoryService.getAll();
		model.addAttribute("categoryList", productCategoryList);
		model.addAttribute("message", message);
		return pageController.productAdd(model);
	}

	@PostMapping(path = "/products/add")
	public String productAddProcess(Model model, @RequestParam Map<String, String> parameters,
			@RequestParam("file") MultipartFile file) {
		Product product = new Product();
		product.setName(parameters.get("name"));
		product.setDescription(parameters.get("description"));
		product.setManufacturer(parameters.get("manufacturer"));
		product.setSupplier(parameters.get("supplier"));
		product.setNotes(parameters.get("notes"));
		product.setPrice(Double.valueOf(parameters.get("price")));
		
		if(parameters.get("packaging_fee") != null && !parameters.get("packaging_fee").isBlank())
			product.setPackagingFee(Double.valueOf(parameters.get("packaging_fee")));
		else
			product.setPackagingFee(0.0);
		
		if (parameters.get("cost") != null && !parameters.get("cost").isBlank())
			product.setCost(Double.valueOf(parameters.get("cost")));
		else
			product.setCost(0.0);
		
		Path filePath = null;
		Map<String, String> message = new HashMap<String, String>();
		try {
			if (!file.isEmpty()) {
				filePath = Paths.get(uploadDirectory, file.getOriginalFilename());
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				product.setImgLocation(file.getOriginalFilename());
			}
			ProductCategory productCategory = categoryService.getById(Long.valueOf(parameters.get("categoryId")));
			product.setProductCategory(productCategory);

			product.setCreatedBy(null); // TODO set USER
			product.setCreatedDate(new Timestamp(System.currentTimeMillis()));

			product = productService.save(product);
			message.put("status", "success");

		} catch (Exception e) {
			message.put("status", "error");
			message.put("message", e.getMessage());
			e.printStackTrace();
		}

		Set<ProductCategory> productCategoryList = categoryService.getAll();

		model.addAttribute("categoryList", productCategoryList);
		model.addAttribute("product", product);
		model.addAttribute("message", message);
		return pageController.productAdd(model);
	}

	@PostMapping(path = "/products/edit")
	public String productEditProcess(Model model, @RequestParam Map<String, String> parameters,
			@RequestParam("file") MultipartFile file) {

		Product product = productService.getById(Long.valueOf(parameters.get("productId")));
		product.setName(parameters.get("name"));
		product.setDescription(parameters.get("description"));
		product.setManufacturer(parameters.get("manufacturer"));
		product.setNotes(parameters.get("notes"));
		product.setSupplier(parameters.get("supplier"));
		product.setPrice(Double.valueOf(parameters.get("price")));
		
		if(parameters.get("packaging_fee") != null && !parameters.get("packaging_fee").isBlank())
			product.setPackagingFee(Double.valueOf(parameters.get("packaging_fee")));
		
		if (parameters.get("cost") != null && !parameters.get("cost").isBlank())
			product.setCost(Double.valueOf(parameters.get("cost")));
		
		Path filePath = null;
		Map<String, String> message = new HashMap<String, String>();
		try {

			// delete existing image file
			if (!file.isEmpty()) {
				if (product.getImgLocation() != null) {
					Path imagePath = Paths.get(uploadDirectory, product.getImgLocation());
					if (Files.exists(imagePath)) {
						Files.delete(imagePath);
					}
				}
				// save the new image file
				filePath = Paths.get(uploadDirectory, file.getOriginalFilename());
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				product.setImgLocation(file.getOriginalFilename());
			}

			ProductCategory productCategory = categoryService.getById(Long.valueOf(parameters.get("categoryId")));
			product.setProductCategory(productCategory);

			product = productService.save(product);
			message.put("status", "success");

		} catch (Exception e) {
			message.put("status", "error");
			message.put("message", e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("message", message);
		return this.productEdit(model, product.getId());
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

}
