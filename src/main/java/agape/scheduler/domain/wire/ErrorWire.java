package agape.scheduler.domain.wire;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.NONE)
public class ErrorWire extends AbstractWire<ErrorWire> {
	private static final long serialVersionUID = -4611857133302315489L;
	
	@XmlElement
	private String[] messages;
	
	public ErrorWire() {
		setCreatedTs(new Date());
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
}
