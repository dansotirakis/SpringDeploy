package br.com.rest.movie.api.awards.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class IntervalAwardsDTO implements Serializable, Comparable<IntervalAwardsDTO> {

  private static final long serialVersionUID = -8195197903314034973L;

  private String producer;
  private Integer interval;
  private String previousWin;
  private String followingWin;

  public IntervalAwardsDTO() {
    super();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((producer == null) ? 0 : producer.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    IntervalAwardsDTO other = (IntervalAwardsDTO) obj;
    if (producer == null) {
      return other.producer == null;
    } else {
      return producer.equals(other.producer);
    }
  }

  @Override
  public String toString() {
    return "producer : " + producer + "\t" + "interval : " + interval + "\t" + "previousWin : " + previousWin + "\t"
        + "followingWin : " + followingWin;
  }

  @Override
  public int compareTo(IntervalAwardsDTO o) {
    return getInterval().compareTo(o.getInterval());
  }

}
