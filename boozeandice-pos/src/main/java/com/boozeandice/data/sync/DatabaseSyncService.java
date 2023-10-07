package com.boozeandice.data.sync;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DatabaseSyncService {
	
	private static final Logger logger = LogManager.getLogger(DatabaseSyncService.class);
	
	@Value("classpath:export_import_script.bat")
	private Resource scriptResource;
	
	public void executeScript() {
		
		logger.debug("In executeScript()");
		
		try {
			
			String scriptPath = scriptResource.getFile().getAbsolutePath();
			
			logger.debug("filepath: " + scriptPath);
			ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", scriptPath);
			
			processBuilder.redirectErrorStream(true);
			
			Process process = processBuilder.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			
			while((line = reader.readLine()) != null) {
				logger.debug(line);
			}
			
			int exitCode = process.waitFor();
			logger.debug("Script execution exit code: " + exitCode);
			
			
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Out executeScript()");
	}
	
}
