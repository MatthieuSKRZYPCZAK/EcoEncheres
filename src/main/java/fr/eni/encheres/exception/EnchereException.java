package fr.eni.encheres.exception;

public class EnchereException extends Exception{
private static final long serialVersionUID = 1L;
	
	public EnchereException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return getMessage();
	}

}
