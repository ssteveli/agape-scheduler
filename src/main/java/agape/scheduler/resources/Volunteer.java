package agape.scheduler.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.sun.jersey.api.JResponse;

import agape.scheduler.domain.wire.VolunteerWire;

@Path("/v1/volunteers")
@Component
public class Volunteer extends AbstractResource {
	
	@Autowired
	private DatastoreService ds;
	
	@GET
	public JResponse<List<VolunteerWire>> getVolunteers() {
		Query q = new Query("Volunteer");
		PreparedQuery pq = ds.prepare(q);
		
		List<VolunteerWire> results = new ArrayList<VolunteerWire>();
		for (Entity e : pq.asIterable()) {
			results.add(VolunteerWire.fromEntity(e));
		}
		
		return JResponse.ok(results).build();
	}
	
	@GET
	@Path("/{userName}")
	public Response getVolunteer(@PathParam("userName") String userName) {
		try {
			Entity db = ds.get(KeyFactory.createKey("Volunteer", userName));
			
			return Response.ok(VolunteerWire.fromEntity(db)).build();
		} catch (EntityNotFoundException e) {
			return Response
					.status(Status.NOT_FOUND)
					.entity(createError("volunteer not found"))
					.build();
		}
	}
	
	@POST
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createVolunteer(@PathParam("userName") String userName, MultivaluedMap<String, String> request) {
		Entity db = new Entity("Volunteer", userName);
		db.setProperty("createdTs", new Date());
		db.setProperty("userName", userName);
		db.setProperty("email", request.getFirst("email"));
		db.setProperty("mobilePhone", request.getFirst("mobilePhone"));
		
		ds.put(db);

		return Response.ok(VolunteerWire.fromEntity(db)).build();
	}
	
	@PUT
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateVolunteer(@PathParam("userName") String userName, MultivaluedMap<String, String> request) {
		Entity db = null;
		try {
			db = ds.get(KeyFactory.createKey("Volunteer", userName));
		} catch (EntityNotFoundException e) {
			db = new Entity("Volunteer", userName);
			db.setProperty("createdTs", new Date());
			db.setProperty("userName", userName);
		}
		
		db.setProperty("email", request.getFirst("email"));
		db.setProperty("mobilePhone", request.getFirst("mobilePhone"));
		
		ds.put(db);
		
		return Response.ok(VolunteerWire.fromEntity(db)).build();		
	}
	
	@DELETE
	@Path("/{userName}")
	public Response updateVolunteer(@PathParam("userName") String userName) {
		ds.delete(KeyFactory.createKey("Volunteer", userName));
		return Response.noContent().build();		
	}
	
}
