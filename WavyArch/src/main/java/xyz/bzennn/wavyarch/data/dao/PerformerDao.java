package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.Performer;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link Performer} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface PerformerDao {
	void save(Performer performer) throws DaoLayerException;
	void update(Performer performer) throws DaoLayerException;
	void refresh(Performer performer) throws DaoLayerException;
	void delete(Performer performer) throws DaoLayerException;
	List<Performer> findByAudioId(Long audioId) throws DaoLayerException;
	Performer findByAudioAndAudioMaker(Audio audio, AudioMaker audioMaker) throws DaoLayerException;
	boolean isPerformerExists(Audio audio, AudioMaker audioMaker) throws DaoLayerException;
}
