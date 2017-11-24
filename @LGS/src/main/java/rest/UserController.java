package rest;

import domain.User;
import enums.Role;
import service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dennis van Opstal on 23-11-2017.
 */
@Path("/user")
public class UserController {
    @Inject
    private UserService userService;
    private JsonResponseObject json;

    @Path("")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName,
                             @FormParam("email") String email, @FormParam("password") String password) {
        if (userService.find(email) == null) {
            byte[] salt = generateSalt();
            String hashedPassword = hash(password, salt);
            if (hashedPassword != null) {
                Role role;
                if (userService.getAll().size() == 0) {
                    role = Role.ADMIN;
                } else {
                    role = Role.NORMAL_USER;
                }
                User user = new User(firstName, lastName, hashedPassword, email, role, salt);
                userService.create(user);
                if (userService.find(email) != null) {
                    json = new JsonResponseObject(false, "User created successfully!");
                    return Response.status(Response.Status.OK).entity(json).build();
                } else {
                    json = new JsonResponseObject(true, "Something went wrong while creating user.");
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
                }
            } else {
                json = new JsonResponseObject(true, "Something went wrong while hashing the password.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } else {
            json = new JsonResponseObject(true,"User with email: " + email + " already exists.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email")String email, @FormParam("password") String password) {
        User user = userService.find(email);
        if (user != null) {
            if (user.getPassword().equals(hash(password,user.getSalt()))) {
                String token = generateToken(user.getId());
                Date date = generateTokenExpiration();
                user.refreshToken(token,date);
                userService.edit(user);
                json = new JsonResponseObject(false, (Object)token);
                return Response.status(Response.Status.OK).entity(json).build();
            } else {
                json = new JsonResponseObject(true, "The given password was incorrect.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
            }
        } else {
            json = new JsonResponseObject(true,"User with email: " + email + " doesn't exist.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }

    @Path("/validation")
    @GET
    @Secured({Role.NORMAL_USER, Role.LGS, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateToken(@Context SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();
        String email = principal.getName();
        json = new JsonResponseObject(false,(Object)email);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @Path("/role")
    @PUT
    @Secured({Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response editRole(@FormParam("id") long id, @FormParam("role") String role) {
        User user = userService.find(id);
        if (user != null) {
            user.setRole(Role.valueOf(role));
            userService.edit(user);
            json = new JsonResponseObject(false, "Successfully changed role!");
            return Response.status(Response.Status.OK).entity(json).build();
        } else {
            json = new JsonResponseObject(true,"User with id: " + id + " doesn't exist.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }

    @Path("")
    @GET
    @Secured({Role.NORMAL_USER,Role.LGS,Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@Context SecurityContext securityContext, @QueryParam("id") long id) {
        User user = userService.find(id);
        if (user != null) {
            json = new JsonResponseObject(false,user);
            return Response.status(Response.Status.OK).entity(json).build();
        } else {
            json = new JsonResponseObject(true,"User with id: " + id + " doesn't exist.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
    }

    //region Security
    //region Hash and Salt
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

    private String hash(String data, byte[] salt) {
        try {
            byte[] unsaltedBytes = data.getBytes();
            byte[] saltedBytes = addSalt(unsaltedBytes, salt);
            if (saltedBytes == null) {
                return null;
            }
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(saltedBytes);
            return bytesToHex(md.digest());
        } catch(NoSuchAlgorithmException e) {
            return null;
        }
    }

    private byte[] addSalt(byte[] unsaltedBytes, byte[] salt) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(salt);
            outputStream.write(unsaltedBytes);
            outputStream.write(salt);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputStream.toByteArray();
    }
    //endregion

    //region Tokens

    private String generateToken(long id) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytesToHex(bytes);
        return token + id;
    }

    private Date generateTokenExpiration() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        return c.getTime();
    }
    //endregion

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
    //endregion
}