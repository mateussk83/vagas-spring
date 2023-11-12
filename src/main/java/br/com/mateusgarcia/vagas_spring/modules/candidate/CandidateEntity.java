package br.com.mateusgarcia.vagas_spring.modules.candidate;

import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @NotBlank
  @Pattern(regexp = "\\S+", message="o campo [username] não deve conter espaço")
  private String username;

  @Email(message = "O campo [email] deve conter um e-mail válido")
  private String email;

  @Length(min = 10, max = 100, message="a senha deve conter entre 10 a 100")
  private String password;
  private String description;
  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
