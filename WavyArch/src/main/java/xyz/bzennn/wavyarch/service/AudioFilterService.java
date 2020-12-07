package xyz.bzennn.wavyarch.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.data.model.AudioAlbum;
import xyz.bzennn.wavyarch.data.model.AudioGenre;

/**
 * Implementation of {@link FilterService} for {@link Audio} filtering 
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class AudioFilterService implements FilterService<Audio> {

	@Override
	public void filter(List<Audio> list, Map<String, String> params) {
		String performers = params.get("performers");
		if (performers != null && !performers.isEmpty()) {
			filter(list, buildPerformersPredicate(performers));
		}
		
		String authors = params.get("authors");
		if (authors != null && !authors.isEmpty()) {
			filter(list, buildAuthorsPredicate(authors));
		}
		
		String albums = params.get("albums");
		if (albums != null && !albums.isEmpty()) {
			filter(list, buildAlbumsPredicate(albums));
		}
		
		String genres = params.get("genres");
		if (genres != null && !genres.isEmpty()) {
			filter(list, buildGenresPredicate(genres));
		}
		
		String tags = params.get("tags");
		if (tags != null && !tags.isEmpty()) {
			filter(list, buildTagsPredicate(tags));
		}
		
		String yearFrom = params.get("yearFrom");
		if (yearFrom != null && !yearFrom.isEmpty()) {
			filter(list, buildYearFromPredicate(yearFrom));
		}
		
		String yearTo = params.get("yearTo");
		if (yearTo != null && !yearTo.isEmpty()) {
			filter(list, buildYearToPredicate(yearTo));
		}
		
	}
	
	private void filter(List<Audio> list, Predicate<Audio> predicate) {
		list.removeIf(predicate.negate());
	}
	
	private Predicate<Audio> buildPerformersPredicate(String performers) {
		return audio -> {
			List<String> list = Arrays.asList(performers.split(";")).stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
			List<String> audioList = audio.getPerformers().stream().map(n -> n.getAudioMaker().getName()).map(String::toLowerCase).collect(Collectors.toList());
			
			return !Collections.disjoint(list, audioList);
		};
	}
	
	private Predicate<Audio> buildAuthorsPredicate(String authors) {
		return audio -> {
			List<String> list = Arrays.asList(authors.split(";")).stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
			List<String> audioList = audio.getAuthors().stream().map(n -> n.getAudioMaker().getName()).map(String::toLowerCase).collect(Collectors.toList());
			
			return !Collections.disjoint(list, audioList);
		};
	}
	
	private Predicate<Audio> buildAlbumsPredicate(String albums) {
		return audio -> {
			List<String> list = Arrays.asList(albums.split(";")).stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
			AudioAlbum paramValue = audio.getAlbum();
			if (paramValue != null) {
				String audioParam = paramValue.getName().toLowerCase();
				return list.contains(audioParam);
			}
			return false;
		};
	}
	
	private Predicate<Audio> buildGenresPredicate(String genres) {
		return audio -> {
			List<String> list = Arrays.asList(genres.split(";")).stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
			AudioGenre paramValue = audio.getGenre();
			if (paramValue != null) {
				String audioParam = paramValue.getName().toLowerCase();
				return list.contains(audioParam);
			}
			return false;
		};
	}
	
	private Predicate<Audio> buildTagsPredicate(String tags) {
		return audio -> {
			List<String> list = Arrays.asList(tags.split(";")).stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
			List<String> audioList = audio.getTags().stream().map(n -> n.getName()).map(String::toLowerCase).collect(Collectors.toList());
			
			return !Collections.disjoint(list, audioList);
		};
	}
	
	private Predicate<Audio> buildYearFromPredicate(String yearFrom) {
		return audio -> {
			Date dateFrom;
			try {
				dateFrom = new SimpleDateFormat("yyyy").parse(yearFrom);
			} catch (Exception e) {
				return false;
			}
			
			Set<Date> dates = new HashSet<Date>();
			
			if (audio.getCreationDate() != null) {
				dates.add(audio.getCreationDate());
			}
			
			if (audio.getAlbum() != null) {
				if (audio.getAlbum().getCreationDate() != null) {
					dates.add(audio.getAlbum().getCreationDate());
				}
			}
			
			if (audio.getPerformers() != null) {
				dates.addAll(audio.getPerformers().stream().map(n -> n.getAudioMaker().getCreationDate()).collect(Collectors.toList()));
			}
			
			if (audio.getAuthors() != null) {
				dates.addAll(audio.getAuthors().stream().map(n -> n.getAudioMaker().getCreationDate()).collect(Collectors.toList()));
			}
			
			dates.removeIf(n -> n == null);
			for (Date date : dates) {
				if (dateFrom.before(date)) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	private Predicate<Audio> buildYearToPredicate(String yearTo) {
		return audio -> {
			Date dateTo;
			try {
				dateTo = new SimpleDateFormat("yyyy").parse(yearTo);
			} catch (Exception e) {
				return false;
			}
			
			Set<Date> dates = new HashSet<Date>();
			
			if (audio.getCreationDate() != null) {
				dates.add(audio.getCreationDate());
			}
			
			if (audio.getAlbum() != null) {
				if (audio.getAlbum().getCreationDate() != null) {
					dates.add(audio.getAlbum().getCreationDate());
				}
			}
			
			if (audio.getPerformers() != null) {
				dates.addAll(audio.getPerformers().stream().map(n -> n.getAudioMaker().getCreationDate()).collect(Collectors.toList()));
			}
			
			if (audio.getAuthors() != null) {
				dates.addAll(audio.getAuthors().stream().map(n -> n.getAudioMaker().getCreationDate()).collect(Collectors.toList()));
			}
			
			dates.removeIf(n -> n == null);
			for (Date date : dates) {
				if (dateTo.after(date)) {
					return true;
				}
			}
			
			return false;
		};
	}

}
