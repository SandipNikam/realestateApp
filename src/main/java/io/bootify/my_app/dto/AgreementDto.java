package io.bootify.my_app.dto;

import io.bootify.my_app.domain.Agreement;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgreementDto {
    private Integer agreementId;
    private Double duration;
    private String type;
    private Double rate;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate notificationDate;
    private Integer userId;
    private Integer propertyId;


public AgreementDto(Agreement agreement){
    this.agreementId=agreement.getAgreementId();
    this.duration= agreement.getDuration();;
    this.type= agreement.getType();;
    this.rate= agreement.getRate();;
    this.startDate=agreement.getStartDate();
    this.endDate=agreement.getEndDate();
    this.notificationDate=agreement.getNotificationDate();

}

}
