package ru.practicum.ewm.event;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.ewm.StatClientImpl;
import ru.practicum.ewm.category.mapper.CategoryMapperImpl;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.mapper.EventMapperImpl;
import ru.practicum.ewm.event.mapper.RequestMapperImpl;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.model.Request;
import ru.practicum.ewm.event.model.RequestStatus;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.event.service.EventServiceImpl;
import ru.practicum.ewm.user.mapper.UserMapperImpl;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({EventServiceImpl.class, EventMapperImpl.class, RequestMapperImpl.class,
        UserMapperImpl.class, CategoryMapperImpl.class, StatClientImpl.class})
public class EventServiceImplTest {

    private final EntityManager em;
    private final EventService eventService;
    private final EventMapper eventMapper;

    private final NewEventDto newEvent = new NewEventDto(
            "annotation",
            1,
            "description",
            LocalDateTime.now().plusDays(2),
            new Location(34.56, 25.98),
            true,
            5,
            true,
            "title"
    );

    private User getUser() {
        User user = new User("test@mail.ru", null, "name");
        em.persist(user);
        return user;
    }

    private Category getCategory() {
        Category category = new Category(null, "name");
        em.persist(category);
        return category;
    }

    private Request getRequest(Event event) {
        User user = new User("test@yandex.ru", null, "name");
        em.persist(user);
        Request request = new Request(0, event,user, RequestStatus.CONFIRMED, LocalDateTime.now());
        em.persist(request);
        return request;
    }

    private Event getEvent() {
        User user = getUser();
        Category category = getCategory();
        Event event = eventMapper.map(newEvent);
        event.setInitiator(user);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        em.persist(event);
        getRequest(event);
        return event;
    }

    @Test
    void createEvent() {
        // given
        User user = getUser();
        Category category = getCategory();
        newEvent.setCategory(category.getId());

        // when
        eventService.create(user.getId(), newEvent);

        // then
        TypedQuery<Event> query = em.createQuery("Select e from Event e where e.title = :title", Event.class);
        Event targetEvent = query.setParameter("title", newEvent.getTitle()).getSingleResult();

        assertThat(targetEvent, is(allOf(
                hasProperty("id", notNullValue()),
                hasProperty("title", equalTo(newEvent.getTitle())),
                hasProperty("annotation", equalTo(newEvent.getAnnotation())),
                hasProperty("description", equalTo(newEvent.getDescription()))
        )));
    }

    @Test
    void findBy() {
        // given
        Event sourceEvent = getEvent();
        ParamEventDto paramEventDto = new ParamEventDto(sourceEvent.getInitiator().getId(), sourceEvent.getId());

        // when
        EventFullDto targetEvent = eventService.findBy(paramEventDto);

        // then

        assertThat(targetEvent, is(allOf(
                hasProperty("id", notNullValue()),
                hasProperty("title", equalTo(sourceEvent.getTitle())),
                hasProperty("annotation", equalTo(sourceEvent.getAnnotation())),
                hasProperty("description", equalTo(sourceEvent.getDescription()))
        )));
    }

    @Test
    void findBy_withSearchParam() {
        // given
        Event sourceEvent = getEvent();

        // when
         Collection<EventShortDto> targetEvents = eventService
                 .findBy(sourceEvent.getInitiator().getId(), new SearchEventDto(0L,10L));

        // then

        assertThat(targetEvents, hasItem(allOf(
                hasProperty("id", notNullValue()),
                hasProperty("title", equalTo(sourceEvent.getTitle())),
                hasProperty("annotation", equalTo(sourceEvent.getAnnotation()))
        )));
    }


}
