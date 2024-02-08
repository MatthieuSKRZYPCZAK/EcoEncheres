package fr.eni.encheres.exception;

public class RetraitException extends Exception {
	private static final long serialVersionUID = 1L;

	public RetraitException(String message) {
		super(message);
	}
	@Override
	public String toString() {
		return getMessage();
	}
}


