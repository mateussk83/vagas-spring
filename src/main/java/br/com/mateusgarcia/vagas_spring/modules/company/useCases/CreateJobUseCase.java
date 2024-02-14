package br.com.mateusgarcia.vagas_spring.modules.company.useCases;

import br.com.mateusgarcia.vagas_spring.exceptions.CompanyNotFoundException;
import br.com.mateusgarcia.vagas_spring.modules.company.entities.JobEntity;
import br.com.mateusgarcia.vagas_spring.modules.company.repositories.CompanyRepository;
import br.com.mateusgarcia.vagas_spring.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public ResponseEntity<Object> execute(JobEntity jobEntity) {
        try {
            companyRepository.findById(jobEntity.getCompanyId())
                    .orElseThrow(() -> {
                        throw new CompanyNotFoundException();
                    });

            var result = this.jobRepository.save(jobEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
