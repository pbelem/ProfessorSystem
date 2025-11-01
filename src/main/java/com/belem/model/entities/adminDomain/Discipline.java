package com.belem.model.entities.adminDomain;

import com.belem.model.entities.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disciplines")
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String acronym; // Sigla

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer workload; // Carga Horária

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityStatus status;

    @OneToMany(mappedBy = "discipline")
    private List<DisciplineInterest> interests;

    @OneToMany(mappedBy = "discipline")
    private List<Allocation> allocations;
}