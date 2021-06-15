package br.com.rest.movie.api.awards.application.controller;

import br.com.rest.movie.api.awards.domain.Movie;
import br.com.rest.movie.api.awards.util.dto.IntervalDTO;
import br.com.rest.movie.api.awards.util.dto.MovieDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
@Api(value = "restMovieApi")
@RequestMapping(value = "awards")
public class ApiMovieController {

  private static final Logger log = LoggerFactory.getLogger(ApiMovieController.class);

  private final Movie movie;

  @ApiOperation(value = "Request list films")
  @GetMapping(value = "/movies")
  public ResponseEntity<List<MovieDTO>> list() {
    List<MovieDTO> movies = movie.listMovies();
    return ResponseEntity.status(HttpStatus.OK).body(movies);
  }

  @ApiOperation(value = "Request film selected champions")
  @GetMapping(value = "/interval-awards")
  public ResponseEntity<IntervalDTO> intervalAwards() {
    IntervalDTO intervalAwardsDTO = movie.findByIntervalAwards();
    return ResponseEntity.status(HttpStatus.OK).body(intervalAwardsDTO);
  }

  @ApiOperation(value = "Insert all records")
  @PostMapping(value = "/records")
  public HttpStatus postRecords() {
    try {
      movie.insertRecords();
      log.info("success posted new records");
      return HttpStatus.ACCEPTED;
    } catch (IOException ioException) {
      return HttpStatus.BAD_REQUEST;
    }
  }

}
