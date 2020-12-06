package xyz.bzennn.wavyarch.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioMaker;

/**
 * Implementation of {@link SortingService} for {@link AudioAlbum} sorting
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class AlbumSortingService implements SortingService<AudioAlbum> {

	private boolean order;
	
	public AlbumSortingService() {
		order = true;
	}
	
	@Override
	public void sort(List<AudioAlbum> list, Map<String, String> params) {
		Comparator<? super AudioAlbum> comparator = getComparator(params);
		
		if (comparator != null) {
			list.sort(comparator);
		}
	}

	private Comparator<? super AudioAlbum> getComparator(Map<String, String> params) {
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
			case "date": 
				return this::compareByDate;
			case "author": 
				return this::compareByAuthor;
		}
		
		return null;
	}
	
	private int compareByName(AudioAlbum a1, AudioAlbum a2) {
		if (order) {
			return a1.getName().compareTo(a2.getName());
		} else {
			return a2.getName().compareTo(a1.getName());
		}
	}
	
	private int compareByDate(AudioAlbum a1, AudioAlbum a2) {
		Date date1 = a1.getCreationDate();
		Date date2 = a2.getCreationDate();
		
		if (date1 == null && date2 == null) {
			return 0;
		}
		
		if (date2 == null) {
			return -1;
		} else if (date1 == null) {
			return 1;
		}
		
		if (order) {
			return date1.compareTo(date2);
		} else {
			return date2.compareTo(date1);
		}
	}
	
	private int compareByAuthor(AudioAlbum a1, AudioAlbum a2) {
		AudioMaker audioMaker1 = a1.getAudioMaker();
		AudioMaker audioMaker2 = a2.getAudioMaker();
		
		if (audioMaker1 == null && audioMaker2 == null) {
			return 0;
		}
		
		if (audioMaker2 == null) {
			return -1;
		} else if (audioMaker1 == null) {
			return 1;
		}
		
		String name1 = audioMaker1.getName();
		String name2 = audioMaker2.getName();
		if (order) {
			return name1.compareTo(name2);
		} else {
			return name2.compareTo(name1);
		}
	}
	
}
