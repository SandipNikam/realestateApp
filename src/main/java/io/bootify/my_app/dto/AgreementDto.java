package io.bootify.my_app.dto;

import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.Property;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AgreementDto {

    private Integer agreementId;


    private Double duration;


    private String type;


    private Double rate;

    private Integer propertyId;

    public AgreementDto(Agreement agreement)
    {
        this.agreementId = agreement.getAgreementId();
        this.duration = agreement.getDuration();
        this.type = agreement.getType();
        this.rate = agreement.getRate();
    }

}
