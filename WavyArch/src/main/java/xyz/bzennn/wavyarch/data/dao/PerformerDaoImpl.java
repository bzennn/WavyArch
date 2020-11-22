package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.Performer;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation for {@link Performer}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class PerformerDaoImpl extends BaseDaoImpl<Performer> implements PerformerDao {

	@Override
	public List<Performer> findByAudioId(Long audioId) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Performer> list = session.createQuery("from Performer where audio_id=:audio_id").setParameter("audio_id", audioId).getResultList();
			transaction.commit();
			session.close();

			return list;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find performers!", e);
		}
	}

	@Override
	public Performer findByAudioAndAudioMaker(Audio audio, AudioMaker audioMaker) throws DaoLayerException {
		Long audioId = audio.getId();
		Long audioMakerId = audioMaker.getId();
		
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query = "from Performer where audio_id=:audio_id and audio_maker_id=:audio_maker_id";
			Performer performer = (Performer) session.createQuery(query).setParameter("audio_id", audioId).setParameter("audio_maker_id", audioMakerId).uniqueResult();
			transaction.commit();
			session.close();

			return performer;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find performers!", e);
		}
	}

	@Override
	public boolean isPerformerExists(Audio audio, AudioMaker audioMaker) throws DaoLayerException {
		try {
			Performer performer = findByAudioAndAudioMaker(audio, audioMaker);
			return performer != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
