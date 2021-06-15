package br.com.rest.movie.api.awards.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class IntervalDTO implements Serializable {

  private static final long serialVersionUID = -7137962307504317088L;
  private IntervalAwardsDTO min;
  private IntervalAwardsDTO max;

  public IntervalDTO() {
    super();
  }


}
