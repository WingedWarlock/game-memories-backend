package com.fabio.gamememories.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "save_points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id", nullable = false)
    private Run run;

    private String slot;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
}
