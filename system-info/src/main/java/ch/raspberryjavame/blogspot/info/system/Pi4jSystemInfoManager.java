package ch.raspberryjavame.blogspot.info.system;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.platform.PlatformManager;
import com.pi4j.system.SystemInfo;

import ch.raspberryjavame.blogspot.info.model.CPUInfo;

class Pi4jSystemInfoManager implements SystemInfoManager<CPUInfo> {

	protected final Logger LOGGER = LogManager.getLogger(Pi4jSystemInfoManager.class);

	@Override
	public CPUInfo getSystemInfo() throws SystemInfoException {
		CPUInfo result = new CPUInfo();
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
			result.setTransactionInfo(Pi4jSystemInfoManager.class.getSimpleName());
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
