package no.hvl.oblig11.Poll.models;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Vote
 */
public class Vote {

  private int id;
  @JsonIgnore
  private User caster;

  private Instant publishedAt;
  private VoteOption selected;

  public Vote(){}

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

  public User getCaster() {
    return caster;
  }

  public void setCaster(User caster) {
    this.caster = caster;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Vote vote = (Vote) o;

    if (id != vote.id) return false;
    if (caster != null ? !caster.equals(vote.caster) : vote.caster != null) return false;
    if (publishedAt != null ? !publishedAt.equals(vote.publishedAt) : vote.publishedAt != null) return false;
    return selected != null ? selected.equals(vote.selected) : vote.selected == null;
  }

  @Override
  public String toString() {
    return "Vote{" +
      "id=" + id +
      // ", caster=" + caster +
      ", publishedAt=" + publishedAt +
      ", selected=" + selected +
      '}';
  }
}
