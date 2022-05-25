package exceptions;

public class InvalidInputException extends Exception{
	public InvalidInputException(String message){
		super(message);
	}

	public InvalidInputException(){
		super("Some inputs are invalid!");
	}
}