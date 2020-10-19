package xyz.bzennn.wavyarch.data.dao;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AccountRole;

/**
 * DAO interface for {@link AccountRole}
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountRoleDao {
	AccountRole getById(int id);
}
