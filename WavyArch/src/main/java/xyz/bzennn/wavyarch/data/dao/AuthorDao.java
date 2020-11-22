package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link Author} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AuthorDao {
	void save(Author author) throws DaoLayerException;
	void update(Author author) throws DaoLayerException;
	void refresh(Author author) throws DaoLayerException;
	void delete(Author author) throws DaoLayerException;
	List<Author> findByAudioId(Long audioId) throws DaoLayerException;
	Author findByAudioAndAudioMaker(Audio audio, AudioMaker audioMaker) throws DaoLayerException;
	boolean isAuthorExists(Audio audio, AudioMaker audioMaker) throws DaoLayerException;
}
