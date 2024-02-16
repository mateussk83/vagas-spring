package br.com.mateusgarcia.vagas_spring.modules.candidate.useCase;

import br.com.mateusgarcia.vagas_spring.exceptions.UserNotFoundException;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateRepository;
import br.com.mateusgarcia.vagas_spring.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ResponseEntity<Object> execute(UUID idCandidate) {
        try {


        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        var result= ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .id(candidate.getId())
                .email(candidate.getEmail())
                .username(candidate.getUsername())
                .name(candidate.getName())
                .build();
        return ResponseEntity.ok().body(result);
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
