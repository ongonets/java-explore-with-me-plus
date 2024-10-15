package ru.practicum.ewm.events.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    long confirmedRequests;

    @Column(name = "created")
    LocalDateTime createdOn;

    String description;

    @Column(name = "event_date")
    LocalDateTime eventDate;

    float latitude;

    float longitude;

    long participantLimit;

    @Column(name = "published")
    LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    boolean requestModeration;

    @Column(name = "state")
    @Enumerated(value = EnumType.ORDINAL)
    EventState state;

    boolean paid;

    String title;
}

