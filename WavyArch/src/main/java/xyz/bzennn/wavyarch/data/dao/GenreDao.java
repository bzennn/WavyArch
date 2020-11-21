package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * DAO interface for {@link AudioGenre} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface GenreDao {
	void save(AudioGenre genre) throws ServiceLayerException;
	AudioGenre findByName(String name) throws ServiceLayerException;
	boolean isGenreExists(String name) throws ServiceLayerException;
	void update(AudioGenre genre) throws ServiceLayerException;
	void refresh(AudioGenre genre) throws ServiceLayerException;
}
