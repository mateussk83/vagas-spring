package br.com.mateusgarcia.vagas_spring.modules.candidate.entity;

import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateEntity;
import br.com.mateusgarcia.vagas_spring.modules.company.entities.JobEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "apply_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    @JsonIgnore
    private JobEntity jobEntity;


    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    @JsonIgnore
    private CandidateEntity candidateEntity;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "job_id")
    private UUID jobId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
