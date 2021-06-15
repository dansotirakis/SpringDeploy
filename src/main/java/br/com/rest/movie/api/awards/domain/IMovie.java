package br.com.rest.movie.api.awards.domain;

import java.io.IOException;
import java.util.List;

import br.com.rest.movie.api.awards.util.dto.MovieDTO;
import br.com.rest.movie.api.awards.util.dto.IntervalDTO;

public interface IMovie {

  List<MovieDTO> listMovies();

  void save(List<MovieDTO> filmes);

  IntervalDTO findByIntervalAwards();

  void insertRecords() throws IOException;

  void clearTable();

}
