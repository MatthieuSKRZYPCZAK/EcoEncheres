package fr.eni.encheres.exception;

public class UpdateException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public UpdateException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return getMessage();
	}

}
