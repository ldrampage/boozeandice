package com.boozeandice.datastore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PrinterName;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boozeandice.entity.ProductCategory;
import com.boozeandice.repository.ProductCategoryRepository;

import jakarta.annotation.PostConstruct;

@Component
public class CategoryDataStore implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(CategoryDataStore.class);

	@Autowired
	private ProductCategoryRepository productCatRepo;

//	private static void sendCashDrawerCommand(PrintService printer) throws PrintException {
//		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//		Doc doc = new SimpleDoc(new byte[] { /* Your cash drawer command bytes */ }, flavor, null);
//		PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
//		DocPrintJob printJob = printer.createPrintJob();
//		printJob.print(doc, attributeSet);
//	}
//
//	private static PrintService findPrinter(String printerName) {
//		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//		for (PrintService printService : printServices) {
//			if (printService.getName().equals(printerName)) {
//				return printService;
//			}
//		}
//		return null;
//	}

	@PostConstruct
	public void init() {
		
//		logger.debug("Enter printOrderSlip()");
//		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//
//		for (PrintService printer : printServices) {
//			logger.debug("Printer: " + printer.getName());
//			if (printer.getName().contains("POS58")) {
//	
//				logger.debug("execute sample print");
//				StringBuilder stringBuilder = new StringBuilder();
//				stringBuilder.append("Order Slip");
//				stringBuilder.append("\n\n");
//				stringBuilder.append("Customer name: Lyndon");
//				stringBuilder.append("\n\n");
//				stringBuilder.append("Table No: 1");
//				stringBuilder.append("\n\n");
//				stringBuilder.append("Dine In");
//				stringBuilder.append("\n\n");
//				stringBuilder.append("Tap Silog | Php 99.00 | 2 | Php 198.00");
//				stringBuilder.append("\n");
//				stringBuilder.append("Sisig Silog | Php 99.00 | 2 | Php 198.00");
//				stringBuilder.append("\n");
//				stringBuilder.append("Bangus Silog | Php 169.00 | 1 | Php 169.00");
//				stringBuilder.append("\n");
//				stringBuilder.append("Total: Php 565.00" );
//				stringBuilder.append("\n\n\n\n");
//
//
//	            String documentContent = stringBuilder.toString();
//	            
//	            InputStream inputStream = new ByteArrayInputStream(documentContent.getBytes());
//	            DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//	            Doc doc = new SimpleDoc(inputStream, docFlavor, null);
//
//	            DocPrintJob printJob = printer.createPrintJob();
//	            try {
//	                printJob.print(doc,null);
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	            }
//			}
//		}

		logger.debug("Start inserting product categories");
		List<ProductCategory> productCategoryList = new ArrayList<>();
		ProductCategory category = null;
		String[] drinks = { "beer", "wine" };
		for (int x = 0; x < drinks.length; x++) {
			category = new ProductCategory();
			category.setName(drinks[x]);
			productCategoryList.add(category);

		}
		productCatRepo.saveAll(productCategoryList);

	}
	

}
