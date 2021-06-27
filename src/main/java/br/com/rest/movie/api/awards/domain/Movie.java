package br.com.rest.movie.api.awards.domain;

import br.com.rest.movie.api.awards.application.model.EntityMovie;
import br.com.rest.movie.api.awards.infrastructure.repository.MovieRepository;
import br.com.rest.movie.api.awards.util.dto.EntityMovieDTO;
import br.com.rest.movie.api.awards.util.dto.IntervalAwardsDTO;
import br.com.rest.movie.api.awards.util.dto.IntervalDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class Movie implements IMovie {

  private final URL csvFile = this.getClass().getResource("/movielist.csv");

  public MovieRepository movieRepository;


  @Override
  public void insertRecords() throws IOException {
    this.save(movieRepository.uploadCsvFile(csvFile));
  }

  @Override
  public ArrayList<EntityMovieDTO> listMovies() {
    ArrayList<EntityMovieDTO> entityMovieDTO = new ArrayList<>();
    try {
      for (EntityMovie movie :this.findByMovies()) {
        entityMovieDTO.add(new EntityMovieDTO(movie));
      }
      return entityMovieDTO;
    } catch (Exception e) {
      return entityMovieDTO;
    }
  }

  @Override
  public IntervalDTO findByIntervalAwards() {
    var intervalDTO = new IntervalDTO();
    intervalDTO.setMin(this.searchProducerTwoPrizesFaster());
    intervalDTO.setMax(this.searchLongestRangeProducerAwards());
    return intervalDTO;
  }

  public void save(List<EntityMovieDTO> films) {
    if (films.isEmpty()) {
      return;
    }
    List<EntityMovie> movies = new ArrayList<>();
    for (EntityMovieDTO entityMovieDTO : films) {
      movies.add(new EntityMovie(entityMovieDTO));
    }
    movieRepository.saveAll(movies);
  }

  public void extracted(List<IntervalAwardsDTO> intervalsDTO, List<EntityMovie> movies) {
    for (EntityMovie movie : movies) {
      if (!"yes".equalsIgnoreCase(movie.getChampion())) {
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
          intervalsDTO.add(IntervalAwardsDTO.builder()
                  .followingWin(movie.getYear())
                  .previousWin(movie.getYear())
                  .interval(0)
                  .producer(producer)
                  .build());
        }
      }
    }
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
        if (!"yes".equalsIgnoreCase(movie.getChampion())) {
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
