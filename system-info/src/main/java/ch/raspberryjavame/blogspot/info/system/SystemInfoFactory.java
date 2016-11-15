package ch.raspberryjavame.blogspot.info.system;

public class SystemInfoFactory {

	private SystemInfoFactory() {
		throw new RuntimeException("There  is no need to create an instance of this class!");
	}

	public static SystemInfoManager createInfoManager() {
		return new PythonSystemInfoManager();
	}
}
