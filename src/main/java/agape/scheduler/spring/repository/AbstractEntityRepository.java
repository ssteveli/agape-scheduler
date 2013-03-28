package agape.scheduler.spring.repository;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public abstract class AbstractEntityRepository<T, ID extends Serializable> implements CrudRepository<T, ID> {

	@Autowired
	private DatastoreService ds;

	@Override
	public <S extends T> S save(S entity) {
		ds.put(toEntity(entity));
		return entity;
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		ArrayList<S> l = new ArrayList<S>();
		for (S entity : entities) {
			save(entity);
			l.add(entity);
		}
		
		return l;
	}

	@Override
	public T findOne(ID id) {
		try {
			Entity db = ds.get(getKey(id));
			return fromEntity(db);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	@Override
	public boolean exists(ID id) {
		return findOne(id) != null;
	}

	@Override
	public Iterable<T> findAll() {
		Query q = new Query(getType());
		PreparedQuery pq = ds.prepare(q);
		
		ArrayList<T> results = new ArrayList<T>();
		for (Entity e : pq.asIterable()) {
			results.add(fromEntity(e));
		}
		
		return results;
	}

	@Override
	public Iterable<T> findAll(Iterable<ID> ids) {
		ArrayList<T> results = new ArrayList<T>();
		for (ID id : ids) {
			results.add(findOne(id));
		}
		
		return results;
	}

	@Override
	public long count() {
		Query q = new Query(getType());
		PreparedQuery pq = ds.prepare(q);
		return pq.countEntities(FetchOptions.Builder.withDefaults());
	}

	@Override
	public void delete(ID id) {
		ds.delete(getKey(id));
	}

	@Override
	public void delete(T entity) {
		ds.delete(getKey(entity));
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		for (T e : entities) {
			delete(e);
		}
	}

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	protected abstract Entity toEntity(T e);
	protected abstract T fromEntity(Entity e);
	protected abstract String getType();
	protected abstract Key getKey(ID id);
	protected abstract Key getKey(T e);
}
