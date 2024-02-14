package br.com.mateusgarcia.vagas_spring.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Empresa não encontrada");
    }
}
