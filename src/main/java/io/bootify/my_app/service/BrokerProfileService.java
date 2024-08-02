package io.bootify.my_app.service;



import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;

import java.util.List;

public interface BrokerProfileService {
    void addBrokerProfile(BrokerProfileDto brokerProfileDto);

    void updateBrokerProfile(BrokerProfileDto brokerProfileDto, Integer brokerProfileId);

    List<BrokerProfileDto> getAllBrokerProfiles();

    BrokerProfileDto getBrokerProfileById(Integer brokerProfileId) throws BrokerProfileNotFoundException;

    void deleteBrokerProfileById(Integer brokerProfileId) throws BrokerProfileNotFoundException;

    List<BrokerProfileDto> getBrokerByUserId(Integer userId) throws BrokerProfileNotFoundException;
}
