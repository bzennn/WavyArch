package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.Performer;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * DAO interface for {@link Performer} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface PerformerDao {
	void save(Performer performer) throws ServiceLayerException;
	void update(Performer performer) throws ServiceLayerException;
	void refresh(Performer performer) throws ServiceLayerException;
	List<Performer> findByAudioId(Long audioId) throws ServiceLayerException;
	Performer findByAudioAndAudioMaker(Audio audio, AudioMaker audioMaker) throws ServiceLayerException;
	boolean isPerformerExists(Audio audio, AudioMaker audioMaker) throws ServiceLayerException;
}
