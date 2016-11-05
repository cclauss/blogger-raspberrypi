package ch.raspberryjavame.blogspot.info.service;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class InfoApplication extends ResourceConfig {

	public InfoApplication() {
		packages("ch.raspberryjavame.blogspot.info.service.local");
	}
}
