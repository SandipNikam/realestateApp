package io.bootify.my_app.domain;

import io.bootify.my_app.dto.LeaseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lease {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaseId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(length = 45)
    private String status;

    @Column(length = 45)
    private String ownerId;

    @Column(length = 45)
    private String renterId;

    @Column(length = 45)
    private String amount;

    @Column(length = 45)
    private String rent;

    @Column(length = 45)
    private String deposit;

    @Column(length = 45)
    private String propertyBookingcol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_property_id", nullable = false)
    private Property propertyProperty;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_person_rent_person_id", nullable = false)
    private RentPerson rentPerson;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User userUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprty_woner_proprty_woner_id", nullable = false)
    private PropertyOwner proprtyWoner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_profile_id", nullable = false)
    private BrokerProfile brokerProfiles;

    public Lease(LeaseDto leaseDto)
    {
        this.leaseId = leaseDto.getLeaseId();
        this.startDate = leaseDto.getStartDate();
        this.endDate = leaseDto.getEndDate();
        this.status = leaseDto.getStatus();
        this.ownerId = leaseDto.getOwnerId();
        this.renterId = leaseDto.getRenterId();
        this.amount = leaseDto.getAmount();
        this.rent = leaseDto.getRent();
        this.deposit = leaseDto.getDeposit();
        this.propertyBookingcol = leaseDto.getPropertyBookingcol();
    }

}
