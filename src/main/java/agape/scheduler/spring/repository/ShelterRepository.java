package agape.scheduler.spring.repository;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import agape.scheduler.domain.wire.ShelterWire;

@Repository
public class ShelterRepository extends AbstractEntityRepository<ShelterWire, String> {

	@Override
	protected Entity toEntity(ShelterWire s) {
		Entity e = new Entity(getType(), s.getName());
		e.setProperty("name", s.getName());
		e.setProperty("createdTs", s.getCreatedTs());
		e.setProperty("lastUpdatedTs", s.getLastUpdatedTs());
		
		return e;
	}

	@Override
	protected ShelterWire fromEntity(Entity e) {
		ShelterWire s = new ShelterWire();
		s.setName((String)e.getProperty("name"));
		s.setLastUpdatedTs((Date)e.getProperty("lastUpdatedTs"));
		s.setCreatedTs((Date)e.getProperty("createdTs"));
		
		return s;
	}

	@Override
	protected String getType() {
		return "Shelter";
	}

	@Override
	protected Key getKey(String id) {
		return KeyFactory.createKey(getType(), id);
	}

	@Override
	protected Key getKey(ShelterWire s) {
		return KeyFactory.createKey(getType(), s.getName());
	}

}
