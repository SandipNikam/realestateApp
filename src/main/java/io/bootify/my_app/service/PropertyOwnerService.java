package io.bootify.my_app.service;

import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.dto.PropertyOwnerDto;
import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.PageNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserNotFound;

import java.util.List;
import java.util.Optional;

public interface PropertyOwnerService {
    public List<PropertyOwnerDto> getAllPropertyOwners(int pageNo, int pageSize) throws UserNotFound, PageNotFoundException, ResourceNotFoundException;
    Optional<PropertyOwner> getPropertyOwnerById(Integer id);
    PropertyOwner createPropertyOwner(PropertyOwnerDto propertyOwnerDto);
    PropertyOwner updatePropertyOwner(Integer id, PropertyOwnerDto propertyOwnerDto);
    void deletePropertyOwner(Integer id);
}
