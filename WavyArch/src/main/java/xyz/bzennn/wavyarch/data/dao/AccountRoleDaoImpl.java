package xyz.bzennn.wavyarch.data.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AccountRole;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

@Repository
public class AccountRoleDaoImpl extends BaseDaoImpl<AccountRole> implements AccountRoleDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public AccountRole getById(int id) throws DaoLayerException {
		return findByAttribute(AccountRole.class, "id", id);
	}

	@Override
	public AccountRole findByRoleName(String roleName) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			AccountRole role = (AccountRole) session.createQuery("from AccountRole where name=:roleName").setParameter("roleName", roleName).uniqueResult();
			transaction.commit();
			session.close();
			return role;	
		} catch (Exception e) {
			throw new DaoLayerException("Failed to get account role by roleName!", e);
		}
	}

}
