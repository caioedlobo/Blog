package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByAccountId(@Param("id") Long id);
    Page<Post> findAllByAccountId(@Param("id") Long id, Pageable pageable);

    List<Post> findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(String title, String body);
}
