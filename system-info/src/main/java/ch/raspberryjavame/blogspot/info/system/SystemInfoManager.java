package ch.raspberryjavame.blogspot.info.system;

import java.io.IOException;

import com.pi4j.platform.PlatformManager;
import com.pi4j.system.SystemInfo;

import ch.raspberryjavame.blogspot.info.model.SysInfo;

public class SystemInfoManager {

	public SysInfo getRealTimeSystemInfo() throws SystemInfoException {
		SysInfo result = new SysInfo();
		try {
			result.setBoardType(SystemInfo.getBoardType().name());
			result.setPlatform(PlatformManager.getPlatform().getLabel());
			result.setProcessor(SystemInfo.getProcessor());
			// result.setCpuTemperature((SystemInfo.getCpuTemperature()));
			result.setCpuArchitecture(SystemInfo.getCpuArchitecture());
			result.setCpuImplementer(SystemInfo.getCpuImplementer());
			result.setCpuPart(SystemInfo.getCpuPart());
			result.setCpuRevision(SystemInfo.getCpuRevision());
			result.setCpuVariant(SystemInfo.getCpuVariant());
			// result.setCpuVoltage(SystemInfo.getCpuVoltage());
			return result;
		} catch (UnsupportedOperationException e) {
			throw new SystemInfoException("Error while calling a Pi4J function!", e, 1001);
		} catch (IOException | InterruptedException e) {
			throw new SystemInfoException(e.getMessage(), e, 1001);
		}
	}
}
