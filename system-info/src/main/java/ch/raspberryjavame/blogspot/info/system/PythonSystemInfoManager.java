package ch.raspberryjavame.blogspot.info.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import ch.raspberryjavame.blogspot.info.model.SystemInfo;
import ch.raspberryjavame.blogspot.info.script.ScriptManager;

class PythonSystemInfoManager implements SystemInfoManager<SystemInfo> {

	private final Logger LOGGER = LogManager.getLogger(PythonSystemInfoManager.class);

	@Override
	public SystemInfo getSystemInfo() throws SystemInfoException {
		try {
			ScriptManager manager = ScriptManager.getInstance();
			String result = manager.executePythonScript("system.py");
			return createSystemInfoFromJson(result);
		} catch (Exception e) {
			LOGGER.error("Executing python script failed!", e);
			throw new SystemInfoException(e.getMessage(), e, 1003);
		}
	}

	@SuppressWarnings("rawtypes")
	private SystemInfo createSystemInfoFromJson(String json) throws SystemInfoException {
		SystemInfo info = new SystemInfo();
		info.setTransactionInfo(PythonSystemInfoManager.class.getSimpleName());
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = new HashMap<>();
			map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
			});
			if (map != null && !map.isEmpty()) {
				info.setTransactionInfo(json);
				info.setCpuTemperature(Float.valueOf((String) map.get("cpu_temperature")));
				ArrayList ram = (ArrayList) map.get("ram_info");
				info.setTotalRAM(Integer.valueOf((String) ram.get(0)));
				info.setUsedRAM(Integer.valueOf((String) ram.get(1)));
				info.setFreeRAM(Integer.valueOf((String) ram.get(2)));
				ArrayList disk = (ArrayList) map.get("disk_space");
				info.setTotalDiskSpace((String) disk.get(0));
				info.setUsedDiskSpace((String) disk.get(1));
				info.setFreeDiskSpace((String) disk.get(2));
				info.setUsedDiskSpacePercentage((String) disk.get(3));
				info.setTransactionInfo(PythonSystemInfoManager.class.getSimpleName());
			}
		} catch (IOException e) {
			LOGGER.error("converting JSON value failed!", e);
			throw new SystemInfoException("converting JSON value failed!", e, 1004);
		}
		return info;
	}
}
