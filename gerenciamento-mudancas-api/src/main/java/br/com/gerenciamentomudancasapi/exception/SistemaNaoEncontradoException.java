package br.com.gerenciamentomudancasapi.exception;

public class SistemaNaoEncontradoException extends RuntimeException {

    public SistemaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
