package robson.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import robson.dto.ErrorResponse;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {
        int status = 500;
        String message = "Erro interno do servidor.";

        if (exception instanceof BusinessException) {
            status = 400;
            message = exception.getMessage();
        }

        ErrorResponse errorResponse = new ErrorResponse(
                status,
                message,
                "/" + uriInfo.getPath()
        );

        return Response
                .status(status)
                .entity(errorResponse)
                .build();
    }
}