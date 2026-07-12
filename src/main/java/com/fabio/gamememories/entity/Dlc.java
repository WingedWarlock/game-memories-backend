package com.fabio.gamememories.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dlcs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dlc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean completed;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
