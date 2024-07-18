package org.example.figma.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String district;
    private String street;
    private String house;
    private String apartment;
    private Double longitude;
    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}