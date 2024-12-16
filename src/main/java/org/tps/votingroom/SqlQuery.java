package org.tps.votingroom;

public enum SqlQuery {

  A("a");

  private final String query;

  private SqlQuery(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }
}
