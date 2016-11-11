package ch.raspberryjavame.blogspot.info.service.local;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;

import ch.raspberryjavame.blogspot.info.service.RestServiceManager;
import ch.raspberryjavame.blogspot.info.service.SystemInfoResponse;

@Path("/system")
public class SystemInfoService {

	@GET
	@Path("/logs")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getLogs() {
		return null;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/info")
	public Response getLifeEntry() {
		try {
			RestServiceManager manager = RestServiceManager.getInstance();
			return Response.status(Response.Status.OK).entity(manager.getSystemInfo()).build();
		} catch (Exception ex) {
			return Response.status(Response.Status.OK).entity(createExceptionResponse(ex)).build();
		}
	}

	private SystemInfoResponse<Exception> createExceptionResponse(Exception ex) {
		return createExceptionResponse(ex, "An unexpected error occurred! Check system logs for more information.");
	}

	private SystemInfoResponse<Exception> createExceptionResponse(Exception ex, String message) {
		SystemInfoResponse<Exception> result = new SystemInfoResponse<>();
		result.setSuccess(false);
		result.setErrorMessage(message);
		result.setDeveloperMessage(ExceptionUtils.getStackTrace(ex));
		return result;
	}
}
