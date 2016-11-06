package ch.raspberryjavame.blogspot.info.system;

import java.io.IOException;

import com.pi4j.platform.PlatformManager;
import com.pi4j.system.SystemInfo;

import ch.raspberryjavame.blogspot.info.model.LogEntry;

public class SystemInfoManager {

	public LogEntry getRealTimeSystemInfo() throws SystemInfoException {
		LogEntry result = new LogEntry();
		try {
			result.setBoardType(SystemInfo.getBoardType().name());
			result.setPlatform(PlatformManager.getPlatform().getLabel());
			result.setProcessor(SystemInfo.getProcessor());
			result.setCpuTemperature((SystemInfo.getCpuTemperature()));
			return result;
		} catch (UnsupportedOperationException e) {
			throw new SystemInfoException(
					"Error while reading value from system! It seams the used method  is not implemnted.", e, 1001);
		} catch (IOException | InterruptedException e) {
			throw new SystemInfoException(
					"Error while reading value from system, this error is unexcpected! Check the hardware please.", e,
					1001);
		}
	}
}
