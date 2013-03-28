package agape.scheduler.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.JResponse;

import agape.scheduler.domain.wire.ShelterWire;
import agape.scheduler.spring.repository.ShelterRepository;

@Path("/v1/shelters")
@Component
public class Shelter {

	@Autowired
	private ShelterRepository repo;
	
	@GET
	public JResponse<Iterable<ShelterWire>> getShelters() {
		return JResponse.ok(repo.findAll()).build();
	}
}
