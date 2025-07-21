package ru.mts.media.platform.umc.dao.postgres.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface EventPgRepository extends JpaRepository<EventPgEntity, Long> {

    @Query("""
            SELECT e FROM EventPgEntity e
            LEFT JOIN FETCH e.venues
            """)
    Set<EventPgEntity> findAllFetchVenues();
}
