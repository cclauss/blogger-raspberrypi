package ch.raspberryjavame.blogspot.info.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.raspberryjavame.blogspot.info.model.SystemInfo;
import ch.raspberryjavame.blogspot.info.script.ScriptManager;

class PythonSystemInfoManager implements SystemInfoManager<SystemInfo> {

	private final Logger LOGGER = LogManager.getLogger(PythonSystemInfoManager.class);

	@Override
	public SystemInfo getSystemInfo() throws SystemInfoException {
		try {
			SystemInfo info = new SystemInfo();
			ScriptManager manager = ScriptManager.getInstance();
			String result = manager.executePythonScript("system.py");
			info.setTransactionInfo(PythonSystemInfoManager.class.getSimpleName());
			// info.setCpuTemperature(Float.valueOf(result));
			info.setTransactionInfo(result);
			return info;
		} catch (Exception e) {
			LOGGER.error("Executing python script failed!", e);
			throw new SystemInfoException(e.getMessage(), e, 1003);
		}
	}
}
