package org.apoorva.userauthservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)})
    private List<Role> roles = new ArrayList<>();
}
