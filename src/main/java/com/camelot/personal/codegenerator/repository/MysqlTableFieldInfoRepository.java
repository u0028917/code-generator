package com.camelot.personal.codegenerator.repository;

import com.camelot.personal.codegenerator.entity.TableFieldInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 查询mysql表字段信息.
 */
public interface MysqlTableFieldInfoRepository extends JpaRepository<TableFieldInfo, Long> {

  static final String SQL =
      "SELECT COLUMN_NAME,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH as DATA_LENGTH,COLUMN_COMMENT as COMMENTS "
          + "FROM information_schema.COLUMNS "
          + "WHERE TABLE_NAME = ?1 ";

  /**
   * 根据数据库表查询表字段信息.
   */
  @Query(value = SQL, nativeQuery = true)
  public List<TableFieldInfo> findByTableName(String tableName);

}
