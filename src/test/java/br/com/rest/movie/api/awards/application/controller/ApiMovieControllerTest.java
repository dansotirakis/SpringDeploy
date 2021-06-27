package br.com.rest.movie.api.awards.application.controller;

import br.com.rest.movie.api.awards.application.model.EntityMovie;
import br.com.rest.movie.api.awards.domain.Movie;
import br.com.rest.movie.api.awards.util.dto.EntityMovieDTO;
import br.com.rest.movie.api.awards.util.dto.IntervalAwardsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ApiMovieController.class)
@ActiveProfiles("test")
class ApiMovieControllerTest {

    private static final String BASE_URL = "/awards";
    private final URL csvFile = this.getClass().getResource("/movielistTest.csv");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Movie movie;

    private final ArrayList<EntityMovieDTO> movieDTOS = new ArrayList<>();
    private final ArrayList<EntityMovie> entityMovies = new ArrayList<>();
    private final ArrayList<IntervalAwardsDTO> intervalAwardsDTOS = new ArrayList<>();

    @BeforeEach
    void setUp() {
        movieDTOS.add(EntityMovieDTO.builder()
                .id(1L)
                .producer("Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("yes")
                .title("Transformers: The Last Knight")
                .year("2017")
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
                .year("2018")
                .recordCompany("Universal Pictures")
                .build());
        movieDTOS.add(EntityMovieDTO.builder()
                .id(4L)
                .producer("Alex Kurtzman, Chris Morgan, Sean Daniel and Sarah Bradshaw")
                .champion("yes")
                .title("The Mummy")
                .year("2015")
                .recordCompany("Universal Pictures")
                .build());

        movieDTOS.forEach(entityMovieDTO -> entityMovies.add(new EntityMovie(entityMovieDTO)));

        intervalAwardsDTOS.add(IntervalAwardsDTO.builder()
                .interval(3)
                .producer("Alex Kurtzman, Chris Morgan, Sean Daniel and Sarah Bradshaw")
                .followingWin("2018")
                .previousWin("2015")
                .build());
        intervalAwardsDTOS.add(IntervalAwardsDTO.builder()
                .interval(1)
                .producer("Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .followingWin("2017")
                .previousWin("2016")
                .build());
    }

    @Test
    void testPostRecords() throws Exception {

        given(movie.uploadCsvFile(csvFile)).willReturn(movieDTOS);

        this.mockMvc.perform(post(BASE_URL+"/records"))
                .andExpect(status().isAccepted());
    }

    @Test
    void testList() throws Exception {

        given(movie.listMovies()).willReturn(movieDTOS);

        this.mockMvc.perform(get(BASE_URL+"/movies"))
                .andExpect(status().isOk());
    }

    @Test
    void testIntervalAwards() throws Exception {

        doCallRealMethod().when(movie).extracted(intervalAwardsDTOS, entityMovies);

        this.mockMvc.perform(get(BASE_URL+"/interval-awards"))
                .andExpect(status().isOk());
    }
}