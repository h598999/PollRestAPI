package no.hvl.oblig11.Poll.models;

import java.time.Instant;

/**
 * VoteOption
 */
public class VoteOption {

  private int id;

  private String caption;
  private Instant validUntil;

  private int numberOfVotes = 0;

  public VoteOption(String caption, Instant validUntil){
    this.caption = caption;
    this.validUntil = validUntil;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public Instant getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(Instant validUntil) {
    this.validUntil = validUntil;
  }

  public int incrementNumberOfVotes() {
    numberOfVotes++;
    return numberOfVotes;
  }

  public int decreaseNumberOfVotes() {
    numberOfVotes--;
    return numberOfVotes;
  }

  public int getNumberOfVotes() {
    return numberOfVotes;
  }

  public void setNumberOfVotes(int numberOfVotes) {
    this.numberOfVotes = numberOfVotes;
  }

  @Override
  public String toString() {
    return "VoteOption{" +
      "id=" + id +
      ", caption='" + caption + '\'' +
      ", validUntil=" + validUntil +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VoteOption that = (VoteOption) o;

    if (id != that.id) return false;
    if (!caption.equals(that.caption)) return false;
    return validUntil.equals(that.validUntil);
  }
}
