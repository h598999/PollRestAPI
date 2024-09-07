package no.hvl.oblig11.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import no.hvl.oblig11.Poll.models.Poll;
import no.hvl.oblig11.Poll.models.User;
import no.hvl.oblig11.Poll.models.VoteOption;

/**
 * flowtest
 */
public class flowtest {

  private RestClient defaultClient = RestClient.create();

  @Test
  public void flowTest(){
    User user1 = new User("john_doe", "john@example.com");
    User user2 = new User("john_doe1", "john1@example.com");
    VoteOption v1 = new VoteOption("Melk", 0);
    VoteOption v2 = new VoteOption("Vann", 1);
    Poll poll1 = new Poll("Hva smaker best?", List.of(v1, v2));
    List<User> allUsers = new ArrayList<>();
    ResponseEntity<User> response1 = defaultClient.post()
      .uri("http://localhost:8080/api/v1/users")
      .body(user1)
      .contentType(MediaType.APPLICATION_JSON)
      .retrieve()
      .toEntity(User.class);

    assertTrue(response1.getStatusCode().equals(HttpStatus.CREATED));
    assertTrue(response1.getBody().equals(user1));

    allUsers.add(user1);

    ResponseEntity<List<User>> allUsersResponse = defaultClient.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>(){});

    assertTrue(allUsersResponse.getStatusCode().equals(HttpStatus.OK));
    System.out.println("Response: " + allUsersResponse.getBody().toString());
    System.out.println("Actual: " + allUsers.toString());
    assertTrue(allUsers.equals(allUsersResponse.getBody()));

    ResponseEntity<User> response2 = defaultClient.post()
      .uri("http://localhost:8080/api/v1/users")
      .body(user2)
      .contentType(MediaType.APPLICATION_JSON)
      .retrieve()
      .toEntity(User.class);
    assertTrue(response2.getStatusCode().equals(HttpStatus.CREATED));
    assertTrue(response2.getBody().equals(user2));

    allUsers.add(user2);

    allUsersResponse = defaultClient.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>(){});

    assertTrue(allUsersResponse.getStatusCode().equals(HttpStatus.OK));
    assertTrue(allUsers.equals(allUsersResponse.getBody()));

    ResponseEntity<Poll> addedPoll1 = defaultClient.post()
      .uri("http://localhost:8080/api/v1/Polls/{id}", user1.getId())
      .body(poll1)
      .contentType(MediaType.APPLICATION_JSON)
      .retrieve()
      .toEntity(Poll.class);

    System.out.println(addedPoll1);
    
    assertTrue(addedPoll1.getStatusCode().equals(HttpStatus.OK));
    System.out.println("Response: " + addedPoll1.getBody());
    System.out.println("Actual: " + poll1);
    assertTrue(addedPoll1.getBody().equals(poll1));

  }
  
}
