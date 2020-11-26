package xyz.bzennn.wavyarch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountAudioDao;
import xyz.bzennn.wavyarch.data.dao.AudioDao;
import xyz.bzennn.wavyarch.data.dao.AudioMakerDao;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountAudio;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.data.model.Performer;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.PerformerEditForm;

/**
 * Implementation of {@link PerformerService} 
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class PerformerServiceImpl implements PerformerService {

	@Autowired
	AudioMakerDao audioMakerDao;
	
	@Autowired
	AudioDao audioDao;
	
	@Autowired
	AccountAudioDao accountAudioDao;
	
	@Override
	public void save(AudioMaker audioMaker) throws ServiceLayerException {
		try {
			audioMakerDao.save(audioMaker);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to save audio maker!", e);
		}
	}

	@Override
	public void update(AudioMaker audioMaker) throws ServiceLayerException {
		try {
			audioMakerDao.update(audioMaker);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to update audio maker!", e);
		}
	}

	@Override
	public void delete(AudioMaker audioMaker) throws ServiceLayerException {
		try {
			audioMakerDao.delete(audioMaker);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to delete audio maker!", e);
		}
	}

	@Override
	public void refresh(AudioMaker audioMaker) throws ServiceLayerException {
		try {
			audioMakerDao.refresh(audioMaker);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to refresh audio maker!", e);
		}
	}

	@Override
	public List<AudioMaker> findByAudioName(String audioName) throws ServiceLayerException {
		try {
			Audio audio = audioDao.findByName(audioName);
			Set<AudioMaker> audioMakers = new HashSet<AudioMaker>();
			if (audio != null) {
				for (Performer performer : audio.getPerformers()) {
					if (performer != null) {
						audioMakers.add(performer.getAudioMaker());
					}
				}
			}
			return new ArrayList<AudioMaker>(audioMakers);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find audio maker by name!", e);
		}
	}

	@Override
	public List<Audio> findAudiosByName(String audioMakerName) throws ServiceLayerException {
		try {
			AudioMaker audioMaker = audioMakerDao.findByName(audioMakerName);
			Set<Audio> audios = new HashSet<Audio>();
			if (audioMaker != null) {
				for (Author author : audioMaker.getAudiosCreated()) {
					if (author != null) {
						audios.add(author.getAudio());
					}
				}
				for (Performer performer : audioMaker.getAudiosPerformed()) {
					if (performer != null) {
						audios.add(performer.getAudio());
					}
				}
			}
			return new ArrayList<Audio>(audios);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find audios by audio maker name!", e);
		}
	}

	@Override
	public List<AudioMaker> findByAccount(Account account) throws ServiceLayerException {
		try {
			List<AccountAudio> audios = accountAudioDao.findByAccountId(account.getId());
			Set<AudioMaker> audioMakers = new HashSet<AudioMaker>();
			for (AccountAudio audio : audios) {
				Set<Performer> performers = audio.getAudio().getPerformers();
				for (Performer performer : performers) {
					if (performer != null) {
						audioMakers.add(performer.getAudioMaker());
					}
				}
			}
			return new ArrayList<AudioMaker>(audioMakers);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find audio makers by account!", e);
		}
	}

	@Override
	public AudioMaker buildAudioMakerFromEditForm(PerformerEditForm form) throws ServiceLayerException {
		try {
			AudioMaker audioMaker = audioMakerDao.findByName(form.getName());
			
			String imagePath = form.getImagePath();
			if (imagePath != null && !imagePath.isEmpty()) {
				audioMaker.setImagePath(imagePath);
			}
			
			Date creationDate = form.getCreationDate();
			audioMaker.setCreationDate(creationDate);
			
			String description = form.getDescription();
			audioMaker.setDescription(description); 
			
			return audioMaker;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to build audio maker from edit form!", e);
		}
	}

	@Override
	public AudioMaker findByName(String audioMakerName) throws ServiceLayerException {
		try {
			return audioMakerDao.findByName(audioMakerName);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find audio maker by name!", e);
		}
	}

}
