package br.com.rest.movie.api.awards.domain;

import br.com.rest.movie.api.awards.util.dto.EntityMovieDTO;
import br.com.rest.movie.api.awards.util.dto.IntervalDTO;

import java.io.IOException;
import java.util.ArrayList;

public interface IMovie {

  ArrayList<EntityMovieDTO> listMovies();

  IntervalDTO findByIntervalAwards();

  void insertRecords() throws IOException;

}
