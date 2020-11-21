package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * DAO interface for {@link Author} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AuthorDao {
	void save(Author author) throws ServiceLayerException;
	void update(Author author) throws ServiceLayerException;
	void refresh(Author author) throws ServiceLayerException;
	List<Author> findByAudioId(Long audioId) throws ServiceLayerException;
	Author findByAudioAndAudioMaker(Audio audio, AudioMaker audioMaker) throws ServiceLayerException;
	boolean isAuthorExists(Audio audio, AudioMaker audioMaker) throws ServiceLayerException;
}
