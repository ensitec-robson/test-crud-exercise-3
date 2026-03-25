package robson.resourse;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import robson.dto.UserRequestDto;
import robson.dto.UserResponseDto;
import robson.service.UserService;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @POST
    public Response create(UserRequestDto dto) {
        UserResponseDto responseDto = userService.create(dto);
        return Response.status(Response.Status.CREATED).entity(responseDto).build();
    }

    @GET
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    public UserResponseDto findById(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @PUT
    @Path("/{id}")
    public UserResponseDto update(@PathParam("id") Long id, UserRequestDto dto) {
        return userService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        userService.delete(id);
        return Response.noContent().build();
    }
}