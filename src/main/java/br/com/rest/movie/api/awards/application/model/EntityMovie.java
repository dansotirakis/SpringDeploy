package br.com.rest.movie.api.awards.application.model;

import br.com.rest.movie.api.awards.application.model.enumeration.ChampionType;
import br.com.rest.movie.api.awards.util.dto.EntityMovieDTO;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "filme")
@Builder
public class EntityMovie implements Serializable {

  private static final long serialVersionUID = 6020617195244815009L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String year;
  private String title;
  private String recordCompany;
  private String producer;
  private String champion;

  public EntityMovie() {

  }

  public EntityMovie(Long id, String year, String title, String recordCompany, String producer, String champion) {
    super();
    this.id = id;
    this.year = year;
    this.title = title;
    this.recordCompany = recordCompany;
    this.producer = producer;
    this.champion = champion;
  }

  public EntityMovie(EntityMovieDTO entityMovieDTO) {
    if (!"".equals(entityMovieDTO.getYear())) {
      this.year = entityMovieDTO.getYear();
    }
    if (!"".equals(entityMovieDTO.getTitle())) {
      this.title = entityMovieDTO.getTitle();
    }
    if (!"".equals(entityMovieDTO.getRecordCompany())) {
      this.recordCompany = entityMovieDTO.getRecordCompany();
    }
    if (!"".equals(entityMovieDTO.getProducer())) {
      this.producer = entityMovieDTO.getProducer();
    }
    this.champion = !"".equals(entityMovieDTO.getChampion())
            ? ChampionType.YES.getDescription()
            : ChampionType.NO.getDescription();
  }

  public Long getId() {
    return id;
  }

  public String toString() {
    return "Movie{year='" + year + "', title=" + title + ", recordCompany='" + recordCompany + ", producer='" + producer
        + ", champion='" + champion + "'''}";
  }

}
