package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link AudioAlbum} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AlbumDao {
	void save(AudioAlbum album) throws DaoLayerException;
	AudioAlbum findByName(String name) throws DaoLayerException;
	boolean isAlbumExists(String name) throws DaoLayerException;
	void update(AudioAlbum album) throws DaoLayerException;
	void refresh(AudioAlbum album) throws DaoLayerException;
	void delete(AudioAlbum album) throws DaoLayerException;
	List<AudioAlbum> findAll() throws DaoLayerException;
	List<AudioAlbum> search(String request) throws DaoLayerException;
}
