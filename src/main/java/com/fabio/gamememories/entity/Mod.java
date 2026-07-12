package com.fabio.gamememories.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String link;

    private Boolean active;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
