package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.event.EventSot;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.List;
import java.util.Set;

@DgsComponent
@RequiredArgsConstructor
public class EventDgsQuery {

    private final EventSot sot;

    @DgsQuery
    public Set<Event> getEvents(){
        return sot.getEvents();
    }

    @DgsData(parentType = "Event", field = "venues")
    public List<Venue> resolveVenues(DgsDataFetchingEnvironment dfe) {
        Event event = dfe.getSource();
        return event.getVenues();
    }
}
