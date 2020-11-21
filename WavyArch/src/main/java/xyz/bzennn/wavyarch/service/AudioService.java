package xyz.bzennn.wavyarch.service;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.AudioUploadForm;

/**
 * Service class for {@link Audio}
 *
 * @author bzennn
 * @version 1.0
 */
public interface AudioService {
	void save(Audio audio) throws ServiceLayerException;
	Audio findByName(String name) throws ServiceLayerException;
	boolean isAudioExists(String name) throws ServiceLayerException;
	void update(Audio audio) throws ServiceLayerException;
	void refresh(Audio audio) throws ServiceLayerException;
	void delete(Audio audio) throws ServiceLayerException;
	Audio buildAudioFromUploadForm(AudioUploadForm form) throws ServiceLayerException;
}
