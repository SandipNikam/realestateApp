package io.bootify.my_app.domain;

import io.bootify.my_app.dto.PropertyOwnerDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class PropertyOwner {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer proprtyWonerId;

    @Column(length = 45)
    private String moNumber;

    @Column(length = 45)
    private String address;

    @Column(length = 45)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User userUser;

   /* @OneToMany(mappedBy = "proprtyWoner")
    private Set<Property> proprtyWoner;

    @OneToMany(mappedBy = "proprtyWoner")
    private Set<Lease> proprtyWonerLease;*/


    public PropertyOwner(PropertyOwnerDto dto) {
        this.proprtyWonerId = dto.getPropertyOwnerId();
        this.moNumber = dto.getMoNumber();
        this.address = dto.getAddress();
        this.status=dto.getStatus();
    }
}