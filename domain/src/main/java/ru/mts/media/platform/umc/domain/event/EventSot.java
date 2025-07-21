package ru.mts.media.platform.umc.domain.event;

import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.util.Optional;
import java.util.Set;

public interface EventSot {

    Optional<Event> getEventById(Long id);

    Set<Event> getEvents();
}
