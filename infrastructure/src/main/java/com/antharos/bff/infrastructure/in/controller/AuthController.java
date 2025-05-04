package com.antharos.bff.infrastructure.in.controller;

import com.antharos.bff.application.commands.login.LoginCommand;
import com.antharos.bff.application.commands.login.LoginCommandHandler;
import com.antharos.bff.application.commands.signup.SignUpCommand;
import com.antharos.bff.application.commands.signup.SignUpCommandHandler;
import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.infrastructure.config.security.jwt.JwtService;
import com.antharos.bff.infrastructure.in.dto.employee.RegisterUserRequest;
import com.antharos.bff.infrastructure.in.dto.login.LoginRequest;
import com.antharos.bff.infrastructure.in.dto.login.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final LoginCommandHandler loginCommandHandler;
  private final SignUpCommandHandler signUpCommandHandler;
  private final JwtService jwtService;

  @PostMapping("/signup")
  public ResponseEntity<Void> register(@RequestBody RegisterUserRequest request) {
    final SignUpCommand command =
        SignUpCommand.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build();
    this.signUpCommandHandler.handle(command);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    final LoginCommand command =
        LoginCommand.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build();

    final Employee authenticatedEmployee = this.loginCommandHandler.handle(command);

    final String jwtToken = this.jwtService.generateToken(authenticatedEmployee);

    final LoginResponse loginResponse =
        new LoginResponse(jwtToken, this.jwtService.getExpirationTime());
    return ResponseEntity.ok(loginResponse);
  }
}
