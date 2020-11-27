package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link AudioMaker} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AudioMakerDao {
	void save(AudioMaker audioMaker) throws DaoLayerException;
	AudioMaker findByName(String name) throws DaoLayerException;
	boolean isAudioMakerExists(String name) throws DaoLayerException;
	void update(AudioMaker audioMaker) throws DaoLayerException;
	void delete(AudioMaker audioMaker) throws DaoLayerException;
	void refresh(AudioMaker audioMaker) throws DaoLayerException;
	List<AudioMaker> findAll() throws DaoLayerException;
}
