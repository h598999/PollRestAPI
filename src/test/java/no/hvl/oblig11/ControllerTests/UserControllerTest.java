package no.hvl.oblig11.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import no.hvl.oblig11.Poll.models.User;

/**
 * UserControllerTest
 */
public class UserControllerTest {

  // private RestClient defaultClient = RestClient.create();
  //
  // @Test
  // public void addUserTest(){
  //     User user = new User("Jonas", "Jonas@email.com");
  //     ResponseEntity<User> result = defaultClient.post()
  //       .uri("http://localhost:8080/api/v1/users")
  //       .contentType(MediaType.APPLICATION_JSON)
  //       .body(user)
  //       .retrieve()
  //       .toEntity(User.class);
  //     assertTrue(result.getStatusCode().equals(HttpStatus.CREATED));
  //     assertTrue(result.getBody().equals(user));
  //     System.out.println(result.getBody());
  // }
  
}
