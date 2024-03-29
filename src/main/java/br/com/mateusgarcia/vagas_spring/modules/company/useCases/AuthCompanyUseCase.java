package br.com.mateusgarcia.vagas_spring.modules.company.useCases;

import br.com.mateusgarcia.vagas_spring.modules.company.dto.AuthCompanyDTO;
import br.com.mateusgarcia.vagas_spring.modules.company.dto.AuthCompanyResponseDTO;
import br.com.mateusgarcia.vagas_spring.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository
                .findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(
                        () -> {
                            throw new UsernameNotFoundException("Username/password incorrect");
                        });

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if(!passwordMatches) {
            throw new AuthenticationException();
        }

        List<String> roles = new ArrayList<String>();
        roles.add("COMPANY");

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withClaim("roles", roles)
                .withSubject(company.getId().toString())
                .sign(algorithm);

        return AuthCompanyResponseDTO
                .builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();
    }
}
