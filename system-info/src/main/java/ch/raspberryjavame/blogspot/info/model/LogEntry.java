package ch.raspberryjavame.blogspot.info.model;

import java.time.LocalDateTime;

public class LogEntry {

	private final LocalDateTime date = LocalDateTime.now();
	private String platform;
	private Float cpuTemperature;
	private String processor;
	private String boardType;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Float getCpuTemperature() {
		return cpuTemperature;
	}

	public void setCpuTemperature(Float cpuTemperature) {
		this.cpuTemperature = cpuTemperature;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public LocalDateTime getDate() {
		return date;
	}

}
