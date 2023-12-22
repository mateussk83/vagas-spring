package br.com.mateusgarcia.vagas_spring.modules.candidate.useCase;

import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateRepository;
import br.com.mateusgarcia.vagas_spring.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found");
                });

        return ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .id(candidate.getId())
                .email(candidate.getEmail())
                .username(candidate.getUsername())
                .name(candidate.getName())
                .build();


    }
}
