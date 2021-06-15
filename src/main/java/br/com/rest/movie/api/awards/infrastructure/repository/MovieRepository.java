package br.com.rest.movie.api.awards.infrastructure.repository;

import br.com.rest.movie.api.awards.application.model.EntityMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<EntityMovie, Long> {

  @Modifying
  @Query(value = "TRUNCATE TABLE filme", nativeQuery = true)
  void clearTable();

}
