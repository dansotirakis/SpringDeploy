package br.com.rest.movie.api.awards.domain;

import br.com.rest.movie.api.awards.application.model.EntityMovie;
import br.com.rest.movie.api.awards.infrastructure.repository.MovieRepository;
import br.com.rest.movie.api.awards.util.dto.EntityMovieDTO;
import br.com.rest.movie.api.awards.util.dto.IntervalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MovieTest {

    Movie movie;

    @Mock
    MovieRepository movieRepository;

    private final URL csvFile = this.getClass().getResource("/movieList.csv");

    private final ArrayList<EntityMovieDTO> movieDTOS = new ArrayList<>();
    private final ArrayList<EntityMovieDTO> movieDTOSNotChampion = new ArrayList<>();
    private final ArrayList<EntityMovie> entityMoviesNotChampion = new ArrayList<>();
    private final ArrayList<EntityMovie> entityMovies = new ArrayList<>();


    @BeforeEach
    protected void setUp() throws IOException {
        movieDTOS.add(EntityMovieDTO.builder()
                .id(1L)
                .producer("Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("yes")
                .title("Transformers: The Last Knight")
                .year("2020")
                .recordCompany("Paramount Pictures")
                .build());
        movieDTOS.add(EntityMovieDTO.builder()
                .id(2L)
                .producer("Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("yes")
                .title("Transformers: The Last Knight")
                .year("2016")
                .recordCompany("Paramount Pictures")
                .build());
        movieDTOS.add(EntityMovieDTO.builder()
                .id(3L)
                .producer("Alex Kurtzman, Chris Morgan, Sean Daniel and Sarah Bradshaw")
                .champion("yes")
                .title("The Mummy")
                .year("2014")
                .recordCompany("Universal Pictures")
                .build());
        movieDTOS.add(EntityMovieDTO.builder()
                .id(4L)
                .producer("Alex Kurtzman")
                .champion("yes")
                .title("The Mummy")
                .year("2016")
                .recordCompany("Universal Pictures")
                .build());
        movieDTOSNotChampion.add(EntityMovieDTO.builder()
                .id(1L)
                .producer("Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("")
                .title("Transformers: The Last Knight")
                .year("2020")
                .recordCompany("Paramount Pictures")
                .build());
        movieDTOSNotChampion.add(EntityMovieDTO.builder()
                .id(2L)
                .producer("Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("")
                .title("Transformers: The Last Knight")
                .year("2016")
                .recordCompany("Paramount Pictures")
                .build());
        movieDTOSNotChampion.add(EntityMovieDTO.builder()
                .id(3L)
                .producer("Alex Kurtzman, Chris Morgan, Sean Daniel and Sarah Bradshaw")
                .champion("")
                .title("The Mummy")
                .year("2014")
                .recordCompany("Universal Pictures")
                .build());
        movieDTOSNotChampion.add(EntityMovieDTO.builder()
                .id(4L)
                .producer("Alex Kurtzman")
                .champion("")
                .title("The Mummy")
                .year("2016")
                .recordCompany("Universal Pictures")
                .build());
        movieDTOS.forEach(entityMovieDTO -> entityMovies.add(new EntityMovie(entityMovieDTO)));
        movieDTOSNotChampion.forEach(entityMovieDTONotChampion -> entityMoviesNotChampion.add(new EntityMovie(entityMovieDTONotChampion)));

        movie = new Movie(movieRepository);
        movieRepository.saveAll(entityMovies);
        movie.insertRecords();
    }

    @Test
    void when_testDomain_Success() {
        when(movieRepository.findAll()).thenReturn(entityMovies);
        List<EntityMovieDTO> entityMovieDTOSResponse = movie.listMovies();
        IntervalDTO intervalDTO = movie.findByIntervalAwards();

        Assertions.assertEquals(entityMovieDTOSResponse.size(), entityMovies.size());
        Assertions.assertNotNull(intervalDTO.getMax().getFollowingWin());
        Assertions.assertNotNull(intervalDTO.getMin().getPreviousWin());
    }

    @Test
    void when_testDomain_NotChampions() {
        IntervalDTO intervalDTO = movie.findByIntervalAwards();
        Assertions.assertNull(intervalDTO.getMin().getInterval());
        Assertions.assertNull(intervalDTO.getMax().getInterval());
    }

    @Test
    void when_testDomain_NotValidChampions() {
        when(movieRepository.findAll()).thenReturn(entityMoviesNotChampion);
        IntervalDTO intervalDTO = movie.findByIntervalAwards();

        Assertions.assertNull(intervalDTO.getMin().getInterval());
        Assertions.assertNull(intervalDTO.getMax().getInterval());
    }

    @Test
    void when_testSave_IsValidRecords() {
        movie.save(movieDTOSNotChampion);
        when(movieRepository.findAll()).thenReturn(entityMovies);
        assertNotNull(movie.listMovies().get(1));
    }

    @Test
    void when_testDomain_InvalidInputFileException() throws IOException {
        assert csvFile != null;

        when(movieRepository.uploadCsvFile(any(URL.class))).thenThrow(new IOException());

        assertThrows(IOException.class, () -> movie.movieRepository.uploadCsvFile(csvFile));
    }
}