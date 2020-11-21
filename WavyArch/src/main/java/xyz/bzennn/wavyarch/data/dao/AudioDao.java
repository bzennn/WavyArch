package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link Audio} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AudioDao {
	void save(Audio audio) throws DaoLayerException;
	Audio findByName(String name) throws DaoLayerException;
	boolean isAudioExists(String name) throws DaoLayerException;
	void update(Audio audio) throws DaoLayerException;
	void delete(Audio audio) throws DaoLayerException;
	void refresh(Audio audio) throws DaoLayerException;
}
