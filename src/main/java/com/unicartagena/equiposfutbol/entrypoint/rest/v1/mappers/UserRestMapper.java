
package com.unicartagena.equiposfutbol.entrypoint.rest.v1.mappers;

import com.unicartagena.equiposfutbol.application.service.dto.commands.CreateUserCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.RecoverPasswordCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.UpdateUserCommand;
import com.unicartagena.equiposfutbol.application.service.dto.queries.LoginQuery;
import com.unicartagena.equiposfutbol.application.service.dto.response.UserResult;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import com.unicartagena.equiposfutbol.domain.valueobjects.RawPassword;
import com.unicartagena.equiposfutbol.domain.valueobjects.TextoCorto;
import com.unicartagena.equiposfutbol.domain.valueobjects.Username;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.CreateUserRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.LoginRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.RecoverPasswordRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.request.UpdateUserRequest;
import com.unicartagena.equiposfutbol.entrypoint.rest.v1.dto.response.UserResponse;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserRestMapper {

    public CreateUserCommand toCommand(CreateUserRequest request) {
        return new CreateUserCommand(
                new Username(request.username()),
                new RawPassword(request.password()),
                new TextoCorto(request.nombre()),
                new EmailAddress(request.email()));
    }

    public UpdateUserCommand toCommand(UpdateUserRequest request) {
        return new UpdateUserCommand(
                new TextoCorto(request.nombre()),
                new EmailAddress(request.email()));
    }

    public LoginQuery toLoginQuery(LoginRequest request) {
        return new LoginQuery(
                new Username(request.username()),
                new RawPassword(request.password()));
    }

    public RecoverPasswordCommand toRecoverCommand(RecoverPasswordRequest request) {
        return new RecoverPasswordCommand(new EmailAddress(request.email()));
    }

    public UserResponse toResponse(UserResult result) {
        return new UserResponse(
                result.id(),
                result.username(),
                result.nombre(),
                result.email(),
                result.createdAt(),
                result.updatedAt());
    }

    public List<UserResponse> toResponses(List<UserResult> results) {
        return results.stream().map(this::toResponse).toList();
    }
}