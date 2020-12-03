package xyz.bzennn.wavyarch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountAudioDao;
import xyz.bzennn.wavyarch.data.dao.AccountDao;
import xyz.bzennn.wavyarch.data.dao.AlbumDao;
import xyz.bzennn.wavyarch.data.dao.AudioDao;
import xyz.bzennn.wavyarch.data.dao.AudioMakerDao;
import xyz.bzennn.wavyarch.data.dao.AuthorDao;
import xyz.bzennn.wavyarch.data.dao.AuthorRoleDao;
import xyz.bzennn.wavyarch.data.dao.GenreDao;
import xyz.bzennn.wavyarch.data.dao.PerformerDao;
import xyz.bzennn.wavyarch.data.dao.TagDao;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountAudio;
import xyz.bzennn.wavyarch.data.model.AccountAudioPK;
import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.AudioTag;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.data.model.AuthorPK;
import xyz.bzennn.wavyarch.data.model.AuthorRole;
import xyz.bzennn.wavyarch.data.model.Performer;
import xyz.bzennn.wavyarch.data.model.PerformerPK;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;
import xyz.bzennn.wavyarch.form.AudioEditForm;
import xyz.bzennn.wavyarch.form.AudioUploadForm;
import xyz.bzennn.wavyarch.form.model.AuthorAndRole;

/**
 * Implementation of {@link AudioService}
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class AudioServiceImpl implements AudioService {

	@Autowired
	private AudioDao audioDao;
	
	@Autowired
	private AccountAudioDao accountAudioDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private AlbumDao albumDao;
	
	@Autowired
	private GenreDao genreDao;
	
	@Autowired
	private AudioMakerDao audioMakerDao;
	
	@Autowired
	private PerformerDao performerDao;
	
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private AuthorRoleDao authorRoleDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Override
	public void save(Audio audio) throws ServiceLayerException {
		try {
			Set<Performer> performers = audio.getPerformers();
			Set<Author> authors = audio.getAuthors();
			
			audioDao.save(audio);
			refresh(audio);
			
			if (performers != null && !performers.isEmpty()) {
				for (Performer performer : performers) {
					PerformerPK performerPK = new PerformerPK();
					performerPK.setAudioId(audio.getId());
					performerPK.setAudioMakerId(performer.getAudioMaker().getId());
					performer.setId(performerPK);
					performerDao.save(performer);
				}
			}
			
			
			if (authors != null && !authors.isEmpty()) {
				for (Author author : authors) {
					AuthorPK authorPK = new AuthorPK();
					authorPK.setAudioId(audio.getId());
					authorPK.setAudioMakerId(author.getAudioMaker().getId());
					author.setId(authorPK);
					authorDao.save(author);
				}
			}
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to save audio!", e);
		}
	}

	@Override
	public Audio findByName(String name) throws ServiceLayerException {
		try {
			return audioDao.findByName(name);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find audio!", e);
		}
	}

	@Override
	public boolean isAudioExists(String name) throws ServiceLayerException {
		try {
			return audioDao.isAudioExists(name);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to check if audio exists!", e);
		}
	}

	@Override
	public void update(Audio audio) throws ServiceLayerException {
		try {
			Audio currentAudio = findByName(audio.getName());
			
			for (Performer performer : currentAudio.getPerformers()) {
				performerDao.delete(performer);
			}
			
			for (Author author : currentAudio.getAuthors()) {
				authorDao.delete(author);
			}
			
			audioDao.update(audio);
			
			Set<Performer> performers = audio.getPerformers();
			if (performers != null && !performers.isEmpty()) {
				for (Performer performer : performers) {
					PerformerPK performerPK = new PerformerPK();
					performerPK.setAudioId(audio.getId());
					performerPK.setAudioMakerId(performer.getAudioMaker().getId());
					performer.setId(performerPK);
					performerDao.save(performer);
				}
			}
			
			Set<Author> authors = audio.getAuthors();
			if (authors != null && !authors.isEmpty()) {
				for (Author author : authors) {
					AuthorPK authorPK = new AuthorPK();
					authorPK.setAudioId(audio.getId());
					authorPK.setAudioMakerId(author.getAudioMaker().getId());
					author.setId(authorPK);
					authorDao.save(author);
				}
			}
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to update audio!", e);
		}
	}

	@Override
	public void delete(Audio audio) throws ServiceLayerException {
		try {
			audioDao.delete(audio);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to delete audio!", e);
		}
	}
	
	@Override
	public void refresh(Audio audio) throws ServiceLayerException {
		try {
			audioDao.refresh(audio);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to refresh audio!", e);
		}
	}

	@Override
	public Audio buildAudioFromUploadForm(AudioUploadForm form) throws ServiceLayerException {
		try {
			Audio audio = new Audio();
			
			audio.setName(form.getName());
			audio.setFilePath(form.getFilePath());
			
			Integer duration = form.getDuration();
			if (duration != null) {
				audio.setDuration(duration);
			}
			
			Date creationDate = form.getCreationDate();
			if (creationDate != null) {
				audio.setCreationDate(creationDate);
			}
			
			String genre = form.getGenre();
			if (genre != null && !genre.isEmpty()) {
				audio.setGenre(findOrCreateGenre(genre));
			}
			
			List<String> performersStr = form.getPerformers();
			if (performersStr != null && !performersStr.isEmpty()) {
				Set<Performer> performers = new HashSet<Performer>();
				for (String performerStr : performersStr) {
					AudioMaker audioMaker = findOrCreateAudioMaker(performerStr);
					performers.add(buildPerformer(audio, audioMaker));
				}
				audio.setPerformers(performers);
			}
			
			String album = form.getAlbum();
			if (album != null && !album.isEmpty()) {
				Performer performer = null;
				if (audio.getPerformers() != null && !audio.getPerformers().isEmpty()) {
					performer = audio.getPerformers().stream().findFirst().get();
				}
				audio.setAlbum(findOrCreateAlbum(album, performer));
			}
			
			List<AuthorAndRole> authorsStr = form.getAuthors();
			if (authorsStr != null && !authorsStr.isEmpty()) {
				Set<Author> authors = new HashSet<Author>();
				for (AuthorAndRole authorAndRole : authorsStr) {
					AudioMaker audioMaker = findOrCreateAudioMaker(authorAndRole.getAuthor());
					authors.add(buildAuthor(audio, audioMaker, authorAndRole.getRole()));
				}
				audio.setAuthors(authors);
			}
			
			List<String> tagsStr = form.getTags();
			if (tagsStr != null && !tagsStr.isEmpty()) {
				Set<AudioTag> tags = new HashSet<AudioTag>();
				for (String tagStr : tagsStr) {
					tags.add(findOrCreateTag(tagStr));
				}
				audio.setTags(tags);
			}
			
			return audio;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to built audio object!", e);
		}
	}
	
	@Override
	public Audio buildAudioFromEditForm(AudioEditForm form) throws ServiceLayerException {
		try {
			Audio audio = findByName(form.getName());
			
			String filePath = form.getFilePath();
			if (filePath != null && !filePath.isEmpty()) {
				audio.setFilePath(filePath);
			}
			
			Integer duration = form.getDuration();
			if (duration != null) {
				audio.setDuration(duration);
			}
			
			Date creationDate = form.getCreationDate();
			audio.setCreationDate(creationDate);
			
			String genre = form.getGenre();
			if (genre != null && !genre.isEmpty()) {
				audio.setGenre(findOrCreateGenre(genre));
			} else {
				audio.setGenre(null);
			}
			
			List<String> performersStr = form.getPerformers();
			if (performersStr != null && !performersStr.isEmpty()) {
				Set<Performer> performers = new HashSet<Performer>();
				for (String performerStr : performersStr) {
					AudioMaker audioMaker = findOrCreateAudioMaker(performerStr);
					performers.add(buildPerformer(audio, audioMaker));
				}
				audio.setPerformers(performers);
			} else {
				audio.setPerformers(Collections.emptySet());
			}
			
			String album = form.getAlbum();
			if (album != null && !album.isEmpty()) {
				Performer performer = null;
				if (audio.getPerformers() != null && !audio.getPerformers().isEmpty()) {
					performer = audio.getPerformers().stream().findFirst().get();
				}
				audio.setAlbum(findOrCreateAlbum(album, performer));
			} else {
				audio.setAlbum(null);
			}
			
			List<AuthorAndRole> authorsStr = form.getAuthors();
			if (authorsStr != null && !authorsStr.isEmpty()) {
				Set<Author> authors = new HashSet<Author>();
				for (AuthorAndRole authorAndRole : authorsStr) {
					AudioMaker audioMaker = findOrCreateAudioMaker(authorAndRole.getAuthor());
					authors.add(buildAuthor(audio, audioMaker, authorAndRole.getRole()));
				}
				audio.setAuthors(authors);
			} else {
				audio.setAuthors(Collections.emptySet());
			}
			
			List<String> tagsStr = form.getTags();
			if (tagsStr != null && !tagsStr.isEmpty()) {
				Set<AudioTag> tags = new HashSet<AudioTag>();
				for (String tagStr : tagsStr) {
					tags.add(findOrCreateTag(tagStr));
				}
				audio.setTags(tags);
			} else {
				audio.setTags(Collections.emptySet());
			}
			
			return audio;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to built audio object!", e);
		}
	}
	
	@Override
	public void deleteFromAccount(Audio audio, Account account) throws ServiceLayerException {
		try {
			AccountAudio accountAudio = accountAudioDao.findByAudioAndAccount(audio, account);
			if (accountAudio != null) {
				accountAudioDao.delete(accountAudio);
			}
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to delete audio from account!", e);
		}
	}
	
	@Override
	public void addAudioToAccount(Audio audio, Account account) throws ServiceLayerException {
		try {
			AccountAudio accountAudio = new AccountAudio();
			accountAudio.setAudio(audio);
			accountAudio.setAccount(account);
			
			AccountAudioPK accountAudioPK = new AccountAudioPK();
			accountAudioPK.setAudioId(audio.getId());
			accountAudioPK.setAccountId(account.getId());
			
			accountAudio.setId(accountAudioPK);
			
			accountAudioDao.save(accountAudio);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to add audio to account!", e);
		}
	}
	
	@Override
		public boolean isAudioExistsOnAccount(Audio audio, Account account) throws ServiceLayerException {
			try {
				return accountAudioDao.isAccountAudioExists(audio, account);
			} catch (Exception e) {
				throw new ServiceLayerException("Failed to check if audio exists on account!", e);
			}
		}

	@Override
	public Set<AccountAudio> loadAudios(Account account) throws ServiceLayerException {
		try {
			return accountDao.loadAudios(account);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to lazily load audios from account!", e);
		}
	}
	
	@Override
	public Set<AccountPlaylist> loadPlaylists(Account account) throws ServiceLayerException {
		try {
			return accountDao.loadPlaylists(account);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to lazily load playlists from account!", e);
		}
	}
	
	@Override
	public List<Audio> findByAccount(Account account) throws ServiceLayerException {
		try {
			List<Audio> result = new ArrayList<Audio>();
			accountAudioDao.findByAccountId(account.getId()).forEach(accountAudio -> result.add(accountAudio.getAudio()));
			return result;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to lazily load palylists from account!", e);
		}
	}
	
	@Override
	public List<Audio> recommendations(String audioName, Integer limit) throws ServiceLayerException {
		try {
			return audioDao.recommendations(audioName, limit);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to build recommendations list!", e);
		}
	}
	
	public AudioGenre findOrCreateGenre(String name) {
		if (!genreDao.isGenreExists(name)) {
			AudioGenre genre = new AudioGenre();
			genre.setName(name);
			genreDao.save(genre);
		}
		
		return genreDao.findByName(name);
	}
	
	public AudioAlbum findOrCreateAlbum(String name, Performer performer) {
		if (!albumDao.isAlbumExists(name)) {
			AudioAlbum album = new AudioAlbum();
			album.setName(name);
			if (performer != null) {
				album.setAudioMaker(performer.getAudioMaker());
			}
			albumDao.save(album);
		}
		
		return albumDao.findByName(name);
	}
	
	public AudioMaker findOrCreateAudioMaker(String name) {
		if (!audioMakerDao.isAudioMakerExists(name)) {
			AudioMaker audioMaker = new AudioMaker();
			audioMaker.setName(name);
			audioMakerDao.save(audioMaker);
		}
		
		return audioMakerDao.findByName(name);
	}
	
	public Performer buildPerformer(Audio audio, AudioMaker audioMaker) {
		Performer performer = new Performer();
		performer.setAudio(audio);
		performer.setAudioMaker(audioMaker);
		
		PerformerPK mockPK = new PerformerPK();
		mockPK.setAudioId(new Long(0));
		mockPK.setAudioMakerId(audioMaker.getId());
		
		performer.setId(mockPK);
		
		return performer;
	}
	
	public Performer findOrCreatePerformer(Audio audio, AudioMaker audioMaker) {
		if (!performerDao.isPerformerExists(audio, audioMaker)) {
			performerDao.save(buildPerformer(audio, audioMaker));
		}
		
		return performerDao.findByAudioAndAudioMaker(audio, audioMaker);
	}
	
	public AuthorRole findOrCreateAuthorRole(String name) {
		if (!authorRoleDao.isAuthorRoleExists(name)) {
			AuthorRole role = new AuthorRole();
			role.setName(name);
			authorRoleDao.save(role);
		}
		
		return authorRoleDao.findByName(name);
	}
	
	public Author buildAuthor(Audio audio, AudioMaker audioMaker, String role) {
		Author author = new Author();
		author.setAudio(audio);
		author.setAudioMaker(audioMaker);
		
		if (role != null && !role.isEmpty()) {
			author.setRole(findOrCreateAuthorRole(role));
		} else {
			author.setRole(findOrCreateAuthorRole("Author"));
		}
		
		AuthorPK mockPK = new AuthorPK();
		mockPK.setAudioId(new Long(0));
		mockPK.setAudioMakerId(audioMaker.getId());
		
		author.setId(mockPK);
		
		return author;
	}
	
	public Author findOrCreateAuthor(Audio audio, AudioMaker audioMaker, String role) {
		if (!authorDao.isAuthorExists(audio, audioMaker)) {
			authorDao.save(buildAuthor(audio, audioMaker, role));
		}
		
		return authorDao.findByAudioAndAudioMaker(audio, audioMaker);
	}
	
	public AudioTag findOrCreateTag(String name) {
		if (!tagDao.isTagExists(name)) {
			AudioTag tag = new AudioTag();
			tag.setName(name);
			tagDao.save(tag);
		}
		
		return tagDao.findByName(name);
	}
	
}
