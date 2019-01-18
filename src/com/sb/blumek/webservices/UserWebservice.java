package com.sb.blumek.webservices;

import com.sb.blumek.daos.UserDAO;
import com.sb.blumek.models.User;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserWebservice {

    @Inject
    UserDAO userDAO;

    @GET
    public Response getUsers() {
        List<User> users = userDAO.getUsers();
        if (users != null) {
            return Response.ok(users).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{userId}")
    public Response getUser(@PathParam("userId") @Min(0) Integer userId) {
        User user = userDAO.getUser(userId);
        if (user != null) {
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/login")
    public Response loginUser(User _user) {
        User user = userDAO.loginUser(_user);
        return Response.ok(user).build();
    }

    @POST
    public Response createUser(User user) {
        userDAO.createUser(user);
        return Response.ok().build();
    }
}
