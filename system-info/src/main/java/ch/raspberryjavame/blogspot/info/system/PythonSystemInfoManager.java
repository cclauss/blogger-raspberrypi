package ch.raspberryjavame.blogspot.info.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.raspberryjavame.blogspot.info.model.SysInfo;
import ch.raspberryjavame.blogspot.info.script.ScriptManager;

class PythonSystemInfoManager implements SystemInfoManager {

	private final Logger LOGGER = LogManager.getLogger(PythonSystemInfoManager.class);

	@Override
	public SysInfo getSystemInfo() throws SystemInfoException {
		try {
			SysInfo info = new SysInfo();
			ScriptManager manager = ScriptManager.getInstance();
			manager.updateScriptConfiguration();
			return info;
		} catch (Exception e) {
			LOGGER.error("Executing python script failed!", e);
			throw new SystemInfoException(e.getMessage(), e, 1003);
		}
	}
}
