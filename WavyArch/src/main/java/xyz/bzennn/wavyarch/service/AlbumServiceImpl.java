package xyz.bzennn.wavyarch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountAudioDao;
import xyz.bzennn.wavyarch.data.dao.AlbumDao;
import xyz.bzennn.wavyarch.data.dao.AudioDao;
import xyz.bzennn.wavyarch.data.dao.AudioMakerDao;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountAudio;
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.AlbumEditForm;

/**
 * Implementation of {@link AlbumService} 
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	AlbumDao albumDao;
	
	@Autowired
	AudioDao audioDao;
	
	@Autowired
	AccountAudioDao accountAudioDao;
	
	@Autowired
	AudioMakerDao audioMakerDao;
	
	@Override
	public void save(AudioAlbum album) throws ServiceLayerException {
		try {
			albumDao.save(album);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to save album!", e);
		}
	}

	@Override
	public void update(AudioAlbum album) throws ServiceLayerException {
		try {
			albumDao.update(album);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to update album!", e);
		}
	}

	@Override
	public void delete(AudioAlbum album) throws ServiceLayerException {
		try {
			
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to delete album!", e);
		}
	}

	@Override
	public void refresh(AudioAlbum album) throws ServiceLayerException {
		try {
			albumDao.refresh(album);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to refresh album!", e);
		}
	}

	@Override
	public AudioAlbum findByAudioName(String audioName) throws ServiceLayerException {
		try {
			return audioDao.findByName(audioName).getAlbum();
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find album by audio!", e);
		}
	}

	@Override
	public AudioAlbum findByName(String albumName) throws ServiceLayerException {
		try {
			return albumDao.findByName(albumName);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find album by name!", e);
		}
	}
	
	@Override
	public List<AudioAlbum> findByAccount(Account account) throws ServiceLayerException {
		try {
			List<AccountAudio> accountAudios = accountAudioDao.findByAccountId(account.getId());
			Set<AudioAlbum> albums = new HashSet<AudioAlbum>();
			for (AccountAudio accountAudio : accountAudios) {
				AudioAlbum album = accountAudio.getAudio().getAlbum();
				if (album != null) {
					albums.add(album);
				}
			}
			return new ArrayList<AudioAlbum>(albums);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find albums by account!", e);
		}
	}

	@Override
	public AudioAlbum buildAlbumFromEditForm(AlbumEditForm form) throws ServiceLayerException {
		try {
			AudioAlbum album = albumDao.findByName(form.getName());
			
			String imagePath = form.getImagePath();
			if (imagePath != null && !imagePath.isEmpty()) {
				album.setImagePath(imagePath);
			}
			
			Date creationDate = form.getCreationDate();
			album.setCreationDate(creationDate);
			
			String authorName = form.getAuthor();
			if (authorName != null && !authorName.isEmpty()) {
				AudioMaker author = findOrCreateAudioMaker(authorName);
				album.setAudioMaker(author);
			} else {
				album.setAudioMaker(null);
			}
			
			return album;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to build album from edit form!", e);
		}
	}
	
	public AudioMaker findOrCreateAudioMaker(String name) {
		if (!audioMakerDao.isAudioMakerExists(name)) {
			AudioMaker audioMaker = new AudioMaker();
			audioMaker.setName(name);
			audioMakerDao.save(audioMaker);
		}
		
		return audioMakerDao.findByName(name);
	}
	
}
