package org.apoorva.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.apoorva.userauthservice.models.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private List<Role> roles = new ArrayList<Role>();

}
