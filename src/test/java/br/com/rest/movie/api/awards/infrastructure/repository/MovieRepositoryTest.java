package br.com.rest.movie.api.awards.infrastructure.repository;

import br.com.rest.movie.api.awards.util.dto.EntityMovieDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    MovieRepository repository;

    BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);

    private final URL csv = this.getClass().getResource("/movieListTest.csv");
    private final URL csvFail = this.getClass().getResource("/w.csv");


    private final ArrayList<EntityMovieDTO> movieDTOS = new ArrayList<>();


    @BeforeEach
    protected void setUp() {
        movieDTOS.add(EntityMovieDTO.builder()
                .id(1L)
                .producer("Don Murphy, Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("yes")
                .title("Transformers: The Last Knight")
                .year("2020")
                .recordCompany("Paramount Pictures")
                .build());
        movieDTOS.add(EntityMovieDTO.builder()
                .id(2L)
                .producer("Don Murphy, Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("yes")
                .title("Transformers: The Last Knight")
                .year("2019")
                .recordCompany("Paramount Pictures")
                .build());
        movieDTOS.add(EntityMovieDTO.builder()
                .id(3L)
                .producer("Don Murphy, Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("yes")
                .title("Transformers: The Last Knight")
                .year("2018")
                .recordCompany("Paramount Pictures")
                .build());
        movieDTOS.add(EntityMovieDTO.builder()
                .id(4L)
                .producer("Don Murphy, Tom DeSanto, Lorenzo di Bonaventura and Ian Bryce")
                .champion("yes")
                .title("Transformers: The Last Knight")
                .year("2017")
                .recordCompany("Paramount Pictures")
                .build());
    }

    @Test
    void when_testUploadCsvFile_shouldNotUpdateCsv() throws IOException {
       Assertions.assertEquals(repository.uploadCsvFile(csv).get(0).getProducer(), movieDTOS.get(0).getProducer());
    }

    @Test
    void when_testUploadCsvFile_shouldException() throws IOException {
        when(bufferedReader.readLine())
                .thenThrow(new IOException());

        assertThrows(IOException.class, () -> repository.uploadCsvFile(csvFail));
    }
}