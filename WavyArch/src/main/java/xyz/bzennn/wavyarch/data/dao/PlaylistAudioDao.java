package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.PlaylistAudio;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link PlaylistAudio} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface PlaylistAudioDao {
	void save(PlaylistAudio playlistAudio) throws DaoLayerException;
	void update(PlaylistAudio playlistAudio) throws DaoLayerException;
	void refresh(PlaylistAudio playlistAudio) throws DaoLayerException;
	void delete(PlaylistAudio playlistAudio) throws DaoLayerException;
	List<PlaylistAudio> findByAudioId(Long audioId) throws DaoLayerException;
	PlaylistAudio findByAudioAndPlaylist(Audio audio, AccountPlaylist accountPlaylist) throws DaoLayerException;
	boolean isPlaylistAudioExists(Audio audio, AccountPlaylist accountPlaylist) throws DaoLayerException;
}
