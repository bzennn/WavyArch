package xyz.bzennn.wavyarch.data.dao;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation for {@link AlbumDao}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AlbumDaoImpl extends BaseDaoImpl<AudioAlbum> implements AlbumDao {

	@Override
	public AudioAlbum findByName(String name) throws DaoLayerException {
		return findByAttribute(AudioAlbum.class, "name", name);
	}

	@Override
	public boolean isAlbumExists(String name) throws DaoLayerException {
		try {
			AudioAlbum album = findByName(name);
			return album != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
