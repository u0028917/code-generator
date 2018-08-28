package com.camelot.personal.codegenerator.enums;

/**
 * 数据库类型.
 */
public enum DBType {
  /**
   * MYSQL
   */
  MYSQL("mysql", "MySql数据库"),
  /**
   * ORACLE
   */
  ORACLE("oracle", "Oracle数据库"),

  OTHER("other", "其他数据库");

  private final String db;

  private final String desc;

  DBType(final String db, final String desc) {
    this.db = db;
    this.desc = desc;
  }

  public String getDb() {
    return this.db;
  }

  public String getDesc() {
    return this.desc;
  }

}
