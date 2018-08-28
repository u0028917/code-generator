package com.camelot.personal.codegenerator.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 数据表主信息.
 */
@Data
@Entity
public class TableInfo {

  @Id
  private String tableName; //表名

  private String tableType; //表类型

  private String comments; //表注释
  @Transient
  private String tableAlias; //表别名（下划线间隔首字母）

}
