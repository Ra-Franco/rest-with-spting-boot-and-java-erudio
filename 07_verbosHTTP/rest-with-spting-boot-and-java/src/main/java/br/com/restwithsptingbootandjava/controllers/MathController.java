package br.com.restwithsptingbootandjava.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.restwithsptingbootandjava.converters.NumberConverter;
import br.com.restwithsptingbootandjava.exceptions.UnsuportedMathOperationException;
import br.com.restwithsptingbootandjava.math.SimpleMath;

@RestController
public class MathController {
	
	private SimpleMath math = new SimpleMath();

	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
			method=RequestMethod.GET)
	public Double sum(@PathVariable(value = "numberOne") String numberOne,@PathVariable(value = "numberTwo") String numberTwo){	
		if (!NumberConverter.isNumeric (numberTwo) || !NumberConverter.isNumeric (numberOne)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
			return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
		}
	@RequestMapping(value = "/minus/{numberOne}/{numberTwo}",
			method=RequestMethod.GET)
	public Double minus(@PathVariable(value = "numberOne") String numberOne, 
			@PathVariable(value = "numberTwo") String numberTwo){
		if (!NumberConverter.isNumeric (numberTwo) || !NumberConverter.isNumeric (numberOne)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return math.minus(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}",
			method=RequestMethod.GET)
	public Double multiplication(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo){
		if (!NumberConverter.isNumeric (numberTwo) || !NumberConverter.isNumeric (numberOne)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return math.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/division/{numberOne}/{numberTwo}",
			method=RequestMethod.GET)
	public Double division(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo){
		if (!NumberConverter.isNumeric (numberTwo) || !NumberConverter.isNumeric (numberOne)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return math.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}	
	
	@RequestMapping(value = "mean/{numberOne}/{numberTwo}",
			method=RequestMethod.GET)
	public Double mean(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo){
		if (!NumberConverter.isNumeric (numberTwo) || !NumberConverter.isNumeric (numberOne)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return math.mean(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "square/{number}",
			method=RequestMethod.GET)
	public Double squereRoot(@PathVariable(value = "number") String number){
		if (!NumberConverter.isNumeric(number)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return math.squareRoot(NumberConverter.convertToDouble(number));
	}
	
}
