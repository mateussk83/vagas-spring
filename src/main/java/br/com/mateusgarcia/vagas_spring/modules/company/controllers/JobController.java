package br.com.mateusgarcia.vagas_spring.modules.company.controllers;

import br.com.mateusgarcia.vagas_spring.modules.company.dto.CreateJobDTO;
import br.com.mateusgarcia.vagas_spring.modules.company.entities.JobEntity;
import br.com.mateusgarcia.vagas_spring.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        JobEntity jobEntity = JobEntity.builder()
                .description(createJobDTO.getDescription())
                .level(createJobDTO.getLevel())
                .benefits(createJobDTO.getBenefits())
                .companyId(UUID.fromString(companyId.toString()))
                .build();

        return this.createJobUseCase.execute(jobEntity);
    }
}
