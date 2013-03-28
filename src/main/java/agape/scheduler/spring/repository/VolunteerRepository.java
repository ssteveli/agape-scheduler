package agape.scheduler.spring.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import agape.scheduler.domain.wire.VolunteerWire;

@Repository
public class VolunteerRepository implements CrudRepository<VolunteerWire, String> {

	@Autowired
	private DatastoreService ds;
	
	@Override
	public <S extends VolunteerWire> S save(S entity) {
		ds.put(toEntity(entity));
		return entity;
	}

	@Override
	public <S extends VolunteerWire> Iterable<S> save(Iterable<S> entities) {
		ArrayList<S> l = new ArrayList<S>();
		for (S entity : entities) {
			save(entity);
			l.add(entity);
		}
		
		return l;
	}

	@Override
	public VolunteerWire findOne(String id) {
		try {
			Entity db = ds.get(KeyFactory.createKey("Volunteer", id));
			return fromEntity(db);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	@Override
	public boolean exists(String id) {
		return findOne(id) != null;
	}

	@Override
	public Iterable<VolunteerWire> findAll() {
		Query q = new Query("Volunteer");
		PreparedQuery pq = ds.prepare(q);
		
		ArrayList<VolunteerWire> results = new ArrayList<VolunteerWire>();
		for (Entity e : pq.asIterable()) {
			results.add(fromEntity(e));
		}
		
		return results;
	}

	@Override
	public Iterable<VolunteerWire> findAll(Iterable<String> ids) {
		ArrayList<VolunteerWire> results = new ArrayList<VolunteerWire>();
		for (String id : ids) {
			results.add(findOne(id));
		}
		
		return results;
	}

	@Override
	public long count() {
		Query q = new Query("Volunteer");
		PreparedQuery pq = ds.prepare(q);
		return pq.countEntities(FetchOptions.Builder.withDefaults());
	}

	@Override
	public void delete(String id) {
		ds.delete(KeyFactory.createKey("Volunteer", id));
	}

	@Override
	public void delete(VolunteerWire entity) {
		delete(entity.getUsername());
	}

	@Override
	public void delete(Iterable<? extends VolunteerWire> entities) {
		for (VolunteerWire v : entities) {
			delete(v);
		}
	}

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	private VolunteerWire fromEntity(Entity e) {
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

	private Entity toEntity(VolunteerWire v) {
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

}
