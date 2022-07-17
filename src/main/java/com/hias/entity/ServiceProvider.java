package com.hias.entity;

import com.hias.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SERVICE_PROVIDER", schema = "HIAS")
@Getter
@Setter
public class ServiceProvider extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICE_PROVIDER_NO")
    private Long serviceProviderNo;

    @Column(name = "SERVICE_PROVIDER_ID")
    private String serviceProviderID;

    @Column(name = "SERVICE_PROVIDER_NAME")
    private String serviceProviderName;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}
