package ch.raspberryjavame.blogspot.info.service;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class InfoRestApplication extends ResourceConfig {

	public InfoRestApplication() {
		packages("ch.raspberryjavame.blogspot.info.service.local");
	}
}
