package ch.raspberryjavame.blogspot.info.model;

public class SystemInfo extends Info {

	private float cpuTemperature;
	private String cpuArchitecture;

	public float getCpuTemperature() {
		return cpuTemperature;
	}

	public void setCpuTemperature(float cpuTemperature) {
		this.cpuTemperature = cpuTemperature;
	}

	public String getCpuArchitecture() {
		return cpuArchitecture;
	}

	public void setCpuArchitecture(String cpuArchitecture) {
		this.cpuArchitecture = cpuArchitecture;
	}
}
