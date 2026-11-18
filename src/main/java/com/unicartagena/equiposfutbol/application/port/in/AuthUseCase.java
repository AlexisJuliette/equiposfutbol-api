package com.unicartagena.equiposfutbol.application.port.in;

import com.unicartagena.equiposfutbol.application.service.dto.commands.RecoverPasswordCommand;
import com.unicartagena.equiposfutbol.application.service.dto.queries.LoginQuery;
import com.unicartagena.equiposfutbol.application.service.dto.response.UserResult;

public interface AuthUseCase {
    UserResult login(LoginQuery query);

    void recuperarPassword(RecoverPasswordCommand command);    
}
