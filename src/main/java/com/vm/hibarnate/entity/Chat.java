package com.vm.hibarnate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chat", schema = "hiber")
public class Chat implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "chat")
    @Builder.Default
    private List<UserChat> users = new ArrayList<>();

}
