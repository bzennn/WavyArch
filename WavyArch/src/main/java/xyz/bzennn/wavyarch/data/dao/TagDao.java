package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.AudioTag;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link AudioTag}
 *
 * @author bzennn
 * @version 1.0
 */
public interface TagDao {
	void save(AudioTag tag) throws DaoLayerException;
	AudioTag findByName(String name) throws DaoLayerException;
	boolean isTagExists(String name) throws DaoLayerException;
	void update(AudioTag tag) throws DaoLayerException;
	void delete(AudioTag tag) throws DaoLayerException;
	void refresh(AudioTag tag) throws DaoLayerException;
	List<AudioTag> findAll() throws DaoLayerException;
	List<AudioTag> search(String request) throws DaoLayerException;
}
