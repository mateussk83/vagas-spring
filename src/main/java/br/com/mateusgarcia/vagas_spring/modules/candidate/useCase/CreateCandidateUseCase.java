package br.com.mateusgarcia.vagas_spring.modules.candidate.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mateusgarcia.vagas_spring.exceptions.UserFoundException;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateEntity;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository
    .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).ifPresent((user) -> {
      throw new UserFoundException();
    });
    return this.candidateRepository.save(candidateEntity);
  }
}
