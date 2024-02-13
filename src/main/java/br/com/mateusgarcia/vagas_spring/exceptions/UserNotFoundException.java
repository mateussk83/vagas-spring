package br.com.mateusgarcia.vagas_spring.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuário não existe");
    }
}
