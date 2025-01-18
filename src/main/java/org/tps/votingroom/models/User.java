package org.tps.votingroom.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
}
