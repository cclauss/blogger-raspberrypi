package ch.raspberryjavame.blogspot.info.system;

import ch.raspberryjavame.blogspot.info.model.SysInfo;

public interface SystemInfoManager {

	public SysInfo getSystemInfo() throws SystemInfoException;
}
