package rest;

import domain.Format;
import enums.Game;
import enums.Role;
import service.FormatService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Dennis van Opstal on 12-1-2018.
 */
@Path("/format")
public class FormatController {
    @Inject
    private FormatService formatService;
    private JsonResponseObject json;

    @Path("")
    @POST
    @Secured({Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFormat(@FormParam("name") String name, @FormParam("game") String game, @FormParam("abbreviation") String abbreviation) {
        Format format = new Format(name, Game.valueOf(game), abbreviation);
        formatService.create(format);
        json = new JsonResponseObject(false,"Successfully created a Format!");
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @Path("")
    @GET
    @Secured({Role.NORMAL_USER, Role.LGS, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFormats(@QueryParam("id") long id) {
        if (id > 0) {
            Format format = formatService.find(id);
            if (format != null) {
                json = new JsonResponseObject(false, format);
                return Response.status(Response.Status.OK).entity(json).build();
            } else {
                json = new JsonResponseObject(true, "Format with the given id doesn't exist.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } else {
            List<Format> formats = formatService.getAll();
            json = new JsonResponseObject(false, formats);
            return Response.status(Response.Status.OK).entity(json).build();
        }
    }

    @Path("")
    @DELETE
    @Secured({Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeFormat(@QueryParam("id") long id) {
        Format format = formatService.find(id);
        if (format != null) {
            formatService.remove(id);
            if (formatService.find(id) == null) {
                json = new JsonResponseObject(false, "Successfully deleted the Format!");
                return Response.status(Response.Status.OK).entity(json).build();
            } else {
                json = new JsonResponseObject(true, "Something went wrong while deleting the Format.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } else {
            json = new JsonResponseObject(true,"Format with the given id doesn't exist.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }
}
