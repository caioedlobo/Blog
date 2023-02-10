package br.com.caiolobo.blogapplication.repositories;

import br.com.caiolobo.blogapplication.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}