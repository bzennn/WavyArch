package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation for {@link GenreDao} 
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class GenreDaoImpl extends BaseDaoImpl<AudioGenre> implements GenreDao {

	@Override
	public AudioGenre findByName(String name) throws DaoLayerException {
		return findByAttribute(AudioGenre.class, "name", name);
	}

	@Override
	public boolean isGenreExists(String name) throws DaoLayerException {
		try {
			AudioGenre genre = findByName(name);
			return genre != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@Override
	public List<AudioGenre> findAll() throws DaoLayerException {
		try {
			return findAll(AudioGenre.class);
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@Override
	public List<AudioGenre> search(String request) throws DaoLayerException {
		try {
			return search(AudioGenre.class, "name", request);
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
