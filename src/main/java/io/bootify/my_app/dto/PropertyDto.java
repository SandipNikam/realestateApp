package io.bootify.my_app.dto;

import io.bootify.my_app.domain.Property;
import io.bootify.my_app.domain.Lease;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {

    private Integer propertyId;
    private String name;
    private String address;
    private String type;
    private String status;
    private String area;
    private String city;
    private String comments;
    private String flore;
    private Integer userId;
    private Integer propertyOwnerId;
    private Set<Integer> propertyLeases;

    public PropertyDto(Property property){
        this.propertyId = property.getPropertyId();
        this.name = property.getName();
        this.address = property.getAddress();
        this.type = property.getType();
        this.status = property.getStatus();
        this.area = property.getArea();
        this.city = property.getCity();
        this.comments = property.getComments();
        this.flore = property.getFlore();

        if (property.getUserUser() != null) {
            this.userId = property.getUserUser().getUserId();
        }
        if (property.getProprtyWoner() != null) {
            this.propertyOwnerId = property.getProprtyWoner().getProprtyWonerId();
        }
        if (property.getPropertyLeases() != null) {
            this.propertyLeases = property.getPropertyLeases().stream()
                    .map(Lease::getLeaseId)
                    .collect(Collectors.toSet());
        }
    }
}
