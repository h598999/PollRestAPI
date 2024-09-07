package no.hvl.oblig11.Poll.models;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Poll
 */
public class Poll {

  private static int idCounter = 0;
  
  private int id;
  private String question;
  private Instant publishedAt;
  private Instant validUntil;
  @JsonIgnore
  private User creator;
  private List<VoteOption> voteOptions;

  public Poll(){
    this.id = generateId();
    this.publishedAt = Instant.now();
    this.validUntil = Instant.MAX;
  }

  public Poll(String question, User creator, List<VoteOption> voteOptions){
    this.id = generateId();
    this.question = question;
    this.publishedAt = Instant.now();
    this.validUntil = Instant.MAX;
    this.creator = creator;
    this.voteOptions = voteOptions;
  }

  public Poll(String question, List<VoteOption> voteOptions){
    this.id = generateId();
    this.question = question;
    this.publishedAt = Instant.now();
    this.validUntil = Instant.MAX;
    this.voteOptions = voteOptions;
  }

  private synchronized int generateId(){
    return idCounter++;
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

  public static int getIdCounter() {
    return idCounter;
  }

  public static void setIdCounter(int idCounter) {
    Poll.idCounter = idCounter;
  }

  @Override
  public String toString(){
    return "ID: " + id + " CREATOR: " + creator.getUsername() + " GET question: " + getQuestion() + " VoteOptions: " + getVoteOptions();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true; // Check for reference equality
    if (o == null || getClass() != o.getClass()) return false; // Check for null or different class

    Poll poll = (Poll) o;

    // Check if all relevant fields are equal
    return id == poll.id &&
      Objects.equals(question, poll.question) &&
      Objects.equals(publishedAt, poll.publishedAt) &&
      Objects.equals(validUntil, poll.validUntil) &&
      Objects.equals(creator, poll.creator) &&
      Objects.equals(voteOptions, poll.voteOptions);
  }
  
}
