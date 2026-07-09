package com.fabio.gamememories.entity;

import com.fabio.gamememories.enums.MemoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "game_memories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameMemory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate memoryDate;

    @Enumerated(EnumType.STRING)
    private MemoryType type;
}
