package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation for {@link AudioMaker}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AudioMakerDaoImpl extends BaseDaoImpl<AudioMaker> implements AudioMakerDao {

	@Override
	public AudioMaker findByName(String name) throws DaoLayerException {
		return findByAttribute(AudioMaker.class, "name", name);
	}

	@Override
	public boolean isAudioMakerExists(String name) throws DaoLayerException {
		try {
			AudioMaker audioMaker = findByName(name);
			return audioMaker != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@Override
	public List<AudioMaker> findAll() throws DaoLayerException {
		try {
			return findAll(AudioMaker.class);
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@Override
	public List<AudioMaker> search(String request) throws DaoLayerException {
		try {
			return search(AudioMaker.class, "name", request);
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
