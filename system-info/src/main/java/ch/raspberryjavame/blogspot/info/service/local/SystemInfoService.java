package ch.raspberryjavame.blogspot.info.service.local;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ch.raspberryjavame.blogspot.info.model.LogEntry;
import ch.raspberryjavame.blogspot.info.service.ServiceDispatcher;

@Path("/system")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class SystemInfoService {

	@GET
	@Path("/logs")
	public Collection<LogEntry> getLogs() {
		return Collections.EMPTY_LIST;
	}

	@GET
	@Path("/info")
	public LogEntry getLatestEntry() {
		ServiceDispatcher dispatcher = ServiceDispatcher.getInstance();
		return dispatcher.getSystemInfo();
	}
}
