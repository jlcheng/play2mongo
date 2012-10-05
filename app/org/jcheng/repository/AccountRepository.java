package org.jcheng.repository;

import java.io.Serializable;
import java.util.List;

import org.jcheng.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Serializable> {
	
    List<Account> findByUsername(String username);

    List<Account> findByUsernameAndActive(String username, boolean isActive);
    
    Account findOneByUsername(String username);

}
