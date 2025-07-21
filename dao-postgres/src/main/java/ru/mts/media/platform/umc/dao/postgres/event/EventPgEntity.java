package ru.mts.media.platform.umc.dao.postgres.event;

import jakarta.persistence.*;
import lombok.Data;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "event")
@Data
public class EventPgEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "event_venue",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "branch_id"),
                    @JoinColumn(name = "provider_id"),
                    @JoinColumn(name = "external_id")
            }
    )
    private List<VenuePgEntity> venues;
}
