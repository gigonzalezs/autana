package io.iunigo.autana.persistence;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractInstanceTrace {

	@Id
	private String id;
	
	@Column(nullable = false, updatable = false)
	private int step;
	
	@Column(nullable = false, updatable = false)
	private String event;
	
	@Column(nullable = false, updatable = false)
	private Date date;
	
	@Column(nullable = false, updatable = false)
	private String path;
	
	@Lob
	@Column(nullable = false, updatable = false)
	private String payload;
	
	public AbstractInstanceTrace() {
		super();
	}
	public AbstractInstanceTrace(int step, String event, String path, String payload) {
		super();
		this.id = UUID.randomUUID().toString();
		this.step = step;
		this.event = event;
		this.date = new Date();
		this.path = path;
		this.payload = payload;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	
	@Override
	public String toString() {
		return "AbstractInstanceTrace [id=" + id + ", step=" + step + ", event=" + event + ", date=" + date + ", path="
				+ path + ", payload=" + payload + "]";
	}
}
