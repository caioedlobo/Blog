package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.Account;
import br.com.caiolobo.blogapplication.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAccountId(@Param("id") Long id);
}
