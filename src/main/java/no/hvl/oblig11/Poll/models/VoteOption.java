package no.hvl.oblig11.Poll.models;

/**
 * VoteOption
 */
public class VoteOption {

  private static int idCounter = 0;

  private int id;
  private String caption;
  private int presentationOrder;
  private int numOfVotes;

  public VoteOption(String caption, int presentationOrder){
    this.id = generateId();
    this.caption = caption;
    this.presentationOrder = presentationOrder;
    this.numOfVotes = 0;
  }

  private synchronized int generateId(){
    return idCounter++;
  }

  public synchronized void castVote(){
    numOfVotes++;
  }

  public synchronized void removeVote(){
    numOfVotes--;
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

  public int getNumOfVotes() {
    return numOfVotes;
  }

  public void setNumOfVotes(int numOfVotes) {
    this.numOfVotes = numOfVotes;
  }
  
  @Override
  public String toString(){
    return "Caption: " + this.getCaption() + " id: " + this.getId();
  }

  @Override
  public boolean equals(Object obj) {
    // Check if the object is compared with itself
    if (this == obj) {
      return true;
    }

    // Check if the object is of the same type
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    // Cast the object to VoteOption
    VoteOption other = (VoteOption) obj;

    // Compare id (assuming id is the unique identifier)
    return this.id == other.id;
  }


}

