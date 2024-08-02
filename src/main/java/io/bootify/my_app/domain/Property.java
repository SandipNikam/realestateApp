package io.bootify.my_app.domain;

import io.bootify.my_app.dto.PropertyDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Property {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer propertyId;

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String address;

    @Column(length = 45)
    private String type;

    @Column(length = 45)
    private String status;

    @Column(length = 45)
    private String area;

    @Column(length = 45)
    private String city;

    @Column(length = 45)
    private String comments;


    @Column(length = 45)
    private String flore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User userUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprty_woner_proprty_woner_id", nullable = false)
    private PropertyOwner proprtyWoner;

    @OneToMany(mappedBy = "propertyProperty")
    private Set<Lease> propertyLeases;

    @OneToMany(mappedBy = "propertyAgreement")
    private Set<Agreement> agreements;

    public Property(PropertyDto propertyDto){
        this.propertyId = propertyDto.getPropertyId();
        this.name = propertyDto.getName();
        this.address = propertyDto.getAddress();
        this.type = propertyDto.getType();
        this.status = propertyDto.getStatus();
        this.area = propertyDto.getArea();
        this.city = propertyDto.getCity();
        this.comments = propertyDto.getComments();
        this.flore = propertyDto.getFlore();
    }
}

