package com.camelot.personal.codegenerator.repository;

import com.camelot.personal.codegenerator.entity.TableInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * mysql表信息.
 */
public interface MysqlTableInfoRepository extends JpaRepository<TableInfo, Long> {

  static final String SQL =
      "SELECT TABLE_NAME,TABLE_TYPE,TABLE_COMMENT AS COMMENTS  FROM INFORMATION_SCHEMA.TABLES "
          + "WHERE TABLE_NAME = ?1 ";

  /**
   * 根据表名查询表信息.
   */
  @Query(value = SQL, nativeQuery = true)
  TableInfo findByTableName(String tableName);

}
