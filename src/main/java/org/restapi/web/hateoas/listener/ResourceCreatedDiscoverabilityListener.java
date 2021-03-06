package org.restapi.web.hateoas.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.restapi.web.hateoas.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.base.Preconditions;

@Component
class ResourceCreatedDiscoverabilityListener implements ApplicationListener<ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(final ResourceCreatedEvent resourceCreatedEvent) {
        Preconditions.checkNotNull(resourceCreatedEvent);
        final HttpServletResponse response = resourceCreatedEvent.getResponse();
        final long idOfNewResource = resourceCreatedEvent.getIdOfNewResource();
        addLinkHeaderOnResourceCreation(response, idOfNewResource);
    }

    void addLinkHeaderOnResourceCreation(final HttpServletResponse response, final long idOfNewResource) {
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{idOfNewResource}").buildAndExpand(idOfNewResource).toUri();
        response.setHeader(HttpHeaders.LOCATION, uri.toASCIIString());
    }

}