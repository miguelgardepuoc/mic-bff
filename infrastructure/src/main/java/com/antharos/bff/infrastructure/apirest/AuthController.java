package com.antharos.bff.infrastructure.apirest;

import com.antharos.bff.application.LoginCommand;
import com.antharos.bff.application.LoginCommandHandler;
import com.antharos.bff.application.SignUpCommand;
import com.antharos.bff.application.SignUpCommandHandler;
import com.antharos.bff.domain.login.Login;
import com.antharos.bff.infrastructure.apirest.presentationmodel.LoginMapper;
import com.antharos.bff.infrastructure.apirest.presentationmodel.LoginRequest;
import com.antharos.bff.infrastructure.apirest.presentationmodel.LoginResponse;
import com.antharos.bff.infrastructure.apirest.presentationmodel.RegisterUserRequest;
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
  private final LoginMapper loginMapper;

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
    final Login login = this.loginCommandHandler.handle(command);
    return ResponseEntity.ok(this.loginMapper.toLoginResponse(login));
  }
}
