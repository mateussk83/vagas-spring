package br.com.mateusgarcia.vagas_spring.modules.candidate.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mateusgarcia.vagas_spring.exceptions.UserFoundException;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateEntity;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository
    .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).ifPresent((user) -> {
      throw new UserFoundException();
    });

    var password = passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(password);

    return this.candidateRepository.save(candidateEntity);
  }
}
