package me.kycho.demo.events;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventEntityModel extends EntityModel<Event> {

    public EventEntityModel(Event event, Link... links) {
        super(event, Arrays.asList(links));
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}
