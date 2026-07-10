package com.fabio.gamememories.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_covers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameCover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String filePath;
    private String originalFileName;
    private Integer displayOrder;
    private String title;
}
