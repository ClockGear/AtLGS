package rest;

import domain.LGS;
import domain.User;
import enums.Role;
import service.LGSService;
import service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Dennis van Opstal on 15-12-2017.
 */
@Path("/lgs")
public class LGSController {
    @Inject
    private LGSService lgsService;
    @Inject
    private UserService userService;
    private JsonResponseObject json;

    @Path("")
    @POST
    @Secured({Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLGS(@FormParam("name") String name, @FormParam("address") String address) {
        LGS lgs = new LGS(name,address);
        lgsService.create(lgs);
        json = new JsonResponseObject(false,"Successfully created a LGS!");
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @Path("")
    @GET
    @Secured({Role.NORMAL_USER, Role.LGS, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLGSs(@QueryParam("id") long id) {
        if (id > 0) {
            LGS lgs = lgsService.find(id);
            if (lgs != null) {
                json = new JsonResponseObject(false, lgs);
                return Response.status(Response.Status.OK).entity(json).build();
            } else {
                json = new JsonResponseObject(true, "LGS with the given id doesn't exist.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } else {
            List<LGS> lgsList = lgsService.getAll();
            json = new JsonResponseObject(false, lgsList);
            return Response.status(Response.Status.OK).entity(json).build();
        }
    }

    @Path("")
    @DELETE
    @Secured({Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeLGS(@QueryParam("id") long id) {
        LGS lgs = lgsService.find(id);
        if (lgs != null) {
            for (User user : userService.getAll()) {
                if (user.getOwnedLGS() != null && user.getOwnedLGS().getId() == id) {
                    user.setOwnedLGS(null);
                    userService.edit(user);
                }
            }
            lgsService.remove(id);
            if (lgsService.find(id) == null) {
                json = new JsonResponseObject(false, "Successfully deleted the LGS!");
                return Response.status(Response.Status.OK).entity(json).build();
            } else {
                json = new JsonResponseObject(true, "Something went wrong while deleting the LGS.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } else {
            json = new JsonResponseObject(true,"LGS with the given id doesn't exist.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }
}
