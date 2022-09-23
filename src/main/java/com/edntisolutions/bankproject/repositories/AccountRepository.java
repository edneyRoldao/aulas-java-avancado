package com.edntisolutions.bankproject.repositories;

import com.edntisolutions.bankproject.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findFirstByNumber(Long accountNumber);

    /*
        select * FROM account a inner join client c on (a.client_id = c.id)
        WHERE c.email = 'vzxcvzxcv@mail.com' or c.document = '30040462889';
    */
    Optional<Account> findFirstByClientDocumentOrClientEmail(String document, String email);

}
