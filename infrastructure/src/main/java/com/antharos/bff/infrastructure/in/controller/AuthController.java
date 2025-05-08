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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operations related to authentication and user sign-up")
public class AuthController {

  private final LoginCommandHandler loginCommandHandler;
  private final SignUpCommandHandler signUpCommandHandler;
  private final JwtService jwtService;

  @PermitAll
  @PostMapping("/signup")
  @Operation(
          summary = "Register a new user",
          description = "Allows a new user to sign up by providing username and password.")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "User registered successfully"),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Invalid request",
                          content = @Content(schema = @Schema())),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Internal server error",
                          content = @Content(schema = @Schema()))
          })
  public ResponseEntity<Void> register(@RequestBody RegisterUserRequest request) {
    final SignUpCommand command =
        SignUpCommand.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build();
    this.signUpCommandHandler.handle(command);
    return ResponseEntity.ok().build();
  }

  @PermitAll
  @PostMapping("/login")
  @Operation(
          summary = "Login",
          description = "Authenticates a user and returns a JWT token for authorized access"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Login successful",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = LoginResponse.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials"),
          @ApiResponse(responseCode = "400", description = "Bad request - malformed input"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
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
