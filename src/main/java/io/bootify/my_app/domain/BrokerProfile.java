package io.bootify.my_app.domain;

import io.bootify.my_app.dto.BrokerProfileDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BrokerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brokerProfileId;
    private String name;
    private String docNumber;
    private String fullAddress;
    private String city;

    @OneToOne(mappedBy = "brokerProfile")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_profile_id")
    private Set<Lease> leases;

    public BrokerProfile(BrokerProfileDto brokerProfileDto){
        this.brokerProfileId = brokerProfileDto.getBrokerProfileId();
        this.name = brokerProfileDto.getName();
        this.docNumber = brokerProfileDto.getDocNumber();
        this.fullAddress = brokerProfileDto.getFullAddress();
        this.city = brokerProfileDto.getCity();
    }
}
