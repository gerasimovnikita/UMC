package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.SaveEventInput;
import ru.mts.media.platform.umc.domain.event.EventDomainService;

@DgsComponent
@RequiredArgsConstructor
public class EventDgsMutation {

    private final EventDomainService domainService;

    @DgsMutation
    public Event saveEvent(Long id, SaveEventInput input){
        return domainService.save(id, input).getEntity();
    }
}
