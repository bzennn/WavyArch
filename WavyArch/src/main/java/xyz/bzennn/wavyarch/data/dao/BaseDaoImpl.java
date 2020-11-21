package xyz.bzennn.wavyarch.data.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation of {@link BaseDao} 
 *
 * @author bzennn
 * @version 1.0
 * @param <T>
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(T object) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DaoLayerException("Failed to persist object!", e);
		}
	}

	@Override
	public void update(T object) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(object);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DaoLayerException("Failed to update object!", e);
		}
	}

	@Override
	public void delete(T object) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.delete(object);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DaoLayerException("Failed to delete object!", e);
		}
	}

	@Override
	public void refresh(T object) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			session.refresh(object);
			session.close();
		} catch (Exception e) {
			throw new DaoLayerException("Failed to refresh object!", e);
		}
	}

	@Override
	public T findByAttribute(Class<T> entityClass, String attributeName, Object attributeValue) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query = "from " + entityClass.getSimpleName() + " where " + attributeName + "=:attributeValue";
			T object = entityClass.cast(session.createQuery(query).setParameter("attributeValue", attributeValue.toString())
					.uniqueResult());
			transaction.commit();
			session.close();

			return object;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find account!", e);
		}
	}

}
