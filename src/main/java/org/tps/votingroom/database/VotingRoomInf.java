package org.tps.votingroom.database;

import java.util.List;

public record VotingRoomInf (Integer ownerId, String name, List<Integer> participantsId,
                                       List<String> variantsNames, List<Integer> variantsInterestRate, Integer state) {
  public VotingRoomInf withInterestRate(List<Integer> interestRate) {
    return new VotingRoomInf(ownerId, name, participantsId, variantsNames, interestRate, state);
  }
}
