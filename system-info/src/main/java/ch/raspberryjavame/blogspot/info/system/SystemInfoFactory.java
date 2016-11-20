package ch.raspberryjavame.blogspot.info.system;

public class SystemInfoFactory {

	public enum ManagerType {
		SIMPLE, ENHANCED
	}

	private SystemInfoFactory() {
		throw new RuntimeException("There  is no need to create an instance of this class!");
	}

	@SuppressWarnings("rawtypes")
	public static SystemInfoManager createInfoManager(ManagerType type) {
		switch (type) {
		case ENHANCED:
			return new PythonSystemInfoManager();
		default:
			return new Pi4jSystemInfoManager();
		}
	}
}
