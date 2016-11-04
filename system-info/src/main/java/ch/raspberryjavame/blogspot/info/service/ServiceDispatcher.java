package ch.raspberryjavame.blogspot.info.service;

import ch.raspberryjavame.blogspot.info.model.LogEntry;
import ch.raspberryjavame.blogspot.info.system.SystemInfoManager;

public class ServiceDispatcher {

	private static ServiceDispatcher instance = null;

	private ServiceDispatcher() {
		super();
	}

	public static ServiceDispatcher getInstance() {
		if (instance == null) {
			instance = new ServiceDispatcher();
		}
		return instance;
	}

	public synchronized LogEntry getSystemInfo() {
		SystemInfoManager info = new SystemInfoManager();
		return info.getRealTimeSystemInfo();
	}
}
