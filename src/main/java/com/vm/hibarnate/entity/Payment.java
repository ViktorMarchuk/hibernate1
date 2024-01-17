package com.vm.hibarnate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment",schema = "hiber")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "amount")
    private int amount;

//    @Column(insertable=false, updatable=false)
//    private int receiverId;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id")
    private User user;
}
