package agape.scheduler.resources;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

import agape.scheduler.domain.wire.VolunteerWire;

@Path("/v1/volunteer")
@Component
public class Volunteer {

	@GET
	public String sayHello() {
		return "hello";
	}
	
	@GET
	@Path("/{id}")
	public VolunteerWire getVolunteer(@PathParam("id") String userId) {
		VolunteerWire v = new VolunteerWire();
		v.setUsername(userId);
		v.setFirstName("Homer");
		v.setLastName("Simpson");
		v.setCreatedTs(new Date());
		
		return v;
	}
}
