package com.fabio.gamememories.entity;

import com.fabio.gamememories.enums.RunStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "runs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String runName;
    private String difficulty;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double completionPercentage;

    @Enumerated(EnumType.STRING)
    private RunStatus status;

    private Boolean favoriteRun;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SavePoint> savePoints = new ArrayList<>();
}
