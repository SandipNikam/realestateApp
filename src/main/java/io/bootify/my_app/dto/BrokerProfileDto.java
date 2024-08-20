package io.bootify.my_app.dto;


import io.bootify.my_app.domain.BrokerProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BrokerProfileDto {
    private Integer brokerProfileId;
    private String name;
    private String docNumber;
    private String fullAddress;
    private String city;

    public BrokerProfileDto(BrokerProfile brokerProfile){
        this.brokerProfileId = brokerProfile.getBrokerProfileId();
        this.name = brokerProfile.getName();
        this.docNumber = brokerProfile.getDocNumber();
        this.fullAddress = brokerProfile.getFullAddress();
        this.city = brokerProfile.getCity();
    }
}
