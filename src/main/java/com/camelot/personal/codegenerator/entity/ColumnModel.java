package com.camelot.personal.codegenerator.entity;

import lombok.Data;

/**
 * 列模型.
 */
@Data
public class ColumnModel {

  private String columnName; //列名称

  private String dataType; //数据类型

  private String javaType; //java类型

  private String jdbcType; //jdbc类型

  private String swaggerType;//swagger类型

  private Long dataLength; //数据长度

  private String comments; //列注释

  private String columnNameCamel; //列名驼峰

}
