package agape.scheduler.spring.repository;

import java.util.Date;

import org.springframework.stereotype.Repository;

import agape.scheduler.domain.wire.VolunteerWire;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Repository
public class VolunteerRepository extends AbstractEntityRepository<VolunteerWire, String> {

	@Override
	protected VolunteerWire fromEntity(Entity e) {
		VolunteerWire v = new VolunteerWire();
		v.setCreatedTs((Date)e.getProperty("createdTs"));
		v.setEmailAddress((String)e.getProperty("email"));
		v.setFirstName((String)e.getProperty("firstName"));
		v.setHomePhone((String)e.getProperty("homePhone"));
		v.setLastName((String)e.getProperty("lastName"));
		v.setLastUpdatedTs((Date)e.getProperty("lastUpdatedTs"));
		v.setMobilePhone((String)e.getProperty("mobilePhone"));
		v.setPassword((String)e.getProperty("password"));
		v.setUsername((String)e.getProperty("userName"));
		v.setWorkPhone((String)e.getProperty("workPhone"));
		return v;
	}

	@Override
	protected Entity toEntity(VolunteerWire v) {
		Entity e = new Entity("Volunteer", v.getUsername());
		e.setProperty("firstName", v.getFirstName());
		e.setProperty("lastName", v.getLastName());
		e.setProperty("homePhone", v.getHomePhone());
		e.setProperty("workPhone", v.getWorkPhone());
		e.setProperty("userName", v.getUsername());
		e.setProperty("email", v.getEmailAddress());
		e.setProperty("mobilePhone", v.getMobilePhone());
		e.setProperty("createdTs", v.getCreatedTs());
		e.setProperty("lastUpdatedTs", v.getLastUpdatedTs());
		
		return e;
	}

	@Override
	protected String getType() {
		return "Volunteer";
	}

	@Override
	protected Key getKey(String id) {
		return KeyFactory.createKey(getType(), id);
	}

	@Override
	protected Key getKey(VolunteerWire e) {
		return KeyFactory.createKey(getType(), e.getUsername());
	}
}
