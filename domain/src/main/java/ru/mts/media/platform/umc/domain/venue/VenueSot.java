package ru.mts.media.platform.umc.domain.venue;

import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalIdInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.Optional;
import java.util.Set;

public interface VenueSot {
    Optional<Venue> getVenueByReferenceId(String id);

    Optional<Venue> getVenueById(FullExternalIdInput externalId);

    Set<Venue> getVenues();
}
