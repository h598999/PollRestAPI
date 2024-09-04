package no.hvl.oblig11.Poll.models;

import java.time.Instant;
import java.util.List;

/**
 * Poll
 */
public class Poll {

  private static int idCounter = 0;
  
  private int id;
  private String question;
  private Instant publishedAt;
  private Instant validUntil;
  private User creator;
  private List<VoteOption> voteOptions;

  public Poll(String question, User creator, List<VoteOption> voteOptions){
    this.id = generateId();
    this.question = question;
    this.publishedAt = Instant.now();
    this.validUntil = Instant.MAX;
    this.creator = creator;
    this.voteOptions = voteOptions;
  }

  private synchronized int generateId(){
    int id = idCounter;
    idCounter++;
    return id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Instant getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Instant publishedAt) {
    this.publishedAt = publishedAt;
  }

  public Instant getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(Instant validUntil) {
    this.validUntil = validUntil;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public List<VoteOption> getVoteOptions() {
    return voteOptions;
  }

  public void setVoteOptions(List<VoteOption> voteOptions) {
    this.voteOptions = voteOptions;
  }
}
