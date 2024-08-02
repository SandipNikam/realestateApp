//package io.bootify.my_app.domain;
//
//import io.bootify.my_app.dto.RentPersonDto;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class RentPerson {
//
//    @Id
//    @Column(nullable = false, updatable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer rentPersonId;
//
//    @Column(length = 45)
//    private String name;
//
//    @Column(length = 45)
//    private String address;
//
//    @Column(length = 45)
//    private String moNumber;
//
//    @Column(length = 45)
//    private String rentPersoncol;
//
//    @Column(length = 45)
//    private String status;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_user_id", nullable = false)
//    private User userUser;
//
//    /*@OneToMany(mappedBy = "rentPerson")
//    private Set<Lease> rentPersonLease;*/
//
//    public RentPerson(RentPersonDto rentPersonDto) {
//        this.rentPersonId = rentPersonDto.getRentPersonId();
//        this.name = rentPersonDto.getName();
//        this.address = rentPersonDto.getAddress();
//        this.moNumber = rentPersonDto.getMoNumber();
//        this.rentPersoncol = rentPersonDto.getRentPersoncol();
//        this.status=rentPersonDto.getStatus();
//    }
//}
