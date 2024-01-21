package com.vm.hibarnate.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@NamedQuery(name = "findByCompany", query = """
        select u from User u 
        left join u.company c
        where c.name = :name
        """)

@NamedQuery(name = "update", query = """
        update User u set  personalInfo.firstName=:newFirstName
        where u.personalInfo.firstName =:oldName
        """)

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile", "userChats", "payments"})
@Entity
@Table(name = "users", schema = "hiber")
@Builder
@EntityScan()
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String userName;
    @Embedded
    private PersonalInfo personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

}
