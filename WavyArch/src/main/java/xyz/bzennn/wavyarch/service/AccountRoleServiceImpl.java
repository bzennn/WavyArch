package xyz.bzennn.wavyarch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountRoleDao;
import xyz.bzennn.wavyarch.data.model.AccountRole;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;


/**
 * Implementation of {@link AccountRoleService} 
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class AccountRoleServiceImpl implements AccountRoleService {
	
	@Autowired
	AccountRoleDao accountRoleDao;
	
	@Override
	public AccountRole getById(int id) throws ServiceLayerException {
		return accountRoleDao.getById(id);
	}

}
