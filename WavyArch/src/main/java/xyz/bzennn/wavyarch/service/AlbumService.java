package xyz.bzennn.wavyarch.service;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.AlbumEditForm;

/**
 * Service class for {@link AudioAlbum} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AlbumService {
	void save(AudioAlbum album) throws ServiceLayerException;
	void update(AudioAlbum album) throws ServiceLayerException;
	void delete(AudioAlbum album) throws ServiceLayerException;
	void refresh(AudioAlbum album) throws ServiceLayerException;
	AudioAlbum findByAudioName(String audioName) throws ServiceLayerException;
	AudioAlbum findByName(String albumName) throws ServiceLayerException;
	List<AudioAlbum> findByAccount(Account account) throws ServiceLayerException;
	AudioAlbum buildAlbumFromEditForm(AlbumEditForm form) throws ServiceLayerException;
}
