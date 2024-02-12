package br.com.mateusgarcia.vagas_spring.modules.candidate.controllers;

import br.com.mateusgarcia.vagas_spring.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.mateusgarcia.vagas_spring.modules.candidate.useCase.FindAllJobsByFilterUseCase;
import br.com.mateusgarcia.vagas_spring.modules.candidate.useCase.ProfileCandidateUseCase;
import br.com.mateusgarcia.vagas_spring.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.mateusgarcia.vagas_spring.modules.candidate.CandidateEntity;
import br.com.mateusgarcia.vagas_spring.modules.candidate.useCase.CreateCandidateUseCase;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class
CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;
  @Autowired
  private FindAllJobsByFilterUseCase findAllJobsByFilterUseCase;

  @PostMapping("/")
  @Operation(
          summary = "Cadastro de candidato",
          description = "Essa função é responsavel por cadastrar um candidato"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", content = {
                  @Content(
                          schema = @Schema(implementation = CandidateEntity.class)
                  )
          }),
          @ApiResponse(responseCode = "400", description = "Usuario já existe")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok(result);
  }
  catch(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(
          summary = "Perfil do candidato",
          description = "Essa função é responsavel por buscar as informações do perfil do candidato"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", content = {
                  @Content(
                          schema = @Schema(implementation = ProfileCandidateResponseDTO.class)
                  )
          }),
          @ApiResponse(responseCode = "400", description = "User not found")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> get(HttpServletRequest request) {
    try {
      var idCandidate = request.getAttribute("candidate_id");
      var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
      return ResponseEntity.ok().body(profile);
    }
    catch(Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(
          summary = "Listagem de vagas disponiveis para o candidato",
          description = "Essa função é responsavel por listar todas as vagas disponiveis, baseada no filtro"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", content = {
                  @Content(
                          array = @ArraySchema(schema = @Schema(
                                  implementation = JobEntity.class
                          ))
                  )
          })
  })
  @SecurityRequirement(name = "jwt_auth")
  public List<JobEntity> findJobByFilter(@RequestParam String filter) {
      return findAllJobsByFilterUseCase.execute(filter);
  }
};
