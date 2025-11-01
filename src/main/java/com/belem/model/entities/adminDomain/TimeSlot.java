package com.belem.model.entities.adminDomain;

import com.belem.model.entities.enums.ClassShift;
import com.belem.model.entities.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_slots")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek; // (java.time.DayOfWeek - ex: MONDAY, TUESDAY)

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassShift shift; // Turno (MANHA, TARDE, NOITE)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityStatus status; // Pode ser modificado (1.3.1)

    @OneToMany(mappedBy = "timeSlot")
    private List<ScheduleAvailability> availabilities;
}