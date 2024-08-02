package io.bootify.my_app.dto;

import io.bootify.my_app.domain.Lease;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaseDto {

    private Integer leaseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String ownerId;
    private String renterId;
    private String amount;
    private String rent;
    private String deposit;
    private String propertyBookingcol;
    private Integer propertyId;
    private Integer userId;
    private Integer propertyOwnerId;
    private Integer brokerProfileId;

    public LeaseDto(Lease lease) {
        this.leaseId = lease.getLeaseId();
        this.startDate = lease.getStartDate();
        this.endDate = lease.getEndDate();
        this.status = lease.getStatus();
        this.ownerId = lease.getOwnerId();
        this.renterId = lease.getRenterId();
        this.amount = lease.getAmount();
        this.rent = lease.getRent();
        this.deposit = lease.getDeposit();
        this.propertyBookingcol = lease.getPropertyBookingcol();

        // Set additional properties
        if (lease.getPropertyProperty() != null) {
            this.propertyId = lease.getPropertyProperty().getPropertyId();
        }
        if (lease.getUserUser() != null) {
            this.userId = lease.getUserUser().getUserId();
        }
        if (lease.getProprtyWoner() != null) {
            this.propertyOwnerId = lease.getProprtyWoner().getProprtyWonerId();
        }
        if (lease.getBrokerProfiles() != null) {
            this.brokerProfileId = lease.getBrokerProfiles().getBrokerProfileId();
        }
    }
}
