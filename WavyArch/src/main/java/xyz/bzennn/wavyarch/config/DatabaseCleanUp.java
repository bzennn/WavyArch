package xyz.bzennn.wavyarch.config;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import xyz.bzennn.wavyarch.data.dao.AlbumDao;
import xyz.bzennn.wavyarch.data.dao.AudioMakerDao;
import xyz.bzennn.wavyarch.data.dao.AuthorRoleDao;
import xyz.bzennn.wavyarch.data.dao.GenreDao;
import xyz.bzennn.wavyarch.data.dao.TagDao;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.AudioTag;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.data.model.AuthorRole;
import xyz.bzennn.wavyarch.data.model.Performer;

/**
 * Bean that cleans up database on application startup
 *
 * @author bzennn
 * @version 1.0
 */
@PropertySource("classpath:upload.properties")
public class DatabaseCleanUp {

	private static Logger logger = LogManager.getLogger(DatabaseCleanUp.class);

	@Value("${upload.file_path}")
	private String uploadPath;

	@Autowired
	AlbumDao albumDao;

	@Autowired
	AudioMakerDao audioMakerDao;

	@Autowired
	AuthorRoleDao authorRoleDao;

	@Autowired
	GenreDao genreDao;

	@Autowired
	TagDao tagDao;

	@PostConstruct
	public void cleanUp() {
		cleanUpAlbums();
		cleanUpAudioMakers();
		cleanUpAuthorRoles();
		cleanUpGenres();
		cleanUpTags();
	}

	private void cleanUpAlbums() {
		List<AudioAlbum> all = albumDao.findAll();
		
		logger.info("Cleaning up Albums table!");
		int count = 0;
		for (AudioAlbum album : all) {
			Set<Audio> audios = album.getAudios();
			if (audios == null || audios.isEmpty()) {
				String imagePath = album.getImagePath();
				if (imagePath != null && !imagePath.isEmpty()) {
					File file = new File(uploadPath + CommonProperties.ALBUM_COVER_FILE_PATH, imagePath);
					if (file != null && file.exists()) {
						file.delete();
					}
				}
				albumDao.delete(album);
				count++;
			}
		}
		logger.info("Cleaned up " + count + " rows");
	}

	private void cleanUpAudioMakers() {
		List<AudioMaker> all = audioMakerDao.findAll();
		
		logger.info("Cleaning up AudioMakers table!");
		int count = 0;
		for (AudioMaker audioMaker : all) {
			Set<Author> audiosCreated = audioMaker.getAudiosCreated();
			Set<Performer> audiosPerformed = audioMaker.getAudiosPerformed();
			
			if ((audiosCreated == null || audiosCreated.isEmpty()) &&
				(audiosPerformed == null || audiosPerformed.isEmpty())) {
				String imagePath = audioMaker.getImagePath();
				if (imagePath != null && !imagePath.isEmpty()) {
					File file = new File(uploadPath + CommonProperties.ALBUM_COVER_FILE_PATH, imagePath);
					if (file != null && file.exists()) {
						file.delete();
					}
					audioMakerDao.delete(audioMaker);
					count++;
				}
			}
		}
		logger.info("Cleaned up " + count + " rows");
	}
	
	private void cleanUpAuthorRoles() {
		List<AuthorRole> all = authorRoleDao.findAll();
		
		logger.info("Cleaning up AuthorRoles table!");
		int count = 0;
		for (AuthorRole role : all) {
			Set<Author> authors = role.getAuthors();
			if (authors == null || authors.isEmpty()) {
				authorRoleDao.delete(role);
				count++;
			}
		}
		logger.info("Cleaned up " + count + " rows");
	}
	
	private void cleanUpGenres() {
		List<AudioGenre> all = genreDao.findAll();
		
		logger.info("Cleaning up Genres table!");
		int count = 0;
		for (AudioGenre genre : all) {
			Set<Audio> audios = genre.getAudios();
			if (audios == null || audios.isEmpty()) {
				genreDao.delete(genre);
				count++;
			}
		}
		logger.info("Cleaned up " + count + " rows");
	}
	
	private void cleanUpTags() {
		List<AudioTag> all = tagDao.findAll();
		
		logger.info("Cleaning up Tags table!");
		int count = 0;
		for (AudioTag tag : all) {
			Set<Audio> audios = tag.getAudios();
			if (audios == null || audios.isEmpty()) {
				tagDao.delete(tag);
				count++;
			}
		}
		logger.info("Cleaned up " + count + " rows");
	}

}
