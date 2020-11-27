package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AudioTag;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation for {@link TagDao}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class TagDaoImpl extends BaseDaoImpl<AudioTag> implements TagDao {

	@Override
	public AudioTag findByName(String name) throws DaoLayerException {
		return findByAttribute(AudioTag.class, "name", name);
	}

	@Override
	public boolean isTagExists(String name) throws DaoLayerException {
		try {
			AudioTag tag = findByName(name);
			return tag != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@Override
	public List<AudioTag> findAll() throws DaoLayerException {
		try {
			return findAll(AudioTag.class);
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
