package com.boozeandice.data.sync;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class ScheduledTaskController {
	
	private static final Logger logger = LogManager.getLogger(ScheduledTaskController.class);
	
	@Autowired
	private DatabaseSyncService databaseSyncService;
	
	@Scheduled(cron = "0 */5 * * * *")
	public void executeSyncScript() {
		logger.debug("In executeSyncScript()");
		databaseSyncService.executeScript();
	}

}
