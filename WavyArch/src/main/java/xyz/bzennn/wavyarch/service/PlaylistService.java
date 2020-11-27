package xyz.bzennn.wavyarch.service;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.PlaylistAudio;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.PlaylistCreateForm;
import xyz.bzennn.wavyarch.form.PlaylistEditForm;

/**
 * Service class for {@link AccountPlaylist} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface PlaylistService {
	void save(AccountPlaylist playlist) throws ServiceLayerException;
	void update(AccountPlaylist playlist) throws ServiceLayerException;
	void delete(AccountPlaylist playlist) throws ServiceLayerException;
	void refresh(AccountPlaylist playlist) throws ServiceLayerException;
	void createPlaylistOnAccount(String playlistName, Account account) throws ServiceLayerException;
	AccountPlaylist findByNameAndLogin(String playlistName, String login) throws ServiceLayerException;
	List<AccountPlaylist> findByAccount(Account account) throws ServiceLayerException;
	List<PlaylistAudio> findAudiosByPlaylistNameAndAccount(String playlistName, Account account) throws ServiceLayerException;
	void addAudioToPlaylist(Audio audio, AccountPlaylist playlist) throws ServiceLayerException;
	void removeAudioFromPlaylist(Audio audio, AccountPlaylist playlist) throws ServiceLayerException;
	AccountPlaylist buildPlaylistFromCreateForm(PlaylistCreateForm form) throws ServiceLayerException;
	AccountPlaylist buildPlaylistFromEditForm(PlaylistEditForm form) throws ServiceLayerException;
}
