package com.unicartagena.equiposfutbol.domain.ports.out;

import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;

public interface NotificationPort {
    void enviarPasswordTemporal(EmailAddress email, String username, String passwordTemporal);    
}
