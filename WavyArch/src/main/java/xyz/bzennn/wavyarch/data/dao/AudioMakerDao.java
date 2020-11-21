package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * DAO interface for {@link AudioMaker} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AudioMakerDao {
	void save(AudioMaker audioMaker) throws ServiceLayerException;
	AudioMaker findByName(String name) throws ServiceLayerException;
	boolean isAudioMakerExists(String name) throws ServiceLayerException;
	void update(AudioMaker audioMaker) throws ServiceLayerException;
	void refresh(AudioMaker audioMaker) throws ServiceLayerException;
}
