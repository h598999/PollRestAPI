package no.hvl.oblig11.Poll.models;

/**
 * VoteOption
 */
public class VoteOption {

  private static int idCounter = 0;

  private int id;
  private String caption;
  private int presentationOrder;
  private Poll poll;

  public VoteOption(String caption, int presentationOrder, Poll poll){
    this.id = generateId();
    this.caption = caption;
    this.presentationOrder = presentationOrder;
    this.poll = poll;
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
    VoteOption.idCounter = idCounter;
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

  public int getPresentationOrder() {
    return presentationOrder;
  }

  public void setPresentationOrder(int presentationOrder) {
    this.presentationOrder = presentationOrder;
  }

  public Poll getPoll() {
    return poll;
  }

  public void setPoll(Poll poll) {
    this.poll = poll;
  }
}

