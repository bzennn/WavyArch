package xyz.bzennn.wavyarch.service;

import java.util.List;
import java.util.Map;

/**
 * Service class for sorting audios 
 *
 * @author bzennn
 * @version 1.0
 */
public interface SortingService<T> {
	void sort(List<T> list, Map<String, String> params);
}
