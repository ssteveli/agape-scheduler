package agape.scheduler.resources;

import java.util.List;

import agape.scheduler.domain.wire.ErrorWire;

public abstract class AbstractResource {

	protected ErrorWire createError(String message) {
		return new ErrorWire(message);
	}
	
	protected ErrorWire createError(String[] messages) {
		return new ErrorWire(messages);
	}
	
	protected ErrorWire createError(List<String> messages) {
		return new ErrorWire(messages.toArray(new String[0]));
	}
	
}
