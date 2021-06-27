package br.com.rest.movie.api.awards.application.controller;

import br.com.rest.movie.api.awards.domain.Movie;
import br.com.rest.movie.api.awards.util.dto.EntityMovieDTO;
import br.com.rest.movie.api.awards.util.dto.IntervalDTO;
import groovy.transform.Synchronized;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
@Api(value = "restMovieApi")
@RequestMapping(value = "awards")
public class ApiMovieController {

  private final Movie movie;

  @ApiOperation(value = "Request list films")
  @Synchronized
  @GetMapping( "/movies")
  public ResponseEntity<List<EntityMovieDTO>> list() {
    List<EntityMovieDTO> movies = movie.listMovies();
    return ResponseEntity.status(HttpStatus.OK).body(movies);
  }

  @ApiOperation(value = "Request film selected champions")
  @Synchronized
  @GetMapping("/interval-awards")
  public ResponseEntity<IntervalDTO> intervalAwards() {
    return ResponseEntity.status(HttpStatus.OK).body(movie.findByIntervalAwards());
  }

  @ApiOperation(value = "Insert all records")
  @Synchronized
  @PostMapping("/records")
  public ResponseEntity<HttpStatus> postRecords() {
    try {
      movie.insertRecords();
      log.info("success posted new records");
      return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    } catch (IOException ioException) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
  }

}
