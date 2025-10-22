package com.boozeandice.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ImageController {
	
	private static final Logger logger = LogManager.getLogger(ImageController.class);
	
	@Value("${upload.product.directory}")
	private String uploadProductDirectory;
	
	@Value("${upload.profile.directory}")
	private String uploadProfileDirectory;
	
	@Value("${upload.barcode.directory}")
	private String uploadBarcodeDirectory;
	
	@GetMapping(path="/images/{imageLocalPath}")
	public ResponseEntity<Resource> fetchImage(@PathVariable String imageLocalPath) throws IOException {
		logger.debug("{ImageController} Start -> imageLocalPath: " + uploadProductDirectory + imageLocalPath);
		
		if(imageLocalPath.equals("null")) {
            return ResponseEntity.ok().build();
		}
		// Load the image file from the upload directory
        Path imagePath = Paths.get(uploadProductDirectory, imageLocalPath);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or MediaType.IMAGE_PNG depending on the image type
                    .body(resource);
        } else {
            // Return a default image or an error response if the image does not exist or is not readable
            // For example:
            return ResponseEntity.notFound().build();
        }
	}
	
	
	@GetMapping(path="/images/profile/{imageLocalPath}")
	public ResponseEntity<Resource> fetchImageforProfile(@PathVariable String imageLocalPath) throws IOException {
		logger.debug("{ImageController} Start -> imageLocalPath: " + uploadProfileDirectory + "\\" +  imageLocalPath);
		
		if(imageLocalPath.equals("null")) {
            return ResponseEntity.ok().build();
		}
		// Load the image file from the upload directory
        Path imagePath = Paths.get(uploadProfileDirectory, imageLocalPath);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or MediaType.IMAGE_PNG depending on the image type
                    .body(resource);
        } else {
            // Return a default image or an error response if the image does not exist or is not readable
            // For example:
            return ResponseEntity.notFound().build();
        }
	}
	
	@GetMapping(path="/images/barcode/{imageLocalPath}")
	public ResponseEntity<Resource> fetchImageforBarcode(@PathVariable String imageLocalPath) throws IOException {
		logger.debug("{ImageController} Start -> imageLocalPath: " + uploadBarcodeDirectory + "\\" +  imageLocalPath);
		
		if(imageLocalPath.equals("null")) {
            return ResponseEntity.ok().build();
		}
		// Load the image file from the upload directory
        Path imagePath = Paths.get(uploadBarcodeDirectory, imageLocalPath);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or MediaType.IMAGE_PNG depending on the image type
                    .body(resource);
        } else {
            // Return a default image or an error response if the image does not exist or is not readable
            // For example:
            return ResponseEntity.notFound().build();
        }
	}
	
}
