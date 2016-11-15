package ch.raspberryjavame.blogspot.info.model;

import java.time.LocalDateTime;

public class SysInfo {

	private final LocalDateTime date = LocalDateTime.now();
	private String platform;
	private String processor;
	private String boardType;
	private float cpuTemperature;
	private String cpuArchitecture;
	private String cpuImplementer;
	private String cpuPart;
	private String cpuRevision;
	private String cpuVariant;
	private float cpuVoltage;
	private String transactionInfo;

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public float getCpuTemperature() {
		return cpuTemperature;
	}

	public void setCpuTemperature(float cpuTemperature) {
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

	public String getCpuArchitecture() {
		return cpuArchitecture;
	}

	public void setCpuArchitecture(String cpuArchitecture) {
		this.cpuArchitecture = cpuArchitecture;
	}

	public String getCpuImplementer() {
		return cpuImplementer;
	}

	public void setCpuImplementer(String cpuImplementer) {
		this.cpuImplementer = cpuImplementer;
	}

	public String getCpuPart() {
		return cpuPart;
	}

	public void setCpuPart(String cpuPart) {
		this.cpuPart = cpuPart;
	}

	public String getCpuRevision() {
		return cpuRevision;
	}

	public void setCpuRevision(String cpuRevision) {
		this.cpuRevision = cpuRevision;
	}

	public String getCpuVariant() {
		return cpuVariant;
	}

	public void setCpuVariant(String cpuVariant) {
		this.cpuVariant = cpuVariant;
	}

	public float getCpuVoltage() {
		return cpuVoltage;
	}

	public void setCpuVoltage(float cpuVoltage) {
		this.cpuVoltage = cpuVoltage;
	}

	public String getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
