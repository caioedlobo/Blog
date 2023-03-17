package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    //@Query(value = " SELECT u FROM Account u WHERE u.email = fulano@gmail.com ", nativeQuery = true)
    Account findByEmail(@Param("email") String email);
}
