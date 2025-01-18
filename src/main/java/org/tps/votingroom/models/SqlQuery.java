package org.tps.votingroom.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SqlQuery {
  SELECT_INFORMATION_FROM_VOTINGROOM("SELECT * FROM votingroom WHERE room_id = ?"),
  UPDATE_INTEREST_RATE("UPDATE votingroom SET variants_interest_rate[?] = ? WHERE room_id = ?"),
  SELECT_INFORMATION_FROM_USER("SELECT * FROM \"user\" WHERE id = ?"),
  UPDATE_PARTICIPANTS_ID("UPDATE votingroom SET participants_id = ? WHERE room_id = ?"),
  UPDATE_STATE("UPDATE votingroom SET state = 0 WHERE room_id = ?");
  private final String query;
}
