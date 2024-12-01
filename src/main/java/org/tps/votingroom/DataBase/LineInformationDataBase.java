package org.tps.votingroom.DataBase;

import java.util.List;

public record LineInformationDataBase (Integer ownerId, String name, List<Integer> participantsId,
                                       List<String> variantsNames, List<Integer> variantsInterestRate, Integer state) {
  public LineInformationDataBase withInterestRate(List<Integer> interestRate) {
    return new LineInformationDataBase(ownerId, name, participantsId, variantsNames, interestRate, state);
  }
}
