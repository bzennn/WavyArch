package xyz.bzennn.wavyarch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountRoleDao;
import xyz.bzennn.wavyarch.data.model.AccountRole;

@Service
public class AccountRoleServiceImpl implements AccountRoleService {
	
	@Autowired
	AccountRoleDao accountRoleDao;
	
	@Override
	public AccountRole getById(int id) {
		return accountRoleDao.getById(id);
	}

}
