package no.hvl.oblig11.Poll.models;

import java.time.Instant;

/**
 * Vote
 */
public class Vote {

  private int id;
  private Instant publishedAt;
  private VoteOption selected;

  public Vote(VoteOption selected){
    this.selected = selected;
    this.publishedAt = Instant.now();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Instant getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Instant publishedAt) {
    this.publishedAt = publishedAt;
  }

  public VoteOption getSelected() {
    return selected;
  }

  public void setSelected(VoteOption selected) {
    this.selected = selected;
  }
}
