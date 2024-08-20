package io.bootify.my_app.dto;

import io.bootify.my_app.domain.Agreement;

import java.time.LocalDate;

public class AgreementNotificationDto extends Agreement {
    private String userEmail;
    private String propertyName;

    public AgreementNotificationDto(String userEmail, String propertyName) {
        this.userEmail = userEmail;
        this.propertyName = propertyName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPropertyName() {
        return propertyName;
    }

}
