package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
