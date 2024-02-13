package br.com.mateusgarcia.vagas_spring.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Vaga não existe");
    }
}
