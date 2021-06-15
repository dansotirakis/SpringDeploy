package br.com.rest.movie.api.awards.application.model.enumeration;

public enum ChampionType {

  YES("yes"), NO("");

  private final String description;

  ChampionType(String desc) {
    this.description = desc;
  }

  public String getDescription() {
    return this.description;
  }

}
