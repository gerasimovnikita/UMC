package ru.mts.media.platform.umc.api.gql.venue;

import com.netflix.graphql.dgs.*;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@DgsComponent
@RequiredArgsConstructor
public class VenueDgsQuery {
    private final VenueSot sot;

    @DgsQuery
    public Venue venueByReferenceId(@InputArgument String id) {
        return Optional.of(id).flatMap(sot::getVenueByReferenceId).orElse(null);
    }

    @DgsQuery
    public Set<Venue> getVenues(){
        return sot.getVenues();
    }

    @DgsData(parentType = "Venue", field = "events")
    public List<Event> resolveEvents(DgsDataFetchingEnvironment dfe) {
        Venue venue = dfe.getSource();
        return venue.getEvents();
    }
}
