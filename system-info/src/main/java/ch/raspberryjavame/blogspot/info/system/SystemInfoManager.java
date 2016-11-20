package ch.raspberryjavame.blogspot.info.system;

import ch.raspberryjavame.blogspot.info.model.Info;

public interface SystemInfoManager<T extends Info> {

	public T getSystemInfo() throws SystemInfoException;
}
