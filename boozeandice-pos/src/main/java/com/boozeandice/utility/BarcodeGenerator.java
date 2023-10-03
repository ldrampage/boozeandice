package com.boozeandice.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

@Component
public class BarcodeGenerator {

	@Value("${upload.barcode.directory}")
	private String imageDirectory;

	public Map<String, String> generateUPCABarcode(String batchStockId, String productId, String productName, int width,
			int height) throws WriterException, IOException {
		// Combine batchStockId, productId, and productName into a single string
		String barcodeText = generateBardcodeText(productId, batchStockId);
		String barcodeFileName = barcodeText;
		Map<String, String> result = new HashMap<>();
		// Define barcode encoding hints
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.MARGIN, 25);

		// Generate the UPC_A barcode
		BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, BarcodeFormat.UPC_A, width, height, hints);

		// Create a BufferedImage from the BitMatrix
		BufferedImage bufferedImage = toBufferedImage(bitMatrix, barcodeText);
				
				//generateBarcodeImage(barcodeText, width, height);
				
				//toBufferedImage(bitMatrix);

		// Add the number as text below the barcode with margins
		//addTextToImage(bufferedImage, barcodeText, 0,0);

		barcodeFileName = generateUniqueFilename(barcodeFileName);
		result.put("barcodeDigits", barcodeText);
		result.put("barcodeDigitsv2", productId +"-"+batchStockId);
		result.put("barcodeImgLocation", barcodeFileName);
		saveImage(bufferedImage, barcodeFileName);

		// Convert the BufferedImage to a byte array (image)
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ImageIO.write(bufferedImage, "PNG", outputStream);
//		return outputStream.toByteArray();
		return result;
	}

	// Helper method to convert BitMatrix to BufferedImage
	private BufferedImage toBufferedImage(BitMatrix bitMatrix,String barcodeText) {
		 int width = bitMatrix.getWidth();
		    int height = bitMatrix.getHeight();
		    BufferedImage image = new BufferedImage(width, height + 30, BufferedImage.TYPE_INT_ARGB);

		    Graphics2D g = image.createGraphics();

		    // Clear the background
		    g.setColor(Color.WHITE);
		    g.fillRect(0, 0, width, height + 30);

		    // Draw the barcode
		    g.setColor(Color.BLACK);

		    for (int x = 0; x < width; x++) {
		        for (int y = 0; y < height; y++) {
		            if (bitMatrix.get(x, y)) {
		                image.setRGB(x, y, 0xFF000000);
		            }
		        }
		    }

		    // Draw the barcodeText at the bottom
		    g.setColor(Color.BLACK);
		    g.setFont(new Font("Arial", Font.PLAIN, 12));
		    int textWidth = g.getFontMetrics().stringWidth(barcodeText);
		    int textX = (width - textWidth) / 2; // Center the text horizontally
		    int textY = height + 15; // Adjust the Y position for the text
		    g.drawString(barcodeText, textX, textY);

		    g.dispose();
		    return image;
	}
	
	
	 // Generate a barcode image with digits at the bottom
    private static BufferedImage generateBarcodeImage(String barcodeText, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Clear the background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Draw the barcode
        g.setColor(Color.BLACK);
        // Replace with your barcode generation code
        // You can use libraries like ZXing for barcode generation

        // Draw the digits at the bottom
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        int textHeight = 20; // Adjust the height for the text
        int textX = 10; // Adjust the X position for the text
        int textY = height - 10; // Adjust the Y position for the text
        g.drawString(barcodeText, textX, textY);

        g.dispose();
        return image;
    }

	// Helper method to generate a unique filename (customize this logic as needed)
	private String generateUniqueFilename(String barcodeFileName) {
		// You can use timestamp, UUID, or any other logic to generate a unique filename
		return barcodeFileName + ".png";
	}

	// Helper method to add text to the BufferedImage with margins
//	private void addTextToImage(BufferedImage image, String text, int leftMargin, int topMargin) {
//		Graphics2D graphics = image.createGraphics();
//		graphics.setColor(Color.BLACK);
//		graphics.setFont(new Font("Arial", Font.PLAIN, 12));
//
//		// Calculate the available width for the text within the image size and margins
//		int availableWidth = image.getWidth() - leftMargin;
//
//		// Get the FontMetrics to measure text width
//		FontMetrics fontMetrics = graphics.getFontMetrics();
//		int textWidth = fontMetrics.stringWidth(text);
//
//		// Calculate the x-coordinate to center the text within the available width
//		int x = leftMargin + (availableWidth - textWidth) / 2;
//
//		// Calculate the y-coordinate to place the text below the barcode with top
//		// margin
//		int y = image.getHeight() + topMargin + fontMetrics.getHeight();
//
//		// Draw the text
//		graphics.drawString(text, x, y);
//		graphics.dispose();
//	}

	// Helper method to add text to the BufferedImage
	private void addTextToImage(BufferedImage image, String text) {
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Arial", Font.PLAIN, 12));

		// Position the text below the barcode
		int x = 10; // X-coordinate
		int y = image.getHeight() + 20; // Y-coordinate

		graphics.drawString(text, x, y);
		graphics.dispose();
	}

	// Helper method to save the image to the image directory
	private void saveImage(BufferedImage image, String filename) throws IOException {
		Path imagePath = Path.of(imageDirectory, filename);
		File imageFile = imagePath.toFile();

		// Ensure the parent directory exists
		imageFile.getParentFile().mkdirs();

		// Write the image data to the file
		ImageIO.write(image, "PNG", imageFile);
	}
	
	private String generateBardcodeText(String productId, String productStockId) {
		// Combine the product ID and product stock ID
        String mergedId = productId + productStockId;

        // Calculate the number of random digits needed to reach 11 digits
        int remainingDigits = 11 - mergedId.length();

        if (remainingDigits <= 0) {
            // The combined ID is already 11 digits or longer
            mergedId = mergedId.substring(0, 11); // Truncate to 11 digits
        } else {
            // Generate random digits for the remaining length
            Random random = new Random();

            for (int i = 0; i < remainingDigits; i++) {
                mergedId += random.nextInt(10); // Append random digit (0-9)
            }
        }

        // Calculate and append the checksum digit
        int checksumDigit = calculateUPCACheckDigit(mergedId);
        return mergedId + checksumDigit;
	}
	
	
	// Calculate the UPC-A checksum digit
    private static int calculateUPCACheckDigit(String barcode) {
        int sumOdd = 0;
        int sumEven = 0;

        for (int i = 0; i < barcode.length(); i++) {
            int digit = Character.getNumericValue(barcode.charAt(i));
            if (i % 2 == 0) {
                sumOdd += digit;
            } else {
                sumEven += digit;
            }
        }

        int total = (sumOdd * 3) + sumEven;
        int checkDigit = 10 - (total % 10);
        return (checkDigit == 10) ? 0 : checkDigit;
    }







}
