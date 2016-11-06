package ch.raspberryjavame.blogspot.info.service.local;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.raspberryjavame.blogspot.info.service.RestServiceManager;

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
		RestServiceManager manager = RestServiceManager.getInstance();
		return Response.status(Response.Status.OK).entity(manager.getSystemInfo()).build();
	}
}
