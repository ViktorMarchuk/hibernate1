package com.vm.hibarnate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "profile", schema = "hiber")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "id")
    private User user;

    private String street;

    private String language;

    public void setUser(User user) {
        this.user = user;
//        user.setProfile(this);
    }
}
