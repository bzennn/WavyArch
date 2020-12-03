package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation for {@link AudioDao} 
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AudioDaoImpl extends BaseDaoImpl<Audio> implements AudioDao {

	@Override
	public Audio findByName(String name) throws DaoLayerException {
		return findByAttribute(Audio.class, "name", name);
	}

	@Override
	public boolean isAudioExists(String name) throws DaoLayerException {
		try {
			Audio audio = findByName(name);
			return audio != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@Override
	public List<Audio> search(String request) throws DaoLayerException {
		try {
			return search(Audio.class, "name", request);
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Audio> recommendations(String audioName, Integer limit) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query = "select * from music_library.buildRecommendationsByAudio(?, ?)";
			List<?> list = (List<?>) session.createSQLQuery(query).setParameter(1, audioName).setParameter(2, limit).addEntity(Audio.class).list();
			transaction.commit();
			session.close();

			return (List<Audio> )list;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
