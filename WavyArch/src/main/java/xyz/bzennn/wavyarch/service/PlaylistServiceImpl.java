package xyz.bzennn.wavyarch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountDao;
import xyz.bzennn.wavyarch.data.dao.AccountPlaylistDao;
import xyz.bzennn.wavyarch.data.dao.AudioDao;
import xyz.bzennn.wavyarch.data.dao.PlaylistAudioDao;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.PlaylistAudio;
import xyz.bzennn.wavyarch.data.model.PlaylistAudioPK;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.PlaylistCreateForm;
import xyz.bzennn.wavyarch.form.PlaylistEditForm;

/**
 * Implementation of {@link PlaylistService}
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

	@Autowired
	AccountPlaylistDao accountPlaylistDao;

	@Autowired
	PlaylistAudioDao playlistAudioDao;

	@Autowired
	AudioDao audioDao;

	@Autowired
	AccountDao accountDao;

	@Override
	public void save(AccountPlaylist playlist) throws ServiceLayerException {
		try {
			accountPlaylistDao.save(playlist);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to save playlist!", e);
		}
	}

	@Override
	public void update(AccountPlaylist playlist) throws ServiceLayerException {
		try {
			accountPlaylistDao.update(playlist);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to update playlist!", e);
		}
	}

	@Override
	public void delete(AccountPlaylist playlist) throws ServiceLayerException {
		try {
			accountPlaylistDao.delete(playlist);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to delete playlist!", e);
		}

	}

	@Override
	public void refresh(AccountPlaylist playlist) throws ServiceLayerException {
		try {
			accountPlaylistDao.refresh(playlist);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to refresh playlist!", e);
		}
	}

	@Override
	public void createPlaylistOnAccount(String playlistName, Account account) throws ServiceLayerException {
		try {
			AccountPlaylist playlist = accountPlaylistDao.findByNameAndAccount(playlistName, account);

			if (playlist == null) {
				AccountPlaylist accountPlaylist = new AccountPlaylist();
				accountPlaylist.setAccount(account);
				accountPlaylist.setName(playlistName);
				save(accountPlaylist);
				playlist = accountPlaylistDao.findByNameAndAccount(playlistName, account);
			}
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find or create playlist!", e);
		}
	}

	@Override
	public AccountPlaylist findByNameAndLogin(String playlistName, String login) throws ServiceLayerException {
		try {
			Account account = accountDao.findByLogin(login);

			if (account != null) {
				return accountPlaylistDao.findByNameAndAccount(playlistName, account);
			}

			return null;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find playlists by name and login!", e);
		}
	}

	@Override
	public List<AccountPlaylist> findByAccount(Account account) throws ServiceLayerException {
		try {
			return accountPlaylistDao.findByAccount(account);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find playlists by account!", e);
		}
	}

	@Override
	public List<PlaylistAudio> findAudiosByPlaylistNameAndAccount(String playlistName, Account account)
			throws ServiceLayerException {
		try {
			AccountPlaylist playlist = accountPlaylistDao.findByNameAndAccount(playlistName, account);
			if (playlist != null) {
				return new ArrayList<>(playlist.getAudios());
			}

			return Collections.emptyList();
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find audios by playlist name!", e);
		}
	}

	@Override
	public void addAudioToPlaylist(Audio audio, AccountPlaylist playlist) throws ServiceLayerException {
		try {
			PlaylistAudio playlistAudio = playlistAudioDao.findByAudioAndPlaylist(audio, playlist);
			if (playlistAudio == null) {
				playlistAudio = new PlaylistAudio();
				playlistAudio.setAudio(audio);
				playlistAudio.setPlaylist(playlist);

				PlaylistAudioPK pk = new PlaylistAudioPK();
				pk.setAudioId(audio.getId());
				pk.setPlaylistId(playlist.getId());

				playlistAudio.setId(pk);

				playlistAudioDao.save(playlistAudio);
			}
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to add audio to playlist!", e);
		}
	}

	@Override
	public void removeAudioFromPlaylist(Audio audio, AccountPlaylist playlist) throws ServiceLayerException {
		try {
			PlaylistAudio playlistAudio = playlistAudioDao.findByAudioAndPlaylist(audio, playlist);
			if (playlistAudio != null) {
				playlistAudioDao.delete(playlistAudio);
			}
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to remove audio from playlist!", e);
		}
	}

	@Override
	public AccountPlaylist buildPlaylistFromCreateForm(PlaylistCreateForm form) throws ServiceLayerException {
		try {
			Account account = accountDao.findByLogin(form.getLogin());
			AccountPlaylist playlist = new AccountPlaylist();
			
			playlist.setName(form.getName());
			
			String imagePath = form.getImagePath();
			if (imagePath != null && !imagePath.isEmpty()) {
				playlist.setImagePath(imagePath);
			}
			
			playlist.setAccount(account);
			
			return playlist;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to remove audio from playlist!", e);
		}
	}
	
	@Override
	public AccountPlaylist buildPlaylistFromEditForm(PlaylistEditForm form) throws ServiceLayerException {
		try {
			Account account = accountDao.findByLogin(form.getLogin());
			AccountPlaylist playlist = accountPlaylistDao.findByNameAndAccount(form.getName(), account);
			
			if (playlist == null) {
				playlist = new AccountPlaylist();
			}
			
			playlist.setName(form.getName());
			
			String imagePath = form.getImagePath();
			if (imagePath != null && !imagePath.isEmpty()) {
				playlist.setImagePath(imagePath);
			}
			
			playlist.setAccount(account);
			
			return playlist;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to remove audio from playlist!", e);
		}
	}

}
