package xyz.bzennn.wavyarch.service;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * Service for audio searching
 *
 * @author bzennn
 * @version 1.0
 */
public interface SearchService {
	List<Audio> searchAll(String request) throws ServiceLayerException;
	List<Audio> searchByAudioName(String request) throws ServiceLayerException;
	List<Audio> searchByGenreName(String request) throws ServiceLayerException;
	List<Audio> searchByAlbumName(String request) throws ServiceLayerException;
	List<Audio> searchByAudioMakerName(String request) throws ServiceLayerException;
	List<Audio> searchByTagName(String request) throws ServiceLayerException;
}
