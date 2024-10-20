package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.request.dto.RequestCountDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;

import java.util.List;
import java.util.stream.Stream;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByEvent(Event event);

    @Query("update Request r set r.status = :status where r.id in :ids")
    void updateStatus(@Param("status") RequestStatus status, @Param("ids") List<Long> requestIds);


    @Query("SELECT new ru.practicum.ewm.request.dto.RequestCountDto(r.event.id, count(r.id)) " +
            "FROM Request r WHERE r.event IN :events " +
            "AND r.status = ru.practicum.ewm.request.model.RequestStatus.CONFIRMED " +
            "GROUP BY r.event.id")
    Stream<RequestCountDto> findConfirmedRequest(@Param("events") List<Event> events);
}
