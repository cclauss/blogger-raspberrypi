package ch.raspberryjavame.blogspot.info.model;

import java.time.LocalDateTime;

public abstract class Info {

	private final LocalDateTime date = LocalDateTime.now();
	private String transactionInfo;

	public LocalDateTime getDate() {
		return date;
	}

	public String getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
