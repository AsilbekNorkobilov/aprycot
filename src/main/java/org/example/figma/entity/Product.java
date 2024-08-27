package org.example.figma.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String name;
    private boolean isArchived;
    private Double price;
    private Integer calorie;

    @OneToOne
    private Attachment attachment;
    @ManyToOne
    private Category category;

}