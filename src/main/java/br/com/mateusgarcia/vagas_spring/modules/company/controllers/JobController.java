package br.com.mateusgarcia.vagas_spring.modules.company.controllers;

import br.com.mateusgarcia.vagas_spring.modules.company.dto.CreateJobDTO;
import br.com.mateusgarcia.vagas_spring.modules.company.entities.JobEntity;
import br.com.mateusgarcia.vagas_spring.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas", description = "Informações das vagas")
    @Operation(
            summary = "Cadastro de vaga",
            description = "Essa função é responsavel por cadastrar as vagas dentro da empresa"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = JobEntity.class)
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
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
