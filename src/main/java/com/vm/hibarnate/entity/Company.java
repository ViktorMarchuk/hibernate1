package com.vm.hibarnate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "company", schema = "hiber")
@EqualsAndHashCode(of = "name")
@ToString(exclude = "name")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
    @Builder.Default
    @OneToMany(mappedBy = "company",orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }
}