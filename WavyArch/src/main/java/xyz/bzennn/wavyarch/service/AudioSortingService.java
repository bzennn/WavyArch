package xyz.bzennn.wavyarch.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioGenre;
import xyz.bzennn.wavyarch.data.model.AudioMaker;
import xyz.bzennn.wavyarch.data.model.AudioTag;
import xyz.bzennn.wavyarch.data.model.Author;
import xyz.bzennn.wavyarch.data.model.Performer;

/**
 * Implementation of {@link SortingService} for {@link Audio} sorting
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class AudioSortingService implements SortingService<Audio> {

	private boolean order;
	
	public AudioSortingService() {
		order = true;
	}
	
	@Override
	public void sort(List<Audio> list, Map<String, String> params) {
		Comparator<? super Audio> comparator = getComparator(params);
		
		if (comparator != null) {
			list.sort(comparator);
		}
	}
	
	private Comparator<? super Audio> getComparator(Map<String, String> params) {
		String sort = params.get("sort");
		if (sort == null || sort.isEmpty()) {
			return null;
		}
		
		String order = params.get("order");
		if (order == null || order.isEmpty()) {
			return null;
		}
		
		if (order.equals("asc")) {
			this.order = true;
		} else if (order.equals("desc")) {
			this.order = false;
		}
		
		switch(sort) {
			case "name": 
				return this::compareByName;
			case "duration": 
				return this::compareByDuration;
			case "performers": 
				return this::compareByPerformers;
			case "authors": 
				return this::compareByAuthors;
			case "album": 
				return this::compareByAlbum;
			case "genre": 
				return this::compareByGenre;
			case "tags": 
				return this::compareByTags;
		}
		
		return null;
	}
	
	private int compareByName(Audio a1, Audio a2) {
		if (order) {
			return a1.getName().compareTo(a2.getName());
		} else {
			return a2.getName().compareTo(a1.getName());
		}
	}
	
	private int compareByDuration(Audio a1, Audio a2) {
		if (order) {
			return a1.getDuration().compareTo(a2.getDuration());
		} else {
			return a2.getDuration().compareTo(a1.getDuration());
		}
	}
	
	private int compareByPerformers(Audio a1, Audio a2) {
		Set<Performer> performers1 = a1.getPerformers();
		Set<Performer> performers2 = a2.getPerformers();
		
		if (performers1.isEmpty() && performers2.isEmpty()) {
			return 0;
		}
		
		if (performers2.isEmpty()) {
			return -1;
		} else if (performers1.isEmpty()) {
			return 1;
		}
		
		String performers1str = performers1.stream().map(Performer::getAudioMaker).map(AudioMaker::getName).collect(Collectors.joining(" "));
		String performers2str = performers2.stream().map(Performer::getAudioMaker).map(AudioMaker::getName).collect(Collectors.joining(" "));
		if (order) {
			return performers1str.compareTo(performers2str);
		} else {		
			return performers2str.compareTo(performers1str);
		}
	}
	
	private int compareByAuthors(Audio a1, Audio a2) {
		Set<Author> authors1 = a1.getAuthors();
		Set<Author> authors2 = a2.getAuthors();
		
		if (authors1.isEmpty() && authors2.isEmpty()) {
			return 0;
		}
		
		if (authors2.isEmpty()) {
			return -1;
		} else if (authors1.isEmpty()) {
			return 1;
		}
		
		String authors1str = authors1.stream().map(Author::getAudioMaker).map(AudioMaker::getName).collect(Collectors.joining(" "));
		String authors2str = authors2.stream().map(Author::getAudioMaker).map(AudioMaker::getName).collect(Collectors.joining(" "));
		if (order) {
			return authors1str.compareTo(authors2str);
		} else {		
			return authors2str.compareTo(authors1str);
		}
	}
	
	private int compareByAlbum(Audio a1, Audio a2) {
		AudioAlbum album1 = a1.getAlbum();
		AudioAlbum album2 = a2.getAlbum();
		
		if (album1 == null && album2 == null) {
			return 0;
		}
		
		if (album2 == null) {
			return -1;
		} else if (album1 == null) {
			return 1;
		}
		
		if (order) {
			return album1.getName().compareTo(album2.getName());
		} else {		
			return album2.getName().compareTo(album1.getName());
		}
	}
	
	private int compareByGenre(Audio a1, Audio a2) {
		AudioGenre genre1 = a1.getGenre();
		AudioGenre genre2 = a2.getGenre();
		
		if (genre1 == null && genre2 == null) {
			return 0;
		}
		
		if (genre2 == null) {
			return -1;
		} else if (genre1 == null) {
			return 1;
		}
		
		if (order) {
			return genre1.getName().compareTo(genre2.getName());
		} else {		
			return genre2.getName().compareTo(genre1.getName());
		}
	}
	
	private int compareByTags(Audio a1, Audio a2) {
		Set<AudioTag> tags1 = a1.getTags();
		Set<AudioTag> tags2 = a2.getTags();
		
		if (tags1.isEmpty() && tags2.isEmpty()) {
			return 0;
		}
		
		if (tags2.isEmpty()) {
			return -1;
		} else if (tags1.isEmpty()) {
			return 1;
		}
		
		String tags1str = tags1.stream().map(AudioTag::getName).collect(Collectors.joining(" "));
		String tags2str = tags2.stream().map(AudioTag::getName).collect(Collectors.joining(" "));
		if (order) {
			return tags1str.compareTo(tags2str);
		} else {		
			return tags2str.compareTo(tags1str);
		}
	}
	
}
