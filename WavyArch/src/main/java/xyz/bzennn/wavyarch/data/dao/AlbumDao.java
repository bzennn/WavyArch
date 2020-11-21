package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * DAO interface for {@link AudioAlbum} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AlbumDao {
	void save(AudioAlbum album) throws ServiceLayerException;
	AudioAlbum findByName(String name) throws ServiceLayerException;
	boolean isAlbumExists(String name) throws ServiceLayerException;
	void update(AudioAlbum album) throws ServiceLayerException;
	void refresh(AudioAlbum album) throws ServiceLayerException;
}
