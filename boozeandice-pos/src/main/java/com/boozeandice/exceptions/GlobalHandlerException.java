package com.boozeandice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {
	
	@ExceptionHandler(Exception.class)
	public String handleUnknownExceptions(Exception ex, Model model){
		
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("message", ex.getMessage());
		
		return "pages/errorPage";
		
	}
	
	@ExceptionHandler(InvalidDateFormatException.class)
	public String handleInvalidDateFormatException(RuntimeException ex, Model model){
		
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("message", ex.getMessage());
		
		return "pages/errorPage";
		
	}
	
	@ExceptionHandler(UnknownHostException.class)
	public String handleUnknownHostException(RuntimeException ex, Model model){
		
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("message", ex.getMessage());
		
		return "pages/errorPage";
		
	}
	
	@ExceptionHandler(UnhandledIOException.class)
	public String handleUnhandledIOException(RuntimeException ex, Model model){
		
		model.addAttribute("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("message", ex.getMessage());
		
		return "pages/errorPage";
		
	}
	

}
