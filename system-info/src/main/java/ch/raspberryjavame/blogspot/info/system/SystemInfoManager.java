package ch.raspberryjavame.blogspot.info.system;

import java.io.IOException;

import com.pi4j.platform.PlatformManager;
import com.pi4j.system.SystemInfo;

import ch.raspberryjavame.blogspot.info.model.LogEntry;

public class SystemInfoManager {

	public LogEntry getRealTimeSystemInfo() {
		LogEntry result = new LogEntry();
		try {
			result.setBoardType(SystemInfo.getBoardType().name());
			result.setPlatform(PlatformManager.getPlatform().getLabel());
			result.setProcessor(SystemInfo.getProcessor());
			result.setCpuTemperature(Float.valueOf(SystemInfo.getCpuTemperature()).toString());
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
