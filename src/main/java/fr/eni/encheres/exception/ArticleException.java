package fr.eni.encheres.exception;

public class ArticleException extends Exception{
	private static final long serialVersionUID = 1L;

	public ArticleException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}
