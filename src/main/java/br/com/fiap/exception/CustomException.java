package br.com.fiap.exception;

/**
 * Classe de exceção personalizada para gerenciar erros na aplicação.
 */
public class CustomException extends RuntimeException {
    private int statusCode;

    public CustomException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
