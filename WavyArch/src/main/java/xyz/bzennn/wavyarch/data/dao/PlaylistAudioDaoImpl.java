package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.PlaylistAudio;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation of {@link PlaylistAudioDao}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class PlaylistAudioDaoImpl extends BaseDaoImpl<PlaylistAudio> implements PlaylistAudioDao {

	@Override
	public List<PlaylistAudio> findByAudioId(Long audioId) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<PlaylistAudio> list = session.createQuery("from PlaylistAudio where audio_id=:audio_id").setParameter("audio_id", audioId).getResultList();
			transaction.commit();
			session.close();

			return list;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find playlist audios!", e);
		}
	}

	@Override
	public PlaylistAudio findByAudioAndPlaylist(Audio audio, AccountPlaylist accountPlaylist) throws DaoLayerException {
		Long audioId = audio.getId();
		Long playlistId = accountPlaylist.getId();
		
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query = "from PlaylistAudio where audio_id=:audio_id and playlist_id=:playlist_id";
			PlaylistAudio playlistAudio = (PlaylistAudio) session.createQuery(query).setParameter("audio_id", audioId).setParameter("playlist_id", playlistId).uniqueResult();
			transaction.commit();
			session.close();

			return playlistAudio;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find playlist audio!", e);
		}
	}

	@Override
	public boolean isPlaylistAudioExists(Audio audio, AccountPlaylist accountPlaylist) throws DaoLayerException {
		try {
			PlaylistAudio playlistAudio = findByAudioAndPlaylist(audio, accountPlaylist);
			return playlistAudio != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
