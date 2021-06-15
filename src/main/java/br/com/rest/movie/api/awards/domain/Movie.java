package br.com.rest.movie.api.awards.domain;

import br.com.rest.movie.api.awards.application.controller.ApiMovieController;
import br.com.rest.movie.api.awards.application.model.EntityMovie;
import br.com.rest.movie.api.awards.infrastructure.repository.MovieRepository;
import br.com.rest.movie.api.awards.util.dto.IntervalAwardsDTO;
import br.com.rest.movie.api.awards.util.dto.IntervalDTO;
import br.com.rest.movie.api.awards.util.dto.MovieDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class Movie implements IMovie {

  private final URL csvFile = this.getClass().getResource("/movielist.csv");

  private static final Logger log = LoggerFactory.getLogger(Movie.class);

  private final MovieRepository movieRepository;

  @Override
  public void insertRecords() throws IOException {
    List<MovieDTO> movies = uploadCsvFile();
    this.save(movies);
  }

  @Override
  public List<MovieDTO> listMovies() {
    List<MovieDTO> movieDTO = new ArrayList<>();
    try {
      List<EntityMovie> movies = this.findByMovies();
      for (EntityMovie movie : movies) {
        movieDTO.add(new MovieDTO(movie));
      }
      return movieDTO;
    } catch (Exception e) {
      return movieDTO;
    }
  }

  @Override
  public void save(List<MovieDTO> films) {
    if (films.isEmpty()) {
      return;
    }
    List<EntityMovie> movies = new ArrayList<>();
    for (MovieDTO movieDTO : films) {
      movies.add(new EntityMovie(movieDTO));
    }
    movieRepository.saveAll(movies);
  }

  @Override
  public IntervalDTO findByIntervalAwards() {
    var intervalDTO = new IntervalDTO();
    intervalDTO.setMin(this.searchProducerTwoPrizesFaster());
    intervalDTO.setMax(this.searchLongestRangeProducerAwards());
    return intervalDTO;
  }

  @Override
  public void clearTable() {
    movieRepository.clearTable();
  }

  private List<EntityMovie> findByMovies() {
    List<EntityMovie> movies = movieRepository.findAll();
    if (movies.isEmpty()) {
      return new ArrayList<>();
    }
    return movies;
  }

  private IntervalAwardsDTO searchLongestRangeProducerAwards() {
    List<IntervalAwardsDTO> intervalsDTO = new ArrayList<>();
    try {
      List<EntityMovie> movies = this.findByMovies();
      if (movies.isEmpty()) {
        return new IntervalAwardsDTO();
      }
      for (EntityMovie movie : movies) {
        if (!movie.isChampion()) {
          continue;
        }
        var interval = new IntervalAwardsDTO();
        for (String producer : this.getProducers(movie)) {
          interval.setProducer(producer);
          if (intervalsDTO.contains(interval)) {
            var index = intervalsDTO.indexOf(interval);
            intervalsDTO.get(index).setFollowingWin(movie.getYear());
            intervalsDTO.get(index).setInterval(this.calculateInterval(intervalsDTO.get(index)));
          } else {
            var intervalAwardsDTO = new IntervalAwardsDTO();
            intervalAwardsDTO.setPreviousWin(movie.getYear());
            intervalAwardsDTO.setFollowingWin(movie.getYear());
            intervalAwardsDTO.setInterval(0);
            intervalAwardsDTO.setProducer(producer);
            intervalsDTO.add(intervalAwardsDTO);
          }
        }
      }
      Collections.sort(intervalsDTO);
      return intervalsDTO.get(intervalsDTO.size() - 1);
    } catch (Exception e) {
      return new IntervalAwardsDTO();
    }
  }

  private IntervalAwardsDTO searchProducerTwoPrizesFaster() {
    List<IntervalAwardsDTO> intervalsDTO = new ArrayList<>();
    try {
      List<EntityMovie> movies = this.findByMovies();
      if (movies.isEmpty()) {
        return new IntervalAwardsDTO();
      }
      extracted(intervalsDTO, movies);
      Collections.sort(intervalsDTO);
      for (IntervalAwardsDTO intervals : intervalsDTO) {
        if (intervals.getInterval() != 0) {
          return intervals;
        }
      }
      return intervalsDTO.get(0);
    } catch (Exception e) {
      return new IntervalAwardsDTO();
    }
  }

  private void extracted(List<IntervalAwardsDTO> intervalsDTO, List<EntityMovie> movies) {
    for (EntityMovie movie : movies) {
      if (!movie.isChampion()) {
        continue;
      }
      var interval = new IntervalAwardsDTO();
      for (String producer : this.getProducers(movie)) {
        interval.setProducer(producer);
        var index = intervalsDTO.indexOf(interval);
        if (intervalsDTO.contains(interval)) {
          if (!intervalsDTO.get(index).getFollowingWin().equals(intervalsDTO.get(index).getPreviousWin())) {
            continue;
          }
          intervalsDTO.get(index).setFollowingWin(movie.getYear());
          intervalsDTO.get(index).setInterval(this.calculateInterval(intervalsDTO.get(index)));
        } else {
          var intervalAwardsDTO = new IntervalAwardsDTO();
          intervalAwardsDTO.setPreviousWin(movie.getYear());
          intervalAwardsDTO.setFollowingWin(movie.getYear());
          intervalAwardsDTO.setInterval(0);
          intervalAwardsDTO.setProducer(producer);
          intervalsDTO.add(intervalAwardsDTO);
        }
      }
    }
  }

  private List<MovieDTO> uploadCsvFile() throws IOException {
    List<MovieDTO> movies = new ArrayList<>();
    try {
      assert csvFile != null;
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
        log.warn("line to csv: {}", movieFields.length);
          movies.add(new MovieDTO(movieFields[0], movieFields[1], movieFields[2], movieFields[3],
                  movieFields.length == 5 ? "yes" : ""));
        }
      }
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }
    return movies;
  }

  private List<String> getProducers(EntityMovie movie) {
    List<String> names = new ArrayList<>();
    if (!movie.getProducer().toLowerCase().contains(" and ".toLowerCase())) {
      names.add(movie.getProducer());
      return names;
    }
    String[] producersSepAnd = movie.getProducer().split(" and ");
    names.add(producersSepAnd[1]);
    if (!producersSepAnd[0].toLowerCase().contains(", ".toLowerCase())) {
      names.add(producersSepAnd[0]);
      return names;
    }
    String[] producersSepVirgule = producersSepAnd[0].split(", ");
    names.addAll(Arrays.asList(producersSepVirgule));
    return names;
  }

  private int calculateInterval(IntervalAwardsDTO intervalAwardsDTO) {
    return Integer.parseInt(intervalAwardsDTO.getFollowingWin()) - Integer
        .parseInt(intervalAwardsDTO.getPreviousWin());
  }

}
