package no.hvl.oblig11.Poll.models;
/**
 * Poll
 */

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Poll{

  private int id;
  private String question;
  @JsonIgnore
  private User creator;
  private Instant publishedAt;
  private Instant validUntil;
  private List<VoteOption> voteOptions;

  public Poll(String question, List<VoteOption> voteOptions){
    this.question = question;
    this.voteOptions = voteOptions;
    this.validUntil = Instant.MAX;
    this.publishedAt = Instant.now();
  }

  public boolean addVoteOption(VoteOption voteOption){
    if (voteOptions.contains(voteOption)){
      return false;
    }
    return voteOptions.add(voteOption);
  }

  public String getQuestion() {
    return question;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
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

  public List<VoteOption> getVoteOptions() {
    return voteOptions;
  }

  public void setVoteOptions(List<VoteOption> voteOptions) {
    this.voteOptions = voteOptions;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Poll{" +
      "id=" + id +
      ", question='" + question + '\'' +
      ", creator=" + (creator != null ? creator.getUsername() : "null") +
      ", publishedAt=" + publishedAt +
      ", validUntil=" + validUntil +
      ", voteOptions=" + voteOptions +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Poll poll = (Poll) o;

    if (id != poll.id) return false;
    if (!question.equals(poll.question)) return false;
    return creator != null ? creator.equals(poll.creator) : poll.creator == null;
  }

}
