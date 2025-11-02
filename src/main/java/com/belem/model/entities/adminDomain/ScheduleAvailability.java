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
@Table(name = "schedule_availabilities",
        // Garante que o professor não possa se cadastrar duas vezes no mesmo horário/semestre
        uniqueConstraints = @UniqueConstraint(columnNames = {"professor_id", "time_slot_id", "semester"}))
public class ScheduleAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String semester; // ex: "2025.1"

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "time_slot_id", nullable = false)
    private TimeSlot timeSlot;
}
