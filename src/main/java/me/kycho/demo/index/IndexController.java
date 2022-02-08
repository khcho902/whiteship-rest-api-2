package me.kycho.demo.index;

import me.kycho.demo.events.EventController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {

    //https://docs.spring.io/spring-hateoas/docs/current/reference/html/#migrate-to-1.0.changes.representation-models
    @GetMapping("/api")
    public RepresentationModel index() {
        RepresentationModel index = new RepresentationModel();
        index.add(linkTo(EventController.class).withRel("events"));
        return index;
    }
}
