package rest;

import domain.Format;
import domain.GameEvent;
import domain.LGS;
import domain.User;
import enums.Role;
import service.FormatService;
import service.GameEventService;
import service.LGSService;
import service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;

/**
 * Created by Dennis van Opstal on 12-1-2018.
 */
@Path("/event")
public class EventController {
    @Inject
    private GameEventService gameEventService;
    @Inject
    private FormatService formatService;
    @Inject
    private LGSService lgsService;
    @Inject
    private UserService userService;
    private JsonResponseObject json;

    @Path("")
    @POST
    @Secured({Role.ADMIN, Role.LGS})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLGS(@Context SecurityContext securityContext, @FormParam("formatId") long formatId, @FormParam("lgsId") long lgsId,
                              @FormParam("name") String name, @FormParam("description") String description, @FormParam("date") String date,
                              @FormParam("startTime") String startTime, @FormParam("endTime") String endTime, @FormParam("price") double price) {
        Principal principal = securityContext.getUserPrincipal();
        String email = principal.getName();
        User user = userService.find(email);
        if (user.getRole().equals(Role.ADMIN) || (user.getRole().equals(Role.LGS) && user.getOwnedLGS().getId() == lgsId)) {
            Format format = formatService.find(formatId);
            LGS lgs = lgsService.find(lgsId);
            if (format == null || lgs == null) {
                json = new JsonResponseObject(true, "LGS and/or Format with the given id doesn't exist.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            } else {
                GameEvent gameEvent = new GameEvent(name, description, date, startTime, endTime, price, format, lgs);
                gameEventService.create(gameEvent);
                json = new JsonResponseObject(false, "Successfully created an Event");
                return Response.status(Response.Status.OK).entity(json).build();
            }
        } else {
            json = new JsonResponseObject(true, "LGS Owners can only create events at their own LGS");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }

    @Path("")
    @GET
    @Secured({Role.NORMAL_USER, Role.LGS, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents(@QueryParam("id") long id) {
        if (id > 0) {
            GameEvent event = gameEventService.find(id);
            if (event != null) {
                json = new JsonResponseObject(false, event);
                return Response.status(Response.Status.OK).entity(json).build();
            } else {
                json = new JsonResponseObject(true, "Event with the given id doesn't exist.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } else {
            List<GameEvent> events = gameEventService.getAll();
            json = new JsonResponseObject(false, events);
            return Response.status(Response.Status.OK).entity(json).build();
        }
    }

    @Path("")
    @DELETE
    @Secured({Role.ADMIN, Role.LGS})
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeEvent(@Context SecurityContext securityContext, @QueryParam("id") long id) {
        GameEvent event = gameEventService.find(id);
        if (event != null) {
            Principal principal = securityContext.getUserPrincipal();
            String email = principal.getName();
            User user = userService.find(email);
            if (user.getRole().equals(Role.ADMIN) || (user.getRole().equals(Role.LGS) && user.getOwnedLGS().getId() == event.getLgs().getId())) {
                gameEventService.remove(id);
                if (gameEventService.find(id) == null) {
                    json = new JsonResponseObject(false, "Successfully deleted the event!");
                    return Response.status(Response.Status.OK).entity(json).build();
                } else {
                    json = new JsonResponseObject(true, "Something went wrong while deleting the event.");
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
                }
            } else {
                json = new JsonResponseObject(true, "LGS Owners can only delete events from their own LGS");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } else {
            json = new JsonResponseObject(true,"Event with the given id doesn't exist.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }
}
