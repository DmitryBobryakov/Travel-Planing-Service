package org.tps.votingroom.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VotingRoomInfo {
  private Integer ownerId;
  private String name;
  private List<Integer> participantsId;
  private List<String> variantsNames;
  private List<Integer> variantsInterestRate;
  private Integer state;
}
