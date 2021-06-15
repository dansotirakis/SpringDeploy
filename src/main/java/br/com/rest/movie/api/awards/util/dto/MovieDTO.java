package br.com.rest.movie.api.awards.util.dto;

import java.io.Serializable;

import br.com.rest.movie.api.awards.application.model.enumeration.ChampionType;
import br.com.rest.movie.api.awards.application.model.EntityMovie;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDTO implements Serializable {

  private static final long serialVersionUID = -3879461964268667743L;

  private String id;
  private String year;
  private String title;
  private String recordCompany;
  private String producer;
  private String champion;

  public MovieDTO(String year, String title, String recordCompany, String producer, String champion) {
    super();
    this.year = year;
    this.title = title;
    this.recordCompany = recordCompany;
    this.producer = (producer.replace('\"', ' ')).trim();
    this.champion = champion;
  }

  public MovieDTO(EntityMovie entityMovie) {
    super();
    if (null != entityMovie.getId()) {
      this.id = entityMovie.getId().toString();
    }
    if (!entityMovie.getYear().equals("")) {
      this.year = entityMovie.getYear();
    }
    if (!entityMovie.getTitle().equals("")) {
      this.title = entityMovie.getTitle();
    }
    if (!entityMovie.getRecordCompany().equals("")) {
      this.recordCompany = entityMovie.getRecordCompany();
    }
    if (!entityMovie.getProducer().equals("")) {
      this.producer = entityMovie.getProducer();
    }
    if (entityMovie.isChampion()) {
      this.champion = ChampionType.YES.getDescription();
    } else {
      this.champion = ChampionType.NO.getDescription();
    }
  }

  public String toString() {
    return "Movie{year='" + year + "', title=" + title + ", recordCompany='" + recordCompany + ", producer='" + producer
            + ", champion='" + champion + "'''}";
  }

}

