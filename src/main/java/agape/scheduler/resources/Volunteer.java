package agape.scheduler.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

@Path("/v1/volunteer")
@Component
public class Volunteer {

	@GET
	public String sayHello() {
		return "hello";
	}
}
