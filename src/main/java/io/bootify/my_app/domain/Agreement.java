package io.bootify.my_app.domain;

import io.bootify.my_app.dto.AgreementDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer agreementId;

    @Column(length = 45)
    private Double duration;

    @Column(length = 45)
    private String type;

    @Column(length = 45)
    private Double rate;

    @OneToMany(mappedBy = "brokerAgreement")
    private Set<BrokerProfile> brokers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property propertyAgreement;

    public Agreement(AgreementDto agreementDto)
    {
        this.agreementId = agreementDto.getAgreementId();
        this.duration = agreementDto.getDuration();
        this.type = agreementDto.getType();
        this.rate = agreementDto.getRate();
    }

}
