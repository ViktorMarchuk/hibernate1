package com.vm.hibarnate.entity;

import com.vm.hibarnate.converter.BirthdayConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Table(name = "users", schema = "hiber")
public class PersonalInfo {
    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birthday")
    private Birthday birthDay;

}
