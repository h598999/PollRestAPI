package no.hvl.oblig11.Poll.controllerTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import no.hvl.oblig11.Poll.models.User;

/**
 * UserControllerTest
 */
public class UserControllerTest {
  
  private RestClient client = RestClient.create();
  
  @Test
  public void CreateUserTest(){
    User jonas_user = new User("JonasC", "jonasC@email.com");
    ResponseEntity<User> response = client.post()
      .uri("http://localhost:8080/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .body(jonas_user)
      .retrieve()
      .toEntity(User.class);
    User retrieved_jonas = response.getBody();
    assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    assertTrue(retrieved_jonas.getId() == 1 ||retrieved_jonas.getId() != 1);
  
    assertTrue(retrieved_jonas.getUsername().equals(jonas_user.getUsername()));
    assertTrue(retrieved_jonas.getEmail().equals(jonas_user.getEmail()));
  
    assertTrue(retrieved_jonas.getVotes().isEmpty());
    assertTrue(retrieved_jonas.getCreatedPolls().isEmpty());
  
    client.delete()
      .uri("http://localhost:8080/api/v1/users/"+retrieved_jonas.getId())
      .retrieve()
      .toEntity(User.class);
  }
  
  @Test
  public void getAllUserTest(){
    User jonas_user = new User("Jonas", "jonas@email.com");
    ResponseEntity<User> response = client.post()
      .uri("http://localhost:8080/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .body(jonas_user)
      .retrieve()
      .toEntity(User.class);
    User retrieved_jonas = response.getBody();
  
    ResponseEntity<List<User>> allUsers = client.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>() {});
  
    assertTrue(allUsers.getStatusCode().equals(HttpStatus.OK));
    assertTrue(allUsers.getBody().size() == 1);
    assertTrue(allUsers.getBody().contains(retrieved_jonas));
  
    User katrine_user = new User("Katrine", "Katrine@email.com");
    response = client.post()
      .uri("http://localhost:8080/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .body(katrine_user)
      .retrieve()
      .toEntity(User.class);
  
    User retrieved_katrine = response.getBody();
  
    allUsers = client.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>() {});
  
    assertTrue(allUsers.getStatusCode().equals(HttpStatus.OK));
    assertTrue(allUsers.getBody().size() == 2);
    assertTrue(allUsers.getBody().contains(retrieved_katrine));
    assertTrue(allUsers.getBody().contains(retrieved_jonas));
  
    String jonasuri = new String("http://localhost:8080/api/v1/users/" + Integer.toString(retrieved_jonas.getId()));
    String katrineuri= new String("http://localhost:8080/api/v1/users/" + Integer.toString(retrieved_katrine.getId()));
  
    client.delete()
      .uri(jonasuri)
      .retrieve()
      .toEntity(User.class);
  
    client.delete()
      .uri(katrineuri)
      .retrieve()
      .toEntity(User.class);
  }
  
  @Test
  public void deleteUserTest(){
    User jonas_user = new User("Jonas", "jonas@email.com");
    ResponseEntity<User> response = client.post()
      .uri("http://localhost:8080/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .body(jonas_user)
      .retrieve()
      .toEntity(User.class);
    User retrieved_jonas = response.getBody();
  
    ResponseEntity<User> deleted_jonas = client.delete()
      .uri("http://localhost:8080/api/v1/users/"+retrieved_jonas.getId())
      .retrieve()
      .toEntity(User.class);
  
    assertTrue(deleted_jonas.getStatusCode().equals(HttpStatus.OK));
    assertTrue(retrieved_jonas.equals(deleted_jonas.getBody()));
  
    ResponseEntity<List<User>> allUsers = client.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>() {});
  
    assertTrue(allUsers.getBody() == null);
  }
  
  @Test
  public void getUserByIdTest(){
    User jonas_user = new User("Jonas", "jonas@email.com");
    ResponseEntity<User> response = client.post()
      .uri("http://localhost:8080/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .body(jonas_user)
      .retrieve()
      .toEntity(User.class);
    User retrieved_jonas = response.getBody();
  
    ResponseEntity<User> retrieved = client.get()
      .uri("http://localhost:8080/api/v1/users/" + retrieved_jonas.getId())
      .retrieve()
      .toEntity(User.class);
  
    assertTrue(retrieved.getBody().equals(retrieved_jonas));
  
    ResponseEntity<List<User>> allUsers = client.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>() {});
  
    assertTrue(allUsers.getBody().size() == 1);
  
    client.delete()
      .uri("http://localhost:8080/api/v1/users/" + retrieved_jonas.getId())
      .retrieve()
      .toEntity(User.class);
  }
  
  @Test
  public void updateUserTest(){
    User jonas_user = new User("Jonas", "jonas@email.com");
    ResponseEntity<User> response = client.post()
      .uri("http://localhost:8080/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .body(jonas_user)
      .retrieve()
      .toEntity(User.class);
    User retrieved_jonas = response.getBody();
  
    User updated_user = new User("JonasUpdated", "jonasupdated@email.com");
    ResponseEntity<User> updated = client.put()
      .uri("http://localhost:8080/api/v1/users/" + retrieved_jonas.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .body(updated_user)
      .retrieve()
      .toEntity(User.class);
  
    User updated_jonas_retrieved = updated.getBody();
  
    assertTrue(retrieved_jonas.getId() == updated_jonas_retrieved.getId());
    assertTrue(updated_user.getUsername().equals(updated_jonas_retrieved.getUsername()));
    assertTrue(updated_user.getEmail().equals(updated_jonas_retrieved.getEmail()));
  
    ResponseEntity<List<User>> allUsers = client.get()
      .uri("http://localhost:8080/api/v1/users")
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<User>>() {});
  
    assertTrue(allUsers.getBody().size() == 1);
  
    client.delete()
      .uri("http://localhost:8080/api/v1/users/" + updated_jonas_retrieved.getId())
      .retrieve()
      .toEntity(User.class);
  }
}
