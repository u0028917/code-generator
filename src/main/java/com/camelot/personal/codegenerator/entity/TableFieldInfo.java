package com.camelot.personal.codegenerator.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 数据库表字段信息.
 */
@Data
@Entity
public class TableFieldInfo {

  @Id
  private String columnName; //列名

  private String dataType; //数据类型

  private Long dataLength; //数据长度

  private String comments; //字段注释

}
