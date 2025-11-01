package com.belem.model.entities.user;

import com.belem.model.entities.adminDomain.Allocation;
import com.belem.model.entities.adminDomain.DisciplineInterest;
import com.belem.model.entities.adminDomain.ScheduleAvailability;
import com.belem.model.entities.adminDomain.School;
import com.belem.model.entities.enums.EntityStatus;
import com.belem.model.entities.teacherContext.AcademicFormation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professors")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityStatus status;

    // Link para a conta de segurança (autenticação)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userAccount;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcademicFormation> formations;

    @OneToMany(mappedBy = "professor")
    private List<ScheduleAvailability> availabilities;

    @OneToMany(mappedBy = "professor")
    private List<DisciplineInterest> interests;

    @OneToMany(mappedBy = "professor")
    private List<Allocation> allocations;
}
