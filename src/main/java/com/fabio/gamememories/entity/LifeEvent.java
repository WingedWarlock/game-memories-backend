package com.fabio.gamememories.entity;

import com.fabio.gamememories.enums.LifeEventCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "life_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LifeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private LifeEventCategory category;
}
