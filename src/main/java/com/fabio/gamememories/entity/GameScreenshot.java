package com.fabio.gamememories.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_screenshots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameScreenshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String filePath;
    private String originalFileName;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
}
