package com.boozeandice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.Address;
import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Shipment;
import com.boozeandice.entity.Transaction;
import com.boozeandice.entity.User;
import com.boozeandice.repository.AddressRepository;
import com.boozeandice.repository.ShipmentRepository;
import com.boozeandice.repository.TransactionRepository;


@Service
public class TransactionService {
	
	private static final Logger logger = LogManager.getLogger(TransactionService.class);
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShipmentRepository shipmentRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void transactionViewShipmentEdit(Model model, String id, Map<String, String> parameters) throws ParseException {
		for(Map.Entry<String, String> map : parameters.entrySet()) {
			logger.debug(map.getKey() + ": " + map.getValue());
		}
		Transaction transaction = this.getById(Long.valueOf(id));
		User user = userService.getById(Long.valueOf(parameters.get("deliveryDriver")));
		Shipment shipment = transaction.getShipment();
		Date date = null;
		date = new SimpleDateFormat("MM/dd/yyyy").parse(parameters.get("deliverydate"));
		Address destinationAddress = shipment.getDestinationAddress();
		destinationAddress.setAdditionalAddressDetails(parameters.get("address"));
		destinationAddress.setLandmark(parameters.get("landmark"));
		destinationAddress = addressRepo.save(destinationAddress);
		
		shipment.setDestinationAddress(destinationAddress);
		shipment.setEstimatedDeliveryDate(date);
		shipment.setDeliveryDriver(user);
		shipment.setShipmentStatus(parameters.get("shipmentStatus"));
		shipmentRepo.save(shipment);
		
		transaction.setShipment(shipment);
		this.save(transaction);
	}
	
	public void transactionViewPage(Model model, String id) {
		Transaction transaction = this.getById(Long.valueOf(id));
		Set<User> userList = userService.getByJobPositionId(Long.valueOf(7));
		model.addAttribute("transaction", transaction);
		model.addAttribute("deliveryDriver",userList);
	}
	
	public Transaction save(Transaction transaction) {
		return transactionRepo.save(transaction);
	}
	
	public Set<Transaction> getAll(){
		Set<Transaction> setT = new HashSet<>(transactionRepo.findAll());
		return setT;
	}
	
	public Transaction getById(Long transactionId) {
		Optional<Transaction> optTrans = transactionRepo.findById(transactionId);
		if(optTrans.isPresent()) {
			return optTrans.get();
		}
		return null;
	}
	
	public Set<Transaction> getByCashDrawer(CashDrawer cashDrawer) {
		return transactionRepo.findByCashdrawer(cashDrawer);
	}
	
	public void delete(Transaction transaction) {
		transactionRepo.delete(transaction);
	}
	
	public Set<Transaction> getByTransactionDate(Date date) {
		Calendar start = Calendar.getInstance();
		start.setTime(date);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);
		return transactionRepo.findByTransactionDateTimeBetween(start.getTime(), end.getTime());
	}
	
	public Set<Transaction> getByToday() {
		Calendar startOfDay = Calendar.getInstance();
        startOfDay.setTime(new Date());
        startOfDay.set(Calendar.HOUR_OF_DAY, 0);
        startOfDay.set(Calendar.MINUTE, 0);
        startOfDay.set(Calendar.SECOND, 0);
        startOfDay.set(Calendar.MILLISECOND, 0);

        Calendar endOfDay = Calendar.getInstance();
        endOfDay.setTime(new Date()); 
        endOfDay.set(Calendar.HOUR_OF_DAY, 23);
        endOfDay.set(Calendar.MINUTE, 59);
        endOfDay.set(Calendar.SECOND, 59);
        endOfDay.set(Calendar.MILLISECOND, 999);
		return transactionRepo.findByTransactionDateTimeBetween(startOfDay.getTime(), endOfDay.getTime());
	}

}
