package xyz.bzennn.wavyarch.service;

import java.util.List;
import java.util.Map;

/**
 * Service class for filtering entities
 *
 * @author bzennn
 * @version 1.0
 */
public interface FilterService<T> {
	void filter(List<T> list, Map<String, String> params);
}
