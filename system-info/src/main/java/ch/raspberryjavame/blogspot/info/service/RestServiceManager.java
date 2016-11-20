package ch.raspberryjavame.blogspot.info.service;

import org.apache.commons.lang3.exception.ExceptionUtils;

import ch.raspberryjavame.blogspot.info.model.CPUInfo;
import ch.raspberryjavame.blogspot.info.model.SystemInfo;
import ch.raspberryjavame.blogspot.info.system.SystemInfoException;
import ch.raspberryjavame.blogspot.info.system.SystemInfoFactory;
import ch.raspberryjavame.blogspot.info.system.SystemInfoFactory.ManagerType;
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

	@SuppressWarnings("unchecked")
	public synchronized SystemInfoResponse<CPUInfo> getCPUInfo() {
		SystemInfoResponse<CPUInfo> result = new SystemInfoResponse<>();
		try {
			SystemInfoManager<CPUInfo> info = SystemInfoFactory.createInfoManager(ManagerType.SIMPLE);
			result.setData(info.getSystemInfo());

		} catch (SystemInfoException e) {
			result.setSuccess(false);
			result.setErrorMessage(e.getMessage());
			result.setCode(e.getErrorCode());
			result.setDeveloperMessage(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public synchronized SystemInfoResponse<SystemInfo> getEnhancedSystemInfo() {
		SystemInfoResponse<SystemInfo> result = new SystemInfoResponse<>();
		try {
			SystemInfoManager<SystemInfo> info = SystemInfoFactory.createInfoManager(ManagerType.ENHANCED);
			result.setData(info.getSystemInfo());

		} catch (SystemInfoException e) {
			result.setSuccess(false);
			result.setErrorMessage(e.getMessage());
			result.setCode(e.getErrorCode());
			result.setDeveloperMessage(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}
}
