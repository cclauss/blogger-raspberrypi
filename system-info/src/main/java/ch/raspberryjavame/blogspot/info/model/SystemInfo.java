package ch.raspberryjavame.blogspot.info.model;

public class SystemInfo extends Info {

	private float cpuTemperature;
	private String totalDiskSpace;
	private String usedDiskSpace;
	private String freeDiskSpace;
	private String usedDiskSpacePercentage;
	private int totalRAM;
	private int usedRAM;
	private int freeRAM;

	public float getCpuTemperature() {
		return cpuTemperature;
	}

	public void setCpuTemperature(float cpuTemperature) {
		this.cpuTemperature = cpuTemperature;
	}

	public String getTotalDiskSpace() {
		return totalDiskSpace;
	}

	public void setTotalDiskSpace(String totalDiskSpace) {
		this.totalDiskSpace = totalDiskSpace;
	}

	public String getUsedDiskSpace() {
		return usedDiskSpace;
	}

	public void setUsedDiskSpace(String usedDiskSpace) {
		this.usedDiskSpace = usedDiskSpace;
	}

	public String getFreeDiskSpace() {
		return freeDiskSpace;
	}

	public void setFreeDiskSpace(String freeDiskSpace) {
		this.freeDiskSpace = freeDiskSpace;
	}

	public float getTotalRAM() {
		return totalRAM;
	}

	public int getUsedRAM() {
		return usedRAM;
	}

	public void setUsedRAM(int usedRAM) {
		this.usedRAM = usedRAM;
	}

	public int getFreeRAM() {
		return freeRAM;
	}

	public void setFreeRAM(int freeRAM) {
		this.freeRAM = freeRAM;
	}

	public void setTotalRAM(int totalRAM) {
		this.totalRAM = totalRAM;
	}

	public String getUsedDiskSpacePercentage() {
		return usedDiskSpacePercentage;
	}

	public void setUsedDiskSpacePercentage(String usedDiskSpacePercentage) {
		this.usedDiskSpacePercentage = usedDiskSpacePercentage;
	}
}
