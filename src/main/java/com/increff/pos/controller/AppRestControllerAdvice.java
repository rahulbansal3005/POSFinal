package com.increff.pos.controller;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.increff.pos.model.Data.MessageData;
import com.increff.pos.service.ApiException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppRestControllerAdvice {

	@ExceptionHandler(ApiException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageData handle(ApiException e) {
		MessageData data = new MessageData();
		data.setMessage(e.getMessage());
		return data;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final MessageData handleConstraintViolation(ConstraintViolationException ex) {
		List<String> details = ex.getConstraintViolations()
				.parallelStream()
				.map(e -> e.getPropertyPath() +" " + e.getMessage())
				.collect(Collectors.toList());
		MessageData data = new MessageData();
		String detailsString = details.toString();
		detailsString = detailsString.replace("[","").replace("]", "");
		String[] stringArray = detailsString.split(",");
		Arrays.parallelSetAll(stringArray, (i) -> stringArray[i].trim());
		HashSet<String> stringHashSet = new HashSet<>(Arrays.asList(stringArray));
		final StringBuilder finalString = new StringBuilder();
		stringHashSet.forEach((String string)-> finalString.append(", ").append(string));
		String resultString = finalString.substring(2);
		data.setMessage(resultString);
		return data;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageData handleException(HttpMessageNotReadableException e) {
		MessageData data = new MessageData();
		data.setMessage("Input is invalid");
		return data;
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public MessageData handle(Throwable e) {
		MessageData data = new MessageData();
		data.setMessage("An unknown error has occurred - " + e.getMessage());
		return data;
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public MessageData handleNumberFormatException(MethodArgumentNotValidException e) {
		MessageData data = new MessageData();
		data.setMessage("Wrong format received" + e.getMessage());
		return data;
	}

//	@ExceptionHandler(NumberFormatException.class)
//	public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
//		String message = "Expected Integer for field intValue, received " + ex.getMessage();
//		return ResponseEntity
//				.status(HttpStatus.BAD_REQUEST)
//				.body(message);
//	}

}