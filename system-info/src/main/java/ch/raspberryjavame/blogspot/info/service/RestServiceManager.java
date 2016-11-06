package ch.raspberryjavame.blogspot.info.service;

import org.apache.commons.lang3.exception.ExceptionUtils;

import ch.raspberryjavame.blogspot.info.model.SysInfo;
import ch.raspberryjavame.blogspot.info.system.SystemInfoException;
import ch.raspberryjavame.blogspot.info.system.SystemInfoManager;

public class RestServiceManager {

	private static RestServiceManager instance = null;

	private RestServiceManager() {
		super();
	}

	public static RestServiceManager getInstance() {
		if (instance == null) {
			instance = new RestServiceManager();
		}
		return instance;
	}

	public synchronized SystemInfoResponse<SysInfo> getSystemInfo() {
		SystemInfoResponse<SysInfo> result = new SystemInfoResponse<>();
		try {
			SystemInfoManager info = new SystemInfoManager();
			result.setData(info.getRealTimeSystemInfo());

		} catch (SystemInfoException ex) {
			result.setSuccess(false);
			result.setErrorMessage(ex.getMessage());
			result.setCode(ex.getErrorCode());
			result.setDeveloperMessage(ExceptionUtils.getStackTrace(ex));
		}
		return result;
	}
}
