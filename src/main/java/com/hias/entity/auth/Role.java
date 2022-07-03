package com.hias.entity.auth;

import com.hias.entity.base.SoftDeleteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ROLE", schema = "HIAS")
public class Role extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_NO")
    private Long roleNo;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<UserRole> userRoleList;
}
