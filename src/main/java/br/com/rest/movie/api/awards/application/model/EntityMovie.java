package br.com.rest.movie.api.awards.application.model;

import java.io.Serializable;

import br.com.rest.movie.api.awards.util.dto.MovieDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Data
@Table(name = "filme")
public class EntityMovie implements Serializable {

  private static final long serialVersionUID = 6020617195244815009L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "year", nullable = false)
  private String year;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "recordCompany", nullable = false)
  private String recordCompany;

  @Column(name = "producer", nullable = false)
  private String producer;

  @Column(name = "champion")
  private boolean champion;

  public EntityMovie() {

  }

  public EntityMovie(Long id, String year, String title, String recordCompany, String producer, Boolean champion) {
    super();
    this.id = id;
    this.year = year;
    this.title = title;
    this.recordCompany = recordCompany;
    this.producer = producer;
    this.champion = champion;
  }

  public EntityMovie(MovieDTO movieDTO) {
    if (!"".equals(movieDTO.getYear())) {
      this.year = movieDTO.getYear();
    }
    if (!"".equals(movieDTO.getTitle())) {
      this.title = movieDTO.getTitle();
    }
    if (!"".equals(movieDTO.getRecordCompany())) {
      this.recordCompany = movieDTO.getRecordCompany();
    }
    if (!"".equals(movieDTO.getProducer())) {
      this.producer = movieDTO.getProducer();
    }
    this.champion = !"".equals(movieDTO.getChampion());
  }

  public Long getId() {
    return id;
  }

  public String toString() {
    return "Movie{year='" + year + "', title=" + title + ", recordCompany='" + recordCompany + ", producer='" + producer
        + ", champion='" + champion + "'''}";
  }

}
