package com.camelot.personal.codegenerator.repository;

import com.camelot.personal.codegenerator.entity.TableFieldInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Oracle表字段信息.
 */
public interface OracleTableFieldInfoRepository extends JpaRepository<TableFieldInfo, Long> {

  static final String SQL =
      "SELECT A.COLUMN_NAME,A.DATA_TYPE,A.DATA_LENGTH,CASE WHEN A.DATA_TYPE='NUMBER' THEN "
          + "(CASE WHEN A.DATA_PRECISION IS NULL THEN A.DATA_TYPE "
          + "WHEN NVL(A.DATA_SCALE, 0) > 0 THEN A.DATA_TYPE||'('||A.DATA_PRECISION||','||A.DATA_SCALE||')' "
          + "ELSE A.DATA_TYPE||'('||A.DATA_PRECISION||')' END) "
          + "ELSE A.DATA_TYPE END DATA_TYPE, B.COMMENTS,DECODE(C.POSITION, '1', 'PRI') KEY "
          + "FROM USER_TAB_COLUMNS A INNER JOIN USER_COL_COMMENTS B ON A.TABLE_NAME = B.TABLE_NAME"
          + " AND A.COLUMN_NAME = B.COLUMN_NAME LEFT JOIN USER_CONSTRAINTS D "
          + "ON D.TABLE_NAME = A.TABLE_NAME AND D.CONSTRAINT_TYPE = 'P' "
          + "LEFT JOIN USER_CONS_COLUMNS C ON C.CONSTRAINT_NAME = D.CONSTRAINT_NAME "
          + "AND C.COLUMN_NAME=A.COLUMN_NAME WHERE A.TABLE_NAME = ?1 ORDER BY A.COLUMN_ID";

  /**
   * 根据表名查询字段信息.
   */
  @Query(value = SQL, nativeQuery = true)
  public List<TableFieldInfo> findByTableName(String tableName);

}
