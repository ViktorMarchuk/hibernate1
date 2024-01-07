package com.vm.hibarnate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_chat",schema = "hiber")
public class UserChat extends AuditableEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Chat chat;

//    private Instant createdAt;
//    private String createdBy;

    public void setUser(User user) {
        this.user = user;
        user.getUserChats().add(this);
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.getUsers().add(this);
    }
}
