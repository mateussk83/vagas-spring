package br.com.mateusgarcia.vagasspring.modules.candidate.useCases;

import br.com.mateusgarcia.vagas_spring.exceptions.JobNotFoundException;
import br.com.mateusgarcia.vagas_spring.exceptions.UserNotFoundException;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateEntity;
import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateRepository;
import br.com.mateusgarcia.vagas_spring.modules.candidate.entity.ApplyJobEntity;
import br.com.mateusgarcia.vagas_spring.modules.candidate.repository.ApplyJobRepository;
import br.com.mateusgarcia.vagas_spring.modules.candidate.useCase.ApplyJobCandidateUseCase;
import br.com.mateusgarcia.vagas_spring.modules.company.entities.JobEntity;
import br.com.mateusgarcia.vagas_spring.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void sholudNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
        applyJobCandidateUseCase.execute(null, null);
        }
        catch(Exception e) {
             assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    public void shoudNotBeAbleToApplyJobWithJobNotFound() {
        var idCandidate = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        }
        catch(Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    public void shoudBeAbleToCreateANewApplyJob() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        var applyJobCreated = ApplyJobEntity.builder()
                .id(UUID.randomUUID())
                .build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate,idJob);
        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
