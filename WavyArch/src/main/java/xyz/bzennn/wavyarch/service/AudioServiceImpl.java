package xyz.bzennn.wavyarch.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AlbumDao;
import xyz.bzennn.wavyarch.data.dao.AudioDao;
import xyz.bzennn.wavyarch.data.dao.AudioMakerDao;
import xyz.bzennn.wavyarch.data.dao.AuthorDao;
import xyz.bzennn.wavyarch.data.dao.AuthorRoleDao;
import xyz.bzennn.wavyarch.data.dao.GenreDao;
import xyz.bzennn.wavyarch.data.dao.PerformerDao;
import xyz.bzennn.wavyarch.data.dao.TagDao;
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
import xyz.bzennn.wavyarch.form.AudioUploadForm;
import xyz.bzennn.wavyarch.form.AudioUploadForm.AuthorAndRole;

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
	}

	@Override
	public Audio findByName(String name) throws ServiceLayerException {
		return audioDao.findByName(name);
	}

	@Override
	public boolean isAudioExists(String name) throws ServiceLayerException {
		return audioDao.isAudioExists(name);
	}

	@Override
	public void update(Audio audio) throws ServiceLayerException {
		audioDao.update(audio);
	}

	@Override
	public void delete(Audio audio) throws ServiceLayerException {
		audioDao.delete(audio);
	}
	
	@Override
	public void refresh(Audio audio) throws ServiceLayerException {
		audioDao.refresh(audio);
	}

	@Override
	public Audio buildAudioFromUploadForm(AudioUploadForm form) {
		Audio audio = new Audio();
		
		audio.setName(form.getName());
		audio.setFilePath(form.getFilePath());
		
		Date creationDate = form.getCreationDate();
		if (creationDate != null) {
			audio.setCreationDate(creationDate);
		}
		
		String genre = form.getGenre();
		if (genre != null && !genre.isEmpty()) {
			audio.setGenre(findOrCreateGenre(genre));
		}
		
		String album = form.getAlbum();
		if (album != null && !album.isEmpty()) {
			audio.setAlbum(findOrCreateAlbum(album));
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
	}
	
	public AudioGenre findOrCreateGenre(String name) {
		if (!genreDao.isGenreExists(name)) {
			AudioGenre genre = new AudioGenre();
			genre.setName(name);
			genreDao.save(genre);
		}
		
		return genreDao.findByName(name);
	}
	
	public AudioAlbum findOrCreateAlbum(String name) {
		if (!albumDao.isAlbumExists(name)) {
			AudioAlbum album = new AudioAlbum();
			album.setName(name);
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
		author.setRole(findOrCreateAuthorRole(role));
		
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
