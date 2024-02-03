package fr.eni.encheres.exception;

public class UtilisateurException extends Exception {
private static final long serialVersionUID = 1L;
	
	public UtilisateurException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return getMessage();
	}

}
