package xyz.bzennn.wavyarch.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.model.AudioMaker;

/**
 * Implementation of {@link SortingService} for {@link AudioMaker} sorting
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class PerformerSortingService implements SortingService<AudioMaker> {

	private boolean order;
	
	public PerformerSortingService() {
		order = true;
	}
	
	@Override
	public void sort(List<AudioMaker> list, Map<String, String> params) {
		Comparator<? super AudioMaker> comparator = getComparator(params);
		
		if (comparator != null) {
			list.sort(comparator);
		}
	}

	private Comparator<? super AudioMaker> getComparator(Map<String, String> params) {
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
		}
		
		return null;
	}
	
	private int compareByName(AudioMaker a1, AudioMaker a2) {
		if (order) {
			return a1.getName().compareTo(a2.getName());
		} else {
			return a2.getName().compareTo(a1.getName());
		}
	}
	
	private int compareByDate(AudioMaker a1, AudioMaker a2) {
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
	
}
