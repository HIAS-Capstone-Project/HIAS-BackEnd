package com.hias.entity.sso;

import com.hias.entity.SoftDeleteEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLE", uniqueConstraints = @UniqueConstraint(columnNames = {
        "USER_NO", "ROLE_NO"
}), schema = "HIAS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ROLE_NO")
    private Long userRoleNo;

    @Column(name = "USER_NO", insertable = false, updatable = false)
    private Long userNo;

    @ManyToOne
    @JoinColumn(name = "USER_NO", nullable = false)
    private User user;

    @Column(name = "ROLE_NO", insertable = false, updatable = false)
    private Long roleNo;

    @ManyToOne
    @JoinColumn(name = "ROLE_NO", nullable = false)
    private Role role;
}
