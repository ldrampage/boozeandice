package com.boozeandice.utility;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.boozeandice.local.entity.Product;
import com.boozeandice.local.entity.Transaction;
import com.boozeandice.local.entity.TransactionItem;

@Component
public class Utilities {

	private static final Logger logger = LogManager.getLogger(Utilities.class);

	public List<Set<Product>> organizeProductsDisplay(Set<Product> productList) {
		List<Set<Product>> productsDisplay = new ArrayList<>();

		int counter = 3;
		int loop = 0;
		int row = 0;
		int locator = 0;
		Set<Product> insertToRows = new HashSet<Product>();
		for (Product product : productList) {
			if (loop < counter) {
				insertToRows.add(product);
				loop++;
			} else {
				loop = 1;
				productsDisplay.add(insertToRows);
				insertToRows = new HashSet<>();
				insertToRows.add(product);
				row++;
			}
			locator++;
			if (locator == productList.size()) {
				productsDisplay.add(insertToRows);
			}
		}

		return productsDisplay;
	}

	public boolean isNumeric(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

//	public void openCashDrawer() {
//		logger.debug("Enter openCashDrawer()");
//		byte[] open = { 27, 112, 0, 25, 125, (byte) 250 };
//		PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
//		if (pservice.createPrintJob() != null) {
//			DocPrintJob job = pservice.createPrintJob();
//			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//			Doc doc = new SimpleDoc(open, flavor, null);
//			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//			try {
//				job.print(doc, aset);
//			} catch (PrintException ex) {
//				System.out.println(ex.getMessage());
//			}
//		}
//		logger.debug("Exit openCashDrawer()");
//	}

//	public String composeOrderSlip(Transaction transaction) {
//		logger.debug("Enter composeOrderSlip()");
//		String result = null;
//		String customer = "";
//		// boolean takeout = false;
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("**** DonsTAP Silogan ****");
//		stringBuilder.append("\n\n");
//		stringBuilder.append("Order Slip");
//		stringBuilder.append("\n\n");
//		stringBuilder.append("Invoice #: " + transaction.getInvoiceNumber());
//
//		if (!transaction.getSoldTo().isEmpty()) {
//			customer = transaction.getSoldTo();
//		} else if (transaction.getCustomer() != null) {
//			customer = transaction.getCustomer().getFname();
//		}
//
//		stringBuilder.append("Customer name: " + customer);
//		stringBuilder.append("\n");
//		stringBuilder.append("Table No: " + transaction.getTableNo());
//		// stringBuilder.append("\n\n");
//		// stringBuilder.append("Dine In");
//		stringBuilder.append("\n");
//
//		for (TransactionItem ti : transaction.getTransactionItem()) {
//			String name = ti.getProduct().getName();
//			Double price = ti.getProduct().getPrice();
//			Long qty = ti.getQuantity();
//			Long subTotal = (long) (price * qty);
//			stringBuilder.append(name + " | " + "Php " + price + " | " + qty + " | " + "Php " + subTotal);
//			stringBuilder.append("\n");
//		}
//		stringBuilder.append("\n");
//		stringBuilder.append("SubTotal: " + transaction.getSubTotal());
//		if (transaction.getPackaging() != null && transaction.getPackaging() > 0) {
//			stringBuilder.append("\n");
//			stringBuilder.append("Packaging: " + transaction.getPackaging());
//		}
//		if (transaction.getDiscount() != null && transaction.getDiscount() > 0) {
//			stringBuilder.append("\n");
//			stringBuilder.append("Discount: " + transaction.getDiscount());
//		}
//		stringBuilder.append("\n");
//		stringBuilder.append("Cash Received: " + transaction.getCashReceived());
//		stringBuilder.append("\n");
//		stringBuilder.append("Total: Php " + transaction.getTotal());
//		stringBuilder.append("\n");
//		stringBuilder.append("Change: " + (transaction.getCashReceived() - transaction.getTotal()));
//		stringBuilder.append("\n\n");
//		stringBuilder.append("**** DonsTAP Silogan ****");
//		stringBuilder.append("\n\n\n\n");
//		result = stringBuilder.toString();
//		logger.debug("Exit composeOrderSlip()");
//		return result;
//	}

//	public void printOrderSlip(String orderSlipMessage) {
//		logger.debug("Enter printOrderSlip()");
//		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//
//		for (PrintService printer : printServices) {
//			logger.debug("Printer: " + printer.getName());
//			if (printer.getName().contains("POS58")) {
//
//				logger.debug("execute sample print");
//				String documentContent = orderSlipMessage;
//
//				InputStream inputStream = new ByteArrayInputStream(documentContent.getBytes());
//				DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//				Doc doc = new SimpleDoc(inputStream, docFlavor, null);
//
//				DocPrintJob printJob = printer.createPrintJob();
//				try {
//					printJob.print(doc, null);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		logger.debug("Exit printOrderSlip()");
//	}

}
