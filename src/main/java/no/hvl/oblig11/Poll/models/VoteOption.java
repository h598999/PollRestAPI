package no.hvl.oblig11.Poll.models;

import java.time.Instant;

/**
 * VoteOption
 */
public class VoteOption {

  private int id;

  private String caption;
  private Instant validUntil;

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
}
