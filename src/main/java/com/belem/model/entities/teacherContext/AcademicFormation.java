package com.belem.model.entities.teacherContext;

import com.belem.model.entities.teacherContext.enums.DegreeCategory;
import com.belem.model.entities.user.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "academic_formations")
public class AcademicFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DegreeCategory degreeCategory;

    @Column(nullable = false)
    private String institutionName;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private Integer conclusionYear;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
}
