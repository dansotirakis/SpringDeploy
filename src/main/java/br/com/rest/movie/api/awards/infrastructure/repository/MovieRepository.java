package br.com.rest.movie.api.awards.infrastructure.repository;

import br.com.rest.movie.api.awards.application.model.EntityMovie;
import br.com.rest.movie.api.awards.util.dto.EntityMovieDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<EntityMovie, Long> {

      default List<EntityMovieDTO> uploadCsvFile(URL csvFile) throws IOException {
      ArrayList<EntityMovieDTO> movies = new ArrayList<>();
      try {
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile.getPath())))) {
          String line;
          var numberLine = 1;
          while ((line = reader.readLine()) != null) {
            if (numberLine == 1) {
              numberLine++;
              continue;
            }
            final var csvResource = ";";
            String[] movieFields = line.split(csvResource);
            movies.add(new EntityMovieDTO(movieFields[0], movieFields[1], movieFields[2], movieFields[3],
                    movieFields.length == 5 ? "yes" : ""));
          }
        }
      } catch (IOException | NullPointerException e) {
        throw new IOException(e.getMessage());
      }
      return movies;
    }
}
