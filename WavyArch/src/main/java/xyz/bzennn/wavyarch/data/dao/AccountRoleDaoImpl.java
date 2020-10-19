package xyz.bzennn.wavyarch.data.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AccountRole;

@Repository
public class AccountRoleDaoImpl implements AccountRoleDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public AccountRole getById(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		AccountRole role = (AccountRole) session.createQuery("from AccountRole where role_id=:id").setParameter("id", id).uniqueResult();
		transaction.commit();
		session.close();
		
		return role;
	}

}
