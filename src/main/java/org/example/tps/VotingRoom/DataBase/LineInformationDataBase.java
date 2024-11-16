package org.example.DataBase;

public record LineInformationDataBase (Integer ownerId, String name, Integer[] participantsId,
                                       String[] variantsNames, Integer[] variantsInterestRate, Integer state) {
  public LineInformationDataBase withInterestRate(Integer[] interestRate) {
    return new LineInformationDataBase(ownerId, name, participantsId, variantsNames, interestRate, state);
  }
}
