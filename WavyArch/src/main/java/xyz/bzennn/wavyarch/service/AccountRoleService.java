package xyz.bzennn.wavyarch.service;

import xyz.bzennn.wavyarch.data.model.AccountRole;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * Service class for {@link AccountRole} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountRoleService {
	AccountRole getById(int id) throws ServiceLayerException;
}
