package io.bootify.my_app.domain;

import io.bootify.my_app.dto.AgreementDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer agreementId;

    @Column(length = 45)
    private Double duration;

    @Column(length = 45)
    private String type;

    @Column(length = 45)
    private Double rate;

    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private LocalDate notificationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_property_id", nullable = false)
    private Property propertyAgreement;


    public Agreement(AgreementDto agreementDto) {
        this.agreementId = agreementDto.getAgreementId();
        this.duration = agreementDto.getDuration();
        this.type = agreementDto.getType();
        this.rate = agreementDto.getRate();
        this.startDate=agreementDto.getStartDate();
        this.endDate=agreementDto.getEndDate();
        this.notificationDate=agreementDto.getNotificationDate();
    }


}
