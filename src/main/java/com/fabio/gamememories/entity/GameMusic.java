package com.fabio.gamememories.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_music")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String filePath;
    private String originalFileName;
    private String title;
    private String artist;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
