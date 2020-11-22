package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation for {@link Author} 
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AuthorDaoImpl extends BaseDaoImpl<Author> implements AuthorDao {

	@Override
	public List<Author> findByAudioId(Long audioId) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Author> list = session.createQuery("from Author where audio_id=:audio_id").setParameter("audio_id", audioId).getResultList();
			transaction.commit();
			session.close();

			return list;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find authors!", e);
		}
	}

	@Override
	public Author findByAudioAndAudioMaker(Audio audio, AudioMaker audioMaker) throws DaoLayerException {
		Long audioId = audio.getId();
		Long audioMakerId = audioMaker.getId();
		
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query = "from Author where audio_id=:audio_id and audio_maker_id=:audio_maker_id";
			Author author = (Author) session.createQuery(query).setParameter("audio_id", audioId).setParameter("audio_maker_id", audioMakerId).uniqueResult();
			transaction.commit();
			session.close();

			return author;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find performers!", e);
		}
	}

	@Override
	public boolean isAuthorExists(Audio audio, AudioMaker audioMaker) throws DaoLayerException {
		try {
			Author author = findByAudioAndAudioMaker(audio, audioMaker);
			return author != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
