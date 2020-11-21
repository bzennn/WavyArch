package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.data.model.AudioTag;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * DAO interface for {@link AudioTag}
 *
 * @author bzennn
 * @version 1.0
 */
public interface TagDao {
	void save(AudioTag tag) throws ServiceLayerException;
	AudioTag findByName(String name) throws ServiceLayerException;
	boolean isTagExists(String name) throws ServiceLayerException;
	void update(AudioTag tag) throws ServiceLayerException;
	void refresh(AudioTag tag) throws ServiceLayerException;
}
