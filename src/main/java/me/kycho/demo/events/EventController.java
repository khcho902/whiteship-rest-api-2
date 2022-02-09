package me.kycho.demo.events;

import lombok.RequiredArgsConstructor;
import me.kycho.demo.commons.ErrorsEntityModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid  EventDto eventDto, Errors errors) {

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        Event newEvent = this.eventRepository.save(event);


        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();

        EventEntityModel eventEntityModel = new EventEntityModel(newEvent);
        eventEntityModel.add(linkTo(EventController.class).withRel("query-events"));
        eventEntityModel.add(selfLinkBuilder.withRel("update-event"));
        eventEntityModel.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.created(createdUri).body(eventEntityModel);
    }

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler) {
        Page<Event> page = this.eventRepository.findAll(pageable);
        PagedModel<EntityModel<Event>> pagedEntityModel = assembler.toModel(page, EventEntityModel::new);
        pagedEntityModel.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
        return ResponseEntity.ok(pagedEntityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Event event = optionalEvent.get();
        EventEntityModel eventEntityModel = new EventEntityModel(event);
        eventEntityModel.add(Link.of("/docs/index.html#resources-events-get").withRel("profile"));
        return ResponseEntity.ok(eventEntityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id,
                                      @RequestBody @Valid EventDto eventDto,
                                      Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        this.eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Event existingEvent = optionalEvent.get();
        this.modelMapper.map(eventDto, existingEvent);
        Event savedEvent = this.eventRepository.save(existingEvent);

        EventEntityModel eventEntityModel = new EventEntityModel(savedEvent);
        eventEntityModel.add(Link.of("/docs/index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok(eventEntityModel);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsEntityModel(errors));
    }

}
