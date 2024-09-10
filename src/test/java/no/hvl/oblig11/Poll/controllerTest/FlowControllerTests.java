package no.hvl.oblig11.Poll.controllerTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import no.hvl.oblig11.Poll.models.Poll;
import no.hvl.oblig11.Poll.models.User;
import no.hvl.oblig11.Poll.models.Vote;
import no.hvl.oblig11.Poll.models.VoteOption;

/**
 * FlowControllerTests
 */
public class FlowControllerTests {

  private RestClient client = RestClient.create();

  @Test
  public void flowUserTest(){
    User user1 = new User("Jonas", "Jonas@email.com");
    User user2 = new User("Jonas2", "Jonas2@email.com");
    VoteOption o1 = new VoteOption("Melk", Instant.MAX);
    VoteOption o2 = new VoteOption("Vann", Instant.MAX);
    VoteOption o3 = new VoteOption("Brus", Instant.MAX);
    Poll poll = new Poll("Hva smaker Best?", List.of(o1,o2,o3));

    ResponseEntity<User> userEntity = client.post()
      .uri("http://localhost:8080/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .body(user1)
      .retrieve()
      .toEntity(User.class);

    user1 = userEntity.getBody();

    ResponseEntity<List<User>> allUsers = client.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>() {});

    assertTrue(user1.getId() == 0);
    assertTrue(user1.getUsername().equals("Jonas"));
    assertTrue(user1.getEmail().equals("Jonas@email.com"));
    assertTrue(user1.getVotes().isEmpty());
    assertTrue(user1.getCreatedPolls().isEmpty());

    assertTrue(allUsers.getBody().contains(user1));
    assertTrue(allUsers.getBody().size() == 1);

     userEntity = client.post()
      .uri("http://localhost:8080/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .body(user2)
      .retrieve()
      .toEntity(User.class);

    user2 = userEntity.getBody();

    assertTrue(user2.getId() == 1);
    assertTrue(user2.getUsername().equals("Jonas2"));
    assertTrue(user2.getEmail().equals("Jonas2@email.com"));
    assertTrue(user2.getVotes().isEmpty());
    assertTrue(user2.getCreatedPolls().isEmpty());

    allUsers = client.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>() {});

    assertTrue(allUsers.getBody().contains(user1));
    assertTrue(allUsers.getBody().contains(user2));
    assertTrue(allUsers.getBody().size() == 2);

    ResponseEntity<Poll> pollEntity = client.post()
      .uri("http://localhost:8080/api/v1/polls/" + user1.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .body(poll)
      .retrieve()
      .toEntity(Poll.class);


    ResponseEntity<List<Poll>> allPolls = client.get()
      .uri("http://localhost:8080/api/v1/polls")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<Poll>>() {});
    
    assertTrue(allPolls.getBody().contains(poll));

    Vote vote = new Vote(poll.getVoteOptions().get(0));

    ResponseEntity<Vote> user2VoteEntity = client.post()
      .uri("http://localhost:8080/api/v1/votes/" + user2.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .body(vote)
      .retrieve()
      .toEntity(Vote.class);

    vote = user2VoteEntity.getBody();
    
    // assertTrue(vote.getCaster().equals(user2));

    Vote updatedVote = new Vote(poll.getVoteOptions().get(1));

    System.out.println(vote.getId());

    user2VoteEntity = client.put()
      .uri("http://localhost:8080/api/v1/votes/" + vote.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .body(updatedVote)
      .retrieve()
      .toEntity(Vote.class);

    System.out.println(user2VoteEntity.getBody());

    ResponseEntity<List<Vote>> allVotes= client.get()
      .uri("http://localhost:8080/api/v1/votes")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<Vote>>() {});

    updatedVote = user2VoteEntity.getBody();

    assertTrue(allVotes.getBody().contains(updatedVote));
    assertTrue(!allVotes.getBody().contains(vote));

    pollEntity = client.delete()
      .uri("http://localhost:8080/api/v1/polls/" + poll.getId())
      .retrieve()
      .toEntity(Poll.class);

    poll = pollEntity.getBody();

    assertTrue(poll.getId() == 0);

     allVotes= client.get()
      .uri("http://localhost:8080/api/v1/votes")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<Vote>>() {});

     assertTrue(allVotes.getBody() == null);

     client.delete()
       .uri("http://localhost:8080/api/v1/users/" + user1.getId())
       .retrieve()
       .toEntity(User.class);

     client.delete()
       .uri("http://localhost:8080/api/v1/users/" + user2.getId())
       .retrieve()
       .toEntity(User.class);
  }
}
