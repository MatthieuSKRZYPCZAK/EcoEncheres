package fr.eni.encheres.exception;

public class CategorieException extends Exception{
	private static final long serialVersionUID = 1L;

	public CategorieException(String message) {
		super(message);
	}
	@Override
	public String toString() {
		return getMessage();
	}
}