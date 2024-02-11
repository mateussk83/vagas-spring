package br.com.mateusgarcia.vagas_spring.modules.candidate.useCase;

import br.com.mateusgarcia.vagas_spring.modules.company.entities.JobEntity;
import br.com.mateusgarcia.vagas_spring.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllJobsByFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {

        return this.jobRepository.findByDescriptionContaining(filter);
    }
}
