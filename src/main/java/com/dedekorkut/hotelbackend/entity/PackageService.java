package com.dedekorkut.hotelbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "package_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package aPackage;
}
