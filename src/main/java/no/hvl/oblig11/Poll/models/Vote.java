package no.hvl.oblig11.Poll.models;

import java.time.Instant;

/**
 * Vote
 */
public class Vote {

  private static int idCounter = 0;
  
  private int id;
  private User caster;
  private Instant publishedAt;
  private VoteOption voteOption;

  public Vote(User caster, Instant publishedAt, VoteOption voteOption){
    this.id = generateId();
    this.caster = caster;
    this.publishedAt = publishedAt;
    this.voteOption = voteOption;
  }

  public Vote(User caster, VoteOption voteOption){
    this.id = generateId();
    this.caster = caster;
    this.publishedAt = Instant.now();
    this.voteOption = voteOption;
  }
  private synchronized int generateId(){
    int id = idCounter;
    idCounter++;
    return id;
  }

  public static int getIdCounter() {
    return idCounter;
  }

  public static void setIdCounter(int idCounter) {
    Vote.idCounter = idCounter;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getCaster() {
    return caster;
  }

  public void setCaster(User caster) {
    this.caster = caster;
  }

  public Instant getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Instant publishedAt) {
    this.publishedAt = publishedAt;
  }

  public VoteOption getVoteOption() {
    return voteOption;
  }

  public void setVoteOption(VoteOption voteOption) {
    this.voteOption = voteOption;
  }
}
