package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link AudioGenre} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface GenreDao {
	void save(AudioGenre genre) throws DaoLayerException;
	AudioGenre findByName(String name) throws DaoLayerException;
	boolean isGenreExists(String name) throws DaoLayerException;
	void update(AudioGenre genre) throws DaoLayerException;
	void delete(AudioGenre genre) throws DaoLayerException;
	void refresh(AudioGenre genre) throws DaoLayerException;
	List<AudioGenre> findAll() throws DaoLayerException;
}
