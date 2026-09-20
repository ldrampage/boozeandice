package com.boozeandice.service;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.boozeandice.config.UserDetailsImpl;
import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.entity.ProductStock;
import com.boozeandice.entity.User;
import com.boozeandice.repository.ProductRepository;
import com.boozeandice.utility.BarcodeGenerator;

import jakarta.transaction.Transactional.TxType;

@Service
public class ProductService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(ProductService.class);
	
	private final ProductRepository productRepo;
	private final CategoryService categoryService;
	private final ProductService productService;
	private final UserService userService;
	private final ProductStockService productStockService;
	private final BarcodeGenerator barcodeGenerator;
	
	public ProductService(ProductRepository productRepo, CategoryService categoryService, ProductService productService,
			UserService userService, ProductStockService productStockService, BarcodeGenerator barcodeGenerator) {
		super();
		this.productRepo = productRepo;
		this.categoryService = categoryService;
		this.productService = productService;
		this.userService = userService;
		this.productStockService = productStockService;
		this.barcodeGenerator = barcodeGenerator;
	}

	@Value("${upload.product.directory}")
	private String uploadDirectory;
	
	public void productEditProcess(Model model, @RequestParam Map<String, String> parameters,
			@RequestParam MultipartFile file) {

		Product product = productService.getById(Long.valueOf(parameters.get("productId")));
		product.setName(parameters.get("name"));
		product.setDescription(parameters.get("description"));
		product.setManufacturer(parameters.get("manufacturer"));
		product.setNotes(parameters.get("notes"));
		product.setSupplier(parameters.get("supplier"));
		product.setPrice(Double.valueOf(parameters.get("price")));

		if (parameters.get("packaging_fee") != null && !parameters.get("packaging_fee").isBlank())
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
		model.addAttribute("id", product.getId());
	}
	
	public void productAddProcess(Model model, @RequestParam Map<String, String> parameters, MultipartFile file) {
		Product product = new Product();
		product.setName(parameters.get("name"));
		product.setDescription(parameters.get("description"));
		product.setManufacturer(parameters.get("manufacturer"));
		product.setSupplier(parameters.get("supplier"));
		product.setNotes(parameters.get("notes"));
		product.setPrice(Double.valueOf(parameters.get("price")));

		if (parameters.get("packaging_fee") != null && !parameters.get("packaging_fee").isBlank())
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
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			User user = userService.getByUsername(userDetails.getUsername());
			product.setCreatedBy(user); 
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
	}
	
	public void productAdd(Model model) {
		Map<String, String> message = new HashMap<String, String>();
		Set<ProductCategory> productCategoryList = categoryService.getAll();
		model.addAttribute("categoryList", productCategoryList);
		model.addAttribute("message", message);
	}
	
	public void productEdit(Model model, Long productId) {
		Product product = productService.getById(productId);
		Set<ProductCategory> productCategoryList = categoryService.getAll();
		logger.debug(product.getName());
		logger.debug(product.getImgLocation());
		model.addAttribute("product", product);
		model.addAttribute("selectedValue", product.getProductCategory().getId());
		model.addAttribute("categoryList", productCategoryList);
	}
	
	public void productView(Model model, Long productId) {
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
	}
	
	public void productStockPrintBarcode(Model model,String productStockId) {
		logger.debug("In productStockPrintBarcode() -> productStockId=" + productStockId);
		ProductStock productStock = productStockService.getById(Long.valueOf(productStockId));
		model.addAttribute("productName", productStock.getProduct().getName());
		model.addAttribute("barcodeImageLocation", productStock.getBarcodeImageLocation());
	}
	
	public void productStocksEdit(Model model,String productStockId) {
		logger.debug(productStockId);
		ProductStock productStock = productStockService.getById(Long.valueOf(productStockId));

		model.addAttribute("productStock", productStock);
		model.addAttribute("product", productStock.getProduct());
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void productStocksAddProcessPrep(Model model, @RequestParam Map<String, String> parameters) {
		Map<String, String> message = new HashMap<String, String>();
		ProductStock productStock = new ProductStock();

		try {
			Product product = productService.getById(Long.valueOf(parameters.get("product")));
			User user = userService.getByUsername(parameters.get("username"));
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

			productStock = productStockService.save(productStock);

			// Generate barcode start
			logger.debug("generatebarcode: " + parameters.get("generatebarcode"));
			if (parameters.get("generatebarcode") != null && parameters.get("generatebarcode").equals("on")) {
				Map<String, String> barcodeInfoMap = barcodeGenerator.generateUPCABarcode(
						productStock.getId().toString(), product.getId().toString(), product.getName(), 100, 50);
				productStock.setBarcodeDigits(Long.valueOf(barcodeInfoMap.get("barcodeDigits")));
				productStock.setBarcodeDigitsv2(barcodeInfoMap.get("barcodeDigitsv2"));
				productStock.setBarcodeImageLocation(barcodeInfoMap.get("barcodeImgLocation"));
			}
			// Generate barcode end

			productStockService.save(productStock);

			message.put("status", "success");
		} catch (Exception ex) {
			message.put("status", "error");
			message.put("message", ex.getMessage());
			ex.printStackTrace();
		}

		Set<Product> productList = productService.getAll();
		model.addAttribute("productList", productList);
		model.addAttribute("message", message);
	}
	
	public void productStocks(Model model) {
		List<ProductStock> productStockList = productStockService.getAll();
		model.addAttribute("productStockList", productStockList);
	}
	
	public void productStocksSearch(Model model, String search) {
		logger.debug("productStocksSearch -> search: " + search);
		Set<Product> productList = productService.searchByName(search);
		List<ProductStock> productStockList = new ArrayList<ProductStock>();

		//ProductStock productStock = null;
		for (Product product : productList) {
			productStockList.addAll(productStockService.getByProduct(product));
		}

		model.addAttribute("search", search);
		model.addAttribute("productStockList", productStockList);
	}
	
	public void productCategoryEditPagePrep(Model model, String id) {
		ProductCategory category = categoryService.getById(Long.valueOf(id));
		model.addAttribute("category", category);
	}
	
	public void productCategoryCreateProcessPrep(Model model, Map<String, String> parameters) {
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
	}
	
	
	public void getProductCategory(Model model) {
		Set<ProductCategory> productCategoryList = categoryService.getAll();
		model.addAttribute("productCategoryList", productCategoryList);
	}
	
	public Set<Product> getAll(){
		return new HashSet<Product>(productRepo.findAll());
	}
	
	
	public Set<Product> getAllNonZeroStock(){
		return productRepo.findByStocksNonZero();
	}
	
	public Product getById(Long productId) {
		Optional<Product> productOpt = productRepo.findById(productId);
		if(productOpt.isPresent()) {
			return productOpt.get();
		}
		return null;
	}
	
	public Set<Product> searchByName(String search){
		return productRepo.findByNameIgnoreCaseLike(search + "%");
	}
	
	public Set<Product> getProductByCategory(ProductCategory category){
		return productRepo.findByProductCategory(category);
	}
		
	public Set<Product> getByProductCategoryAndProductStockGreaterThan(ProductCategory category){
		return productRepo.findByProductCategoryAndStocksGreaterThan(category, Long.valueOf(0));
	}
	
	public Set<Product> getByNameContainingAndStocksGreaterThan(String productName){
		return productRepo.findByNameContainingAndStocksGreaterThan(productName, Long.valueOf(0));
	}
	
	public Set<Product> getByNameContaining(String productName){
		
		return productRepo.findByNameContaining(productName);
	}
	
	public Product save(Product product) {
		return productRepo.save(product);
	}
	

}
