package br.ufpr.aquitemsus.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Registro não encontrado");
    }
}
