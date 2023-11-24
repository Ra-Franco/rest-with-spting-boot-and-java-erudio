package br.com.restwithsptingbootandjava.math;

public class SimpleMath {
	
	public Double sum(Double numberOne, Double numberTwo) {
		return numberOne + numberTwo;
	}
	
	public Double minus(Double numberOne, Double numberTwo) {
		return numberOne - numberTwo;
	}
	
	public Double multiplication(Double numberOne, Double numberTwo) {
		return numberOne * numberTwo;
	}
	
	public Double division(Double numberOne, Double numberTwo) {
		return numberOne / numberTwo;
	}
	
	public Double squareRoot(Double number) {
		return Math.sqrt(number);
	}
	
	
	public Double mean(Double numberOne, Double numberTwo) {
		return (numberTwo + numberOne)/2;
	}
	
}
