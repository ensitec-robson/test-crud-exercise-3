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
import robson.dto.ServiceOrderRequestDto;
import robson.dto.ServiceOrderResponseDto;
import robson.dto.UpdateServiceOrderStatusDto;
import robson.service.ServiceOrderService;

@Path("/api/service-orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceOrderResource {

    @Inject
    ServiceOrderService serviceOrderService;

    @POST
    public Response create(ServiceOrderRequestDto dto) {
        ServiceOrderResponseDto responseDto = serviceOrderService.create(dto);
        return Response.status(Response.Status.CREATED).entity(responseDto).build();
    }

    @GET
    public List<ServiceOrderResponseDto> findAll() {
        return serviceOrderService.findAll();
    }

    @GET
    @Path("/{id}")
    public ServiceOrderResponseDto findById(@PathParam("id") Long id) {
        return serviceOrderService.findById(id);
    }

    @PUT
    @Path("/{id}/status")
    public ServiceOrderResponseDto updateStatus(@PathParam("id") Long id, UpdateServiceOrderStatusDto dto) {
        return serviceOrderService.updateStatus(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        serviceOrderService.delete(id);
        return Response.noContent().build();
    }
}