package ru.mts.media.platform.umc.dao.postgres.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgMapper;
import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        uses = VenuePgMapper.class)
public interface EventPgMapper {

    @Mapping(target = "startTime", source = "startTime", qualifiedByName = "convertDateTimeToString")
    @Mapping(target = "endTime", source = "endTime", qualifiedByName = "convertDateTimeToString")
    Event asModel(EventPgEntity entity);

    @Mapping(target = "startTime", source = "startTime", qualifiedByName = "convertStringToDateTime")
    @Mapping(target = "endTime", source = "endTime", qualifiedByName = "convertStringToDateTime")
    EventPgEntity asEntity(Event event);

    @Named("convertDateTimeToString")
    default String convertDateTimeToString(LocalDateTime dateTime) {
        if(dateTime == null){
            return null;
        }
        return dateTime.toString();
    }

    @Named("convertStringToDateTime")
    default LocalDateTime convertStringToDateTime(String stringDateTime) {
        try {
            return LocalDateTime.parse(stringDateTime);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
