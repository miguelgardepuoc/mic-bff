package com.antharos.bff.infrastructure.apirest.presentationmodel;

import com.antharos.bff.domain.login.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
  LoginResponse toLoginResponse(Login login);
}
