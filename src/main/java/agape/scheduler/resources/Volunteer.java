package agape.scheduler.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import agape.scheduler.domain.wire.VolunteerWire;
import agape.scheduler.spring.repository.VolunteerRepository;

import com.sun.jersey.api.JResponse;

@Path("/v1/volunteers")
@Component
public class Volunteer extends AbstractResource {
	
	@Autowired
	private VolunteerRepository repo;
	
	@GET
	public JResponse<Iterable<VolunteerWire>> getVolunteers() {		
		return JResponse.ok(repo.findAll()).build();
	}
	
	@GET
	@Path("/{userName}")
	public Response getVolunteer(@PathParam("userName") String userName) {
		VolunteerWire v = repo.findOne(userName);
		
		if (v == null) {
			return Response
					.status(Status.NOT_FOUND)
					.entity(createError("volunteer not found"))
					.build();
		} else {
			return Response.ok(v).build();
		}
	}
	
	@POST
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createVolunteer(@PathParam("userName") String userName, MultivaluedMap<String, String> request) {
		return updateVolunteer(userName, request);
	}
	
	@PUT
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateVolunteer(@PathParam("userName") String userName, MultivaluedMap<String, String> request) {
		VolunteerWire v = repo.findOne(userName);
		
		if (v == null) {
			v = new VolunteerWire();
			v.setCreatedTs(new Date());
			v.setLastUpdatedTs(v.getCreatedTs());
			v.setUsername(userName);
		} else {
			v.setLastUpdatedTs(new Date());
		}
		
		v.setEmailAddress(request.getFirst("email"));
		v.setFirstName(request.getFirst("firstName"));
		v.setHomePhone(request.getFirst("homePhone"));
		v.setLastName(request.getFirst("lastName"));
		v.setLastUpdatedTs(null);
		v.setMobilePhone(request.getFirst("mobilePhone"));
		v.setPassword(request.getFirst("password"));
		v.setWorkPhone(request.getFirst("workPhone"));			

		v = repo.save(v);
		
		return Response.ok(v).build();		
	}
	
	@DELETE
	@Path("/{userName}")
	public Response updateVolunteer(@PathParam("userName") String userName) {
		repo.delete(userName);
		return Response.noContent().build();		
	}
	
}
