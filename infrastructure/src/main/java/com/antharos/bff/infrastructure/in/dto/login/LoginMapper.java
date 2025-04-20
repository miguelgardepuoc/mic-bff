package com.antharos.bff.infrastructure.in.dto.login;

import com.antharos.bff.domain.login.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
  LoginResponse toLoginResponse(Login login);
}
