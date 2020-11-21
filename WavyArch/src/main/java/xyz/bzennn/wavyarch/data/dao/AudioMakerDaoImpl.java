package xyz.bzennn.wavyarch.data.dao;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.exception.DaoLayerException;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * Implementation for {@link AudioMaker}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AudioMakerDaoImpl extends BaseDaoImpl<AudioMaker> implements AudioMakerDao {

	@Override
	public AudioMaker findByName(String name) throws ServiceLayerException {
		return findByAttribute(AudioMaker.class, "name", name);
	}

	@Override
	public boolean isAudioMakerExists(String name) throws ServiceLayerException {
		try {
			AudioMaker audioMaker = findByName(name);
			return audioMaker != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
