package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Request;
import ru.practicum.ewm.event.model.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByEvent(Event event);

    @Query("update Request r set r.status = :status where r.id in :ids")
    void updateStatus(@Param("status") RequestStatus status, @Param("ids") List<Long> requestIds);
}
