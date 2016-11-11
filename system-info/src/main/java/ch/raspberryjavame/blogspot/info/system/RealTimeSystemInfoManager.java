package ch.raspberryjavame.blogspot.info.system;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.platform.PlatformManager;
import com.pi4j.system.SystemInfo;

import ch.raspberryjavame.blogspot.info.model.SysInfo;

class RealTimeSystemInfoManager implements SystemInfoManager {

	protected final Logger LOGGER = LogManager.getLogger(RealTimeSystemInfoManager.class);

	@Override
	public SysInfo getSystemInfo() throws SystemInfoException {
		SysInfo result = new SysInfo();
		try {
			result.setBoardType(SystemInfo.getBoardType().name());
			result.setPlatform(PlatformManager.getPlatform().getLabel());
			result.setProcessor(SystemInfo.getProcessor());
			result.setCpuTemperature((SystemInfo.getCpuTemperature()));
			result.setCpuArchitecture(SystemInfo.getCpuArchitecture());
			result.setCpuImplementer(SystemInfo.getCpuImplementer());
			result.setCpuPart(SystemInfo.getCpuPart());
			result.setCpuRevision(SystemInfo.getCpuRevision());
			result.setCpuVariant(SystemInfo.getCpuVariant());
			result.setCpuVoltage(SystemInfo.getCpuVoltage());
			return result;
		} catch (UnsupportedOperationException e) {
			LOGGER.error("Access to system refused, check permission for GPIO features!", e);
			throw new SystemInfoException("Error while calling a Pi4J function!", e, 1001);
		} catch (IOException | InterruptedException e) {
			LOGGER.error("Access to system failed!", e);
			throw new SystemInfoException(e.getMessage(), e, 1002);
		}
	}
}
