package com.boozeandice.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

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

	public byte[] generateCode128Barcode(String batchStockId, String productId, String productName, int width,
			int height) throws WriterException, IOException {
		// Combine batchStockId, productId, and productName into a single string
		String barcodeText = batchStockId + " | " + productId + " | " + productName;
		String barcodeFileName = batchStockId + "_" + productId + "_" + productName;

		// Define barcode encoding hints
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.MARGIN, 25);

		// Generate the Code 128 barcode
		BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, BarcodeFormat.CODE_128, width, height, hints);

		// Create a BufferedImage from the BitMatrix
		BufferedImage bufferedImage = toBufferedImage(bitMatrix);

		// Add the number as text below the barcode with margins
		//addTextToImage(bufferedImage, barcodeText, 0,0);

		String filename = generateUniqueFilename(barcodeFileName);
		saveImage(bufferedImage, filename);

		// Convert the BufferedImage to a byte array (image)
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ImageIO.write(bufferedImage, "PNG", outputStream);
//		return outputStream.toByteArray();
		return filename.getBytes();
	}

	// Helper method to convert BitMatrix to BufferedImage
	private BufferedImage toBufferedImage(BitMatrix bitMatrix) {
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}

		return image;
	}

	// Helper method to generate a unique filename (customize this logic as needed)
	private String generateUniqueFilename(String barcodeFileName) {
		// You can use timestamp, UUID, or any other logic to generate a unique filename
		return barcodeFileName + "_" + System.currentTimeMillis() + ".png";
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

}
