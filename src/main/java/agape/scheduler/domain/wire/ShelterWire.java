package agape.scheduler.domain.wire;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "shelter")
public class ShelterWire extends AbstractWire<ShelterWire> {
	private static final long serialVersionUID = -8064638018899894403L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
