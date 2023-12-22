package br.com.mateusgarcia.vagas_spring.modules.candidate.controllers;

import br.com.mateusgarcia.vagas_spring.modules.candidate.useCase.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.mateusgarcia.vagas_spring.exceptions.UserFoundException;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateEntity;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateRepository;
import br.com.mateusgarcia.vagas_spring.modules.candidate.useCase.CreateCandidateUseCase;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok(result);
  }
  catch(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<Object> get(HttpServletRequest request) {
    try {
      var idCandidate = request.getAttribute("candidate_id");
      var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
      return ResponseEntity.ok().body(profile);
    }
    catch(Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
};
