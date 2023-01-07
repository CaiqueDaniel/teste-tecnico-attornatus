package com.attornatus.testetecnico.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Recurso não encontrado");
    }
}
