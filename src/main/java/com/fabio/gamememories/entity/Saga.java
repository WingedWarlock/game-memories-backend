package com.fabio.gamememories.entity;

import com.fabio.gamememories.enums.GalleryItemType;
import com.fabio.gamememories.enums.MusicChangePolicy;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sagas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Saga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "saga_game_names", joinColumns = @JoinColumn(name = "saga_id"))
    @Column(name = "game_saga_name")
    @Builder.Default
    private List<String> gameSagaNames = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private GalleryItemType heroImageType;

    private Long heroImageId;

    @Enumerated(EnumType.STRING)
    private GalleryItemType secondImageType;

    private Long secondImageId;

    private Long themeMusicId;

    @Enumerated(EnumType.STRING)
    private MusicChangePolicy musicChangePolicy;

    @OneToMany(mappedBy = "saga", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SagaGalleryItem> galleryItems = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
