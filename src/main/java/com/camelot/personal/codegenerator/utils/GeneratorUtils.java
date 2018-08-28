package com.camelot.personal.codegenerator.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成辅助工具类.
 */
public class GeneratorUtils {

    /**
     * 空字符
     */
    public static final String EMPTY = "";

    /**
     * 下划线字符
     */
    public static final char UNDERLINE = '_';


    public static boolean isEmpty(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * <p> 字符串驼峰转下划线格式. </p>
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String camelToUnderline(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * <p> 字符串下划线转驼峰格式. </p>
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String underlineToCamel(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        String temp = param.toLowerCase();
        int len = temp.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = temp.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(temp.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getTableAlias(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        String temp = param.toLowerCase();
        StringBuilder sb = new StringBuilder(getLineNum(temp, UNDERLINE) + 1);
        int f = 0;
        if (temp.indexOf(UNDERLINE) < 0) {
            return sb.append(temp.charAt(0)).toString();
        }
        for (int i = 0; i < temp.length(); i++) {
            char c = temp.charAt(i);
            if (i == f) {
                sb.append(c);
            } else {
                if (c == UNDERLINE) {
                    f = i + 1;
                }
            }
        }

        return sb.toString();
    }

    private static int getLineNum(String param, char value) {
        if (isEmpty(param)) {
            return 0;
        }
        if (param.indexOf(value) < 0) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < param.length(); i++) {
            char c = param.charAt(i);
            if (c == value) {
                res++;
            }
        }
        return res;
    }

    /**
     * <p> 首字母转换小写. </p>
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String firstToLowerCase(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder(param.length());
        sb.append(param.substring(0, 1).toLowerCase());
        sb.append(param.substring(1));
        return sb.toString();
    }

    public static String firstToUpperCase(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder(param.length());
        sb.append(param.substring(0, 1).toUpperCase());
        sb.append(param.substring(1));
        return sb.toString();
    }


    public final static Map<String, String> transOracleSqlType2JavaType = new HashMap<>();

    static {
        transOracleSqlType2JavaType.put("NUMBER", "Long");
        transOracleSqlType2JavaType.put("VARCHAR2", "String");
        transOracleSqlType2JavaType.put("CHAR", "String");
        transOracleSqlType2JavaType.put("CLOB", "String");
        transOracleSqlType2JavaType.put("DATE", "Date");
    }


    public final static Map<String, String> transMysqlSqlType2JavaType = new HashMap<>();

    static {
        transMysqlSqlType2JavaType.put("char", "String");
        transMysqlSqlType2JavaType.put("varchar", "String");
        transMysqlSqlType2JavaType.put("text", "String");
        transMysqlSqlType2JavaType.put("mediumtext", "String");
        transMysqlSqlType2JavaType.put("longtext", "String");
        transMysqlSqlType2JavaType.put("int", "Integer");
        transMysqlSqlType2JavaType.put("bigint", "Long");
        transMysqlSqlType2JavaType.put("tinyint", "Long");
        transMysqlSqlType2JavaType.put("decimal", "BigDecimal");
        transMysqlSqlType2JavaType.put("smallint", "Long");
        transMysqlSqlType2JavaType.put("datetime", "Date");
        transMysqlSqlType2JavaType.put("date", "Date");
        transMysqlSqlType2JavaType.put("timestamp", "Date");
        transMysqlSqlType2JavaType.put("double", "Double");
    }

    public final static Map<String, String> transOracleSqlType2JdbcType = new HashMap<>();

    static {
        transOracleSqlType2JdbcType.put("NUMBER", "DECIMAL");
        transOracleSqlType2JdbcType.put("VARCHAR2", "VARCHAR");
        transOracleSqlType2JdbcType.put("CLOB", "CLOB");
        transOracleSqlType2JdbcType.put("CHAR", "VARCHAR");
        transOracleSqlType2JdbcType.put("DATE", "DATE");
    }

    public final static Map<String, String> transMysqlSqlType2JdbcType = new HashMap<>();

    static {
        transMysqlSqlType2JdbcType.put("char", "VARCHAR");
        transMysqlSqlType2JdbcType.put("varchar", "VARCHAR");
        transMysqlSqlType2JdbcType.put("text", "VARCHAR");
        transMysqlSqlType2JdbcType.put("mediumtext", "VARCHAR");
        transMysqlSqlType2JdbcType.put("longtext", "VARCHAR");
        transMysqlSqlType2JdbcType.put("int", "INTEGER");
        transMysqlSqlType2JdbcType.put("bigint", "INTEGER");
        transMysqlSqlType2JdbcType.put("tinyint", "INTEGER");
        transMysqlSqlType2JdbcType.put("decimal", "DECIMAL");
        transMysqlSqlType2JdbcType.put("smallint", "INTEGER");
        transMysqlSqlType2JdbcType.put("datetime", "INTEGER");
        transMysqlSqlType2JdbcType.put("double", "INTEGER");
        transMysqlSqlType2JdbcType.put("date", "TIMESTAMP");
        transMysqlSqlType2JdbcType.put("timestamp", "TIMESTAMP");
    }

    public final static Map<String, String> transJavaType2SwaggerType = new HashMap<>();

    static {
        transJavaType2SwaggerType.put("String","string");
        transJavaType2SwaggerType.put("Integer","int");
        transJavaType2SwaggerType.put("Long","int");
        transJavaType2SwaggerType.put("BigDecimal","double");
        transJavaType2SwaggerType.put("Date","date");
        transJavaType2SwaggerType.put("Double","double");
    }

}
