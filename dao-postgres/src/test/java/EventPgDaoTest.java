import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgDao;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgEntity;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgMapper;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgRepository;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventPgDaoTest {

    @InjectMocks
    private EventPgDao dao;

    @Mock
    private EventPgRepository repository;

    @Mock
    private EventPgMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEventById_Success() {
        var pgEvent = mock(EventPgEntity.class);
        var mappedEvent = new Event();
        mappedEvent.setId(pgEvent.getId().toString());

        when(repository.findById(anyLong())).thenReturn(Optional.of(pgEvent));
        when(mapper.asModel(pgEvent)).thenReturn(mappedEvent);

        var result = dao.getEventById(1L);

        assertThat(result.isPresent());
        assertThat(Objects.equals(result.get().getId(), mappedEvent.getId()));
    }

    @Test
    public void testGetEventById_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        var result = dao.getEventById(1L);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void testGetEvents_Success() {
        Set<EventPgEntity> eventsList = Set.of(mock(EventPgEntity.class));
        when(repository.findAllFetchVenues())
                .thenReturn(eventsList);
        when(mapper.asModel(any())).thenReturn(new Event());

        var events = dao.getEvents();

        assertThat(events).isNotNull().isNotEmpty();
    }

    @Test
    public void testLoad_GetEventById_MultipleTimes() throws Exception {
        var pgEvent = mock(EventPgEntity.class);
        ReflectionTestUtils.setField(pgEvent, "id", 1L);
        var mappedEvent = new Event();
        mappedEvent.setId(pgEvent.getId().toString());

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(pgEvent));
        when(mapper.asModel(pgEvent))
                .thenReturn(mappedEvent);

        int iterations = 1000;
        AtomicReference<Duration> totalDuration = new AtomicReference<>(Duration.ZERO);

        IntStream.rangeClosed(1, iterations)
                .forEach(i -> {
                    long startTime = System.nanoTime();
                    dao.getEventById(1L);
                    long endTime = System.nanoTime();
                    totalDuration.set(Duration.ZERO.plusNanos(endTime - startTime));
                });

        double averageTimeMillis = TimeUnit.NANOSECONDS.toMillis(totalDuration.get().toNanos()) / iterations;
        System.out.println("Среднее время выполнения: " + averageTimeMillis + " миллисекунд.");

        assertThat(averageTimeMillis).isLessThanOrEqualTo(10.0);
    }
}