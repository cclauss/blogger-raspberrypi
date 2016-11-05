package ch.raspberryjavame.blogspot.info.service.local;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.raspberryjavame.blogspot.info.model.LogEntry;
import ch.raspberryjavame.blogspot.info.service.ServiceDispatcher;

@Path("/system")
public class SystemInfoService {

	@GET
	@Path("/logs")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<LogEntry> getLogs() {
		return Collections.EMPTY_LIST;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/info")
	public LogEntry getLatestEntry() {
		ServiceDispatcher dispatcher = ServiceDispatcher.getInstance();
		return dispatcher.getSystemInfo();
	}
}
