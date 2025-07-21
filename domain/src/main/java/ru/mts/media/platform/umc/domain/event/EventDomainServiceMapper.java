package ru.mts.media.platform.umc.domain.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.SaveEventInput;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EventDomainServiceMapper {

    Event patch(@MappingTarget Event targetEvent, SaveEventInput input);

    @Mapping(target = "id", ignore = true)
    Event toEvent(SaveEventInput input);
}
