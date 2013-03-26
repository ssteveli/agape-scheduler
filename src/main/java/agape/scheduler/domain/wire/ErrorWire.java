package agape.scheduler.domain.wire;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorWire implements Serializable {
	private static final long serialVersionUID = -4611857133302315489L;
	private String[] messages;
	private Date createdTs;
	
	public ErrorWire() {
		this.createdTs = new Date();
	}
	
	public ErrorWire(String message) {
		this();
		this.messages = new String[] {
			message
		};
	}

	public ErrorWire(String[] messages) {
		this();
		this.messages = messages;
	}
	
	public String[] getMessage() {
		return messages;
	}

	public void setMessage(String[] messages) {
		this.messages = messages;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}
}
