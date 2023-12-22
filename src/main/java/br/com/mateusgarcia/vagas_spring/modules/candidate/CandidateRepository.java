package br.com.mateusgarcia.vagas_spring.modules.candidate;

import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
  Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
  Optional<CandidateEntity> findByUsername(String username);
}
