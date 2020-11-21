package xyz.bzennn.wavyarch.data.dao;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.exception.DaoLayerException;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * Implementation for {@link GenreDao} 
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class GenreDaoImpl extends BaseDaoImpl<AudioGenre> implements GenreDao {

	@Override
	public AudioGenre findByName(String name) throws ServiceLayerException {
		return findByAttribute(AudioGenre.class, "name", name);
	}

	@Override
	public boolean isGenreExists(String name) throws ServiceLayerException {
		try {
			AudioGenre genre = findByName(name);
			return genre != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
