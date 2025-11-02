package com.belem.model.entities.adminDomain;

import com.belem.model.entities.user.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discipline_interests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"professor_id", "discipline_id", "semester"}))
public class DisciplineInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private Integer priority; // 1 = Baixo interesse, 2 = Alto interesse (Req 4.1)

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;
}
