package br.com.gerenciamentomudancasapi.exception;

public class SistemaExistenteException extends RuntimeException{

    public SistemaExistenteException(String mensagem) {
        super(mensagem);
    }
}
