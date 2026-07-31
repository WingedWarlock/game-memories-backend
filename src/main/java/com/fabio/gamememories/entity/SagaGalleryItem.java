package com.fabio.gamememories.entity;

import com.fabio.gamememories.enums.GalleryItemType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "saga_gallery_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagaGalleryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "saga_id", nullable = false)
    private Saga saga;

    @Enumerated(EnumType.STRING)
    private GalleryItemType itemType;

    private Long itemId;
}
