package xyz.bzennn.wavyarch.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.model.AccountPlaylist;

/**
 * Implementation of {@link SortingService} for {@link AccountPlaylist} sorting
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class PlaylistSortingService implements SortingService<AccountPlaylist> {

	private boolean order;
	
	public PlaylistSortingService() {
		order = true;
	}
	
	@Override
	public void sort(List<AccountPlaylist> list, Map<String, String> params) {
		Comparator<? super AccountPlaylist> comparator = getComparator(params);
		
		if (comparator != null) {
			list.sort(comparator);
		}
	}
	
	private Comparator<? super AccountPlaylist> getComparator(Map<String, String> params) {
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
		}
		
		return null;
	}
	
	private int compareByName(AccountPlaylist a1, AccountPlaylist a2) {
		if (order) {
			return a1.getName().compareTo(a2.getName());
		} else {
			return a2.getName().compareTo(a1.getName());
		}
	}

}
