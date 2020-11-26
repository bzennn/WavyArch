package xyz.bzennn.wavyarch.service;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.PerformerEditForm;

/**
 * Service class for {@link AudioMaker} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface PerformerService {
	void save(AudioMaker audioMaker) throws ServiceLayerException;
	void update(AudioMaker audioMaker) throws ServiceLayerException;
	void delete(AudioMaker audioMaker) throws ServiceLayerException;
	void refresh(AudioMaker audioMaker) throws ServiceLayerException;
	AudioMaker findByName(String audioMakerName) throws ServiceLayerException;
	List<AudioMaker> findByAudioName(String audioName) throws ServiceLayerException;
	List<Audio> findAudiosByName(String audioMakerName) throws ServiceLayerException;
	List<AudioMaker> findByAccount(Account account) throws ServiceLayerException;
	AudioMaker buildAudioMakerFromEditForm(PerformerEditForm form) throws ServiceLayerException;
}
