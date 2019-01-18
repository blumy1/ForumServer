package com.sb.blumek.webservices;

import com.sb.blumek.daos.ThreadDAO;
import com.sb.blumek.models.Post;
import com.sb.blumek.models.Thread;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/threads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ThreadWebservice {

    @Inject
    ThreadDAO threadDAO;

    @GET
    public Response getThreads() {
        List<Thread> threads = threadDAO.getThreads();
        if (threads != null) {
            return Response.ok(threads).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{threadId}")
    public Response getThread(@PathParam("threadId") @Min(0) Integer threadId) {
        Thread thread = threadDAO.getThread(threadId);
        if (thread != null) {
            return Response.ok(thread).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("last")
    public Response getLastThread() {
        Thread thread = threadDAO.getLastThread();
        if (thread != null) {
            return Response.ok(thread).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createThread(Thread thread) {
        Thread _thread = threadDAO.createThread(thread);
        return Response.ok(_thread).build();
    }

    @GET
    @Path("{threadId}/posts")
    public Response getThreadPosts(@PathParam("threadId") @Min(0) Integer threadId) {
        List<Post> posts = threadDAO.getThreadPosts(threadId);
        if (posts != null) {
            return Response.ok(posts).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{threadId}/posts/{postId}")
    public Response getThreadPost(@PathParam("threadId") @Min(0) Integer threadId, @PathParam("postId") @Min(0) Integer postId) {
        Post post = threadDAO.getThreadPost(threadId, postId);
        if (post != null) {
            return Response.ok(post).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{threadId}/posts/last")
    public Response getThreadPost(@PathParam("threadId") @Min(0) Integer threadId) {
        Post post = threadDAO.getLastThreadPost(threadId);
        return Response.ok(post).build();
    }

    @POST
    @Path("{threadId}/posts")
    public Response createThreadPost(@PathParam("threadId") @Min(0) Integer threadId, Post post) {
        threadDAO.createThreadPost(threadId, post);
        return Response.ok().build();
    }
}
