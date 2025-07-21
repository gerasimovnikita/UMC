package ru.mts.media.platform.umc.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.SaveEventInput;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class EventDomainService {
    private final ApplicationEventPublisher eventPublisher;
    private final EventSot eventSot;
    private final VenueSot venueSot;
    private final EventDomainServiceMapper mapper;

    public EventSave save(Long id, SaveEventInput input) {
        var eventSave = eventSot.getEventById(id)
                .map(applyPatch(input))
                .map(evt -> this.enrichWithVenues(evt, input.getVenueReferenceId()))
                .orElseGet(() -> {
                    Event evt = mapper.toEvent(input);
                    return this.enrichWithVenues(evt, input.getVenueReferenceId());
                });

        eventPublisher.publishEvent(eventSave);

        return eventSave;
    }

    private EventSave enrichWithVenues(Event event, String venueRefId) {
        venueSot.getVenueByReferenceId(venueRefId)
                .ifPresent(venue -> {
                    event.setVenues(List.of(venue));
                });
        return new EventSave(event);
    }

    private Function<Event, Event> applyPatch(SaveEventInput input) {
        return e -> mapper.patch(e, input);
    }
}
