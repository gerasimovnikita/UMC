package ru.mts.media.platform.umc.dao.postgres.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.event.EventSave;
import ru.mts.media.platform.umc.domain.event.EventSot;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class EventPgDao implements EventSot {
    private final EventPgRepository repository;
    private final EventPgMapper mapper;


    @Override
    public Optional<Event> getEventById(Long id) {
        return repository.findById(id)
                .map(mapper::asModel);
    }

    @Override
    public Set<Event> getEvents() {
        return repository.findAllFetchVenues()
                .stream()
                .map(mapper::asModel)
                .collect(toSet());
    }

    @EventListener
    public void handleEventCreatedEvent(EventSave evt) {
        evt.unwrap()
                .map(mapper::asEntity)
                .ifPresent(repository::save);
    }
}
