package br.com.mateusgarcia.vagas_spring.modules.candidate.repository;

import br.com.mateusgarcia.vagas_spring.modules.candidate.entity.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
