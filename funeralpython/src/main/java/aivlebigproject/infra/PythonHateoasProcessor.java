package aivlebigproject.infra;

import aivlebigproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class PythonHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Python>> {

    @Override
    public EntityModel<Python> process(EntityModel<Python> model) {
        return model;
    }
}
