package com.vm.hibarnate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "hiber")
public class User {
    @Id
    private String userName;
    private String firstName;
    private String lastName;
    @Column(name = "birthdate")
    private LocalDate birthDay;
    private Integer age;
}
