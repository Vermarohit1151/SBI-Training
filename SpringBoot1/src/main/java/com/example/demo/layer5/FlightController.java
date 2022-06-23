package com.example.demo.layer5;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.FlightPDFView;
import com.example.demo.ResponseStatus;
import com.example.demo.exceptions.FlightListEmptyException;
import com.example.demo.exceptions.FlightNotFoundException;
import com.example.demo.layer2.Flight;
import com.example.demo.layer4.FlightService;


// http://localhost:8080
//					|
// http://localhost:4200

@CrossOrigin			//CORS
@RestController
@RequestMapping("/flight")
public class FlightController {

	@Autowired
	FlightService flightService;
//import org.springframework.http.ResponseEntity;
	@RequestMapping("/getFlight/{fid}")
	public ResponseEntity<Flight> getFlightObject(@PathVariable("fid") int flightNumber) throws FlightNotFoundException 
	{
		Flight flight = null;
		ResponseEntity responseRef=null;
		try {
			flight = flightService.findFlightByIdService(flightNumber);
			responseRef = ResponseEntity.ok(flight);
			return responseRef;
		} catch (FlightNotFoundException e) {
			throw e;
			//System.out.println("Inside flight not found exception ");
			//ResponseStatus respStatus = new ResponseStatus();
			//respStatus.setMessage(e.getMessage());
			//responseRef = ResponseEntity.status(HttpStatus.NOT_FOUND).body(respStatus);
			//return ;
		}
		//return responseRef;
	}
	
	
	@RequestMapping("/getFlights")
	public ResponseEntity getFlights() throws FlightListEmptyException 
	{
		List<Flight> flightList = new ArrayList<Flight>();
		ResponseEntity responseRef=null;
		try {
			flightList = flightService.listAllFlightService();
			responseRef = ResponseEntity.ok(flightList);
			return responseRef;
		} catch (FlightListEmptyException e) {
			throw e;
			//System.out.println("Inside flight not found exception ");
			//ResponseStatus respStatus = new ResponseStatus();
			//respStatus.setMessage(e.getMessage());
			//responseRef = ResponseEntity.status(HttpStatus.NOT_FOUND).body(respStatus);
			//return ;
		}
		//return responseRef;
	}
	
	@PutMapping("/updateFlight")
	public ResponseEntity<Flight> modifyFlight(@RequestBody Flight flight)
	{
		ResponseEntity<Flight> responseRef=null;
		Flight modifiedFlight = flightService.saveService(flight);
		responseRef = ResponseEntity.ok(modifiedFlight);
		return responseRef;
	}
	
	@PostMapping("/addFlight")
	public ResponseEntity<Flight> addFlight(@RequestBody Flight flight)
	{
		ResponseEntity<Flight> responseRef=null;
		Flight newFlight = flightService.saveService(flight);
		responseRef = ResponseEntity.ok(newFlight);
		return responseRef;
	}
	
	@DeleteMapping("/deleteFlight/{fid}")
	public String deleteFlight(@PathVariable("fid") int flightNumber )
	{
		
		 try {
			flightService.deleteByIdService(flightNumber);
			return "Flight Succesfully Deleted";
		} catch (FlightNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Flight delete failed";
		
	
	}
	
	@Autowired
	FlightPDFView pdfView;
	
	@RequestMapping("/getPdf")
	public FlightPDFView list() {
		return pdfView;
	}
}
