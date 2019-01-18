package com.sb.blumek.webservices;

import com.sb.blumek.daos.CategoryDAO;
import com.sb.blumek.models.Category;
import com.sb.blumek.models.Post;
import com.sb.blumek.models.Subcategory;
import com.sb.blumek.models.Thread;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryWebservice {

    @Inject
    CategoryDAO categoryDAO;

    @GET
    public Response getCategories() {
        List<Category> categories = categoryDAO.getCategories();
        if (categories != null) {
            return Response.ok(categories).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{categoryId}")
    public Response getCategory(@PathParam("categoryId") @Min(0) Integer categoryId) {
        Category category = categoryDAO.getCategory(categoryId);
        if (category != null) {
            return Response.ok(category).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createCategory(Category category) {
        categoryDAO.createCategory(category);
        return Response.ok().build();
    }

    @GET
    @Path("/subcategories/{subcategoryId}/threads")
    public Response getSubcategoryThreads(@PathParam("subcategoryId") @Min(0) Integer subcategoryId) {
        List<Thread> threads = categoryDAO.getSubcategoryThreads(subcategoryId);
        if (threads != null) {
            return Response.ok(threads).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{categoryId}/subcategories")
    public Response getCategorySubcategories(@PathParam("categoryId") @Min(0) Integer categoryId) {
        List<Subcategory> subcategories = categoryDAO.getCategorySubcategories(categoryId);
        if (subcategories != null) {
            return Response.ok(subcategories).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/subcategories/{subcategoryId}")
    public Response getCategorySubcategory(@PathParam("subcategoryId") @Min(0) Integer subcategoryId) {
        Subcategory subcategory = categoryDAO.getSubcategory(subcategoryId);
        if (subcategory != null) {
            return Response.ok(subcategory).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{categoryId}/subcategories/{subcategoryId}/last")
    public Response getLastSubcategoryPost(@PathParam("categoryId") @Min(0) Integer categoryId, @PathParam("subcategoryId") @Min(0) Integer subcategoryId) {
        Post post = categoryDAO.getLastSubcategoryPost(categoryId, subcategoryId);
        if (post != null) {
            return Response.ok(post).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("{categoryId}/subcategories")
    public Response createCategorySubcategory(@PathParam("categoryId") @Min(0) Integer categoryId, Subcategory subcategory) {
        categoryDAO.createCategorySubcategory(categoryId, subcategory);
        return Response.ok().build();
    }
}
