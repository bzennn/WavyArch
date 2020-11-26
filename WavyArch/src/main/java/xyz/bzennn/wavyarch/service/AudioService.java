package xyz.bzennn.wavyarch.service;

import java.util.List;
import java.util.Set;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountAudio;
import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.AudioEditForm;
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
	void deleteFromAccount(Audio audio, Account account) throws ServiceLayerException;
	void addAudioToAccount(Audio audio, Account account) throws ServiceLayerException;
	boolean isAudioExistsOnAccount(Audio audio, Account account) throws ServiceLayerException;
	Audio buildAudioFromUploadForm(AudioUploadForm form) throws ServiceLayerException;
	Audio buildAudioFromEditForm(AudioEditForm form) throws ServiceLayerException;
	Set<AccountAudio> loadAudios(Account account) throws ServiceLayerException;
	Set<AccountPlaylist> loadPlaylists(Account account) throws ServiceLayerException;
	List<Audio> findByAccount(Account account) throws ServiceLayerException;
}
