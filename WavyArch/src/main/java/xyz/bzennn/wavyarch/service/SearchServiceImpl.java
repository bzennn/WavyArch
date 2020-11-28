package xyz.bzennn.wavyarch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AlbumDao;
import xyz.bzennn.wavyarch.data.dao.AudioDao;
import xyz.bzennn.wavyarch.data.dao.AudioMakerDao;
import xyz.bzennn.wavyarch.data.dao.GenreDao;
import xyz.bzennn.wavyarch.data.dao.TagDao;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.AudioTag;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.data.model.Performer;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * Implementation of {@link SearchService}
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private AudioDao audioDao;

	@Autowired
	private GenreDao genreDao;

	@Autowired
	private AlbumDao albumDao;

	@Autowired
	private AudioMakerDao audioMakerDao;

	@Autowired
	private TagDao tagDao;

	@Override
	public List<Audio> searchAll(String request) throws ServiceLayerException {
		try {
			List<Audio> byAudioName = searchByAudioName(request);
			List<Audio> byGenreName = searchByGenreName(request);
			List<Audio> byAlbumName = searchByAlbumName(request);
			List<Audio> byAudioMakerName = searchByAudioMakerName(request);
			List<Audio> byTagName = searchByTagName(request);

			Set<Audio> result = new LinkedHashSet<Audio>(byAudioName);
			result.addAll(byGenreName);
			result.addAll(byAlbumName);
			result.addAll(byAudioMakerName);
			result.addAll(byTagName);
			
			return new ArrayList<Audio>(result);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to perform search by all fields!", e);
		}
	}

	@Override
	public List<Audio> searchByAudioName(String request) throws ServiceLayerException {
		try {
			return audioDao.search(request);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to perform search by audio name field!", e);
		}
	}

	@Override
	public List<Audio> searchByGenreName(String request) throws ServiceLayerException {
		try {
			List<AudioGenre> genres = genreDao.search(request);
			Set<Audio> result = new LinkedHashSet<Audio>();
			for (AudioGenre genre : genres) {
				if (genre != null) {
					result.addAll(genre.getAudios());
				}
			}
			result.removeAll(Collections.singleton(null));

			return new ArrayList<Audio>(result);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to perform search by genre name field!", e);
		}
	}

	@Override
	public List<Audio> searchByAlbumName(String request) throws ServiceLayerException {
		try {
			List<AudioAlbum> albums = albumDao.search(request);
			Set<Audio> result = new LinkedHashSet<Audio>();
			for (AudioAlbum album : albums) {
				if (album != null) {
					result.addAll(album.getAudios());
				}
			}
			result.removeAll(Collections.singleton(null));

			return new ArrayList<Audio>(result);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to perform search by album name field!", e);
		}
	}

	@Override
	public List<Audio> searchByAudioMakerName(String request) throws ServiceLayerException {
		try {
			List<AudioMaker> audioMakers = audioMakerDao.search(request);
			Set<Audio> result = new LinkedHashSet<Audio>();
			for (AudioMaker audioMaker : audioMakers) {
				if (audioMaker != null) {
					Set<Author> audiosCreated = audioMaker.getAudiosCreated();
					for (Author audioCreated : audiosCreated) {
						if (audioCreated != null) {
							result.add(audioCreated.getAudio());
						}
					}
					
					Set<Performer> audiosPerformed = audioMaker.getAudiosPerformed();
					for (Performer audioPerformed : audiosPerformed) {
						if (audioPerformed != null) {
							result.add(audioPerformed.getAudio());
						}
					}
				}
			}
			result.removeAll(Collections.singleton(null));

			return new ArrayList<Audio>(result);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to perform search by audio maker name field!", e);
		}
	}

	@Override
	public List<Audio> searchByTagName(String request) throws ServiceLayerException {
		try {
			List<AudioTag> tags = tagDao.search(request);
			Set<Audio> result = new LinkedHashSet<Audio>();
			for (AudioTag tag : tags) {
				if (tag != null) {
					result.addAll(tag.getAudios());
				}
			}
			result.removeAll(Collections.singleton(null));

			return new ArrayList<Audio>(result);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to perform search by tag field!", e);
		}
	}

}
