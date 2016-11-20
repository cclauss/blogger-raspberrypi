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
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/cpu")
	public Response getCPUInfo() {
		try {
			RestServiceManager manager = RestServiceManager.getInstance();
			return Response.status(Response.Status.OK).entity(manager.getCPUInfo()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(createExceptionResponse(e)).build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/info")
	public Response getSystemInfo() {
		try {
			RestServiceManager manager = RestServiceManager.getInstance();
			return Response.status(Response.Status.OK).entity(manager.getEnhancedSystemInfo()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.OK).entity(createExceptionResponse(e)).build();
		}
	}

	private SystemInfoResponse<Exception> createExceptionResponse(Exception ex) {
		return createExceptionResponse(ex, "An unexpected error occurred! Check system logs for more information.");
	}

	private SystemInfoResponse<Exception> createExceptionResponse(Exception e, String message) {
		SystemInfoResponse<Exception> result = new SystemInfoResponse<>();
		result.setSuccess(false);
		result.setErrorMessage(message);
		result.setDeveloperMessage(ExceptionUtils.getStackTrace(e));
		return result;
	}
}
