package com.camelot.personal.codegenerator.config;

import com.camelot.personal.codegenerator.entity.ColumnModel;
import com.camelot.personal.codegenerator.entity.TableFieldInfo;
import com.camelot.personal.codegenerator.entity.TableInfo;
import com.camelot.personal.codegenerator.enums.DBType;
import com.camelot.personal.codegenerator.repository.MysqlTableFieldInfoRepository;
import com.camelot.personal.codegenerator.repository.MysqlTableInfoRepository;
import com.camelot.personal.codegenerator.repository.OracleTableFieldInfoRepository;
import com.camelot.personal.codegenerator.repository.OracleTableInfoRepository;
import com.camelot.personal.codegenerator.utils.GeneratorUtils;
import com.google.common.collect.Maps;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javassist.NotFoundException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 生成器自动配置.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({GeneratorProperties.class, DataSourceProperties.class})
public class GeneratorConfiguration {

  @Autowired
  private GeneratorProperties properties; //生成设置.

  @Autowired
  private DataSourceProperties dsProperties; //数据源设置.

  @Autowired
  private OracleTableInfoRepository oracleTableInfoRepository; //oracle数据表

  @Autowired
  private MysqlTableInfoRepository mysqlTableInfoRepository; //mysql数据表

  @Autowired
  private OracleTableFieldInfoRepository oracleTableFieldInfoRepository; //oracle表字段

  @Autowired
  private MysqlTableFieldInfoRepository mysqlTableFieldInfoRepository; //mysql表字段

  //DB类型.
  private DBType dbType;

  @PostConstruct
  public void gen() throws Exception {

    //检查配置
    initConfig();

    //查询表信息
    TableInfo tableInfo = queryTableInfo();

    //设置表别名
    tableInfo.setTableAlias(GeneratorUtils.getTableAlias(tableInfo.getTableName()));

    //查询列信息
    List<TableFieldInfo> tableFieldInfoList = queryTableFieldInfo();

    //配置freemaker引擎
    freemarker.template.Configuration conf = initConfiguration();

    //初始模板变量
    Map templateModel = initTemplateModel(tableInfo, tableFieldInfoList);

    //初始文件夹
    initDir();

    //渲染模板
    render(templateModel, conf);

    //自动打开结果
    autoOpenDir();
  }

  /**
   * 初始配置.
   */
  private void initConfig() throws SQLException {
    log.info("==========================代码生成器预热中==========================");
    log.info("author:{}", properties.getAuthor());
    log.info("genTable:{}", properties.getGenTable());
    log.info("outDir:{}", properties.getOutDir());
    dbType = getDbType(dsProperties.getUrl());
  }

  /**
   * 查询表信息.
   */
  private TableInfo queryTableInfo() throws NotFoundException {
    log.info("==========================查询表信息==========================");
    TableInfo tableInfo = null;
    switch (dbType) {
      case MYSQL:
        tableInfo =
            mysqlTableInfoRepository.findByTableName(properties.getGenTable());
        break;
      case ORACLE:
        tableInfo =
            oracleTableInfoRepository.findByTableName(properties.getGenTable());
        break;
      default:
        throw new NotFoundException("The Database's Not Supported! DBType:" + dbType);
    }
    if (null == tableInfo) {
      throw new NotFoundException("can't find this table:" + properties.getGenTable());
    }
    log.info("查询到这张表:{}", tableInfo.getComments());
    return tableInfo;
  }

  /**
   * 查询表字段信息.
   */
  private List<TableFieldInfo> queryTableFieldInfo() throws NotFoundException {
    log.info("==========================查询表字段信息==========================");
    List<TableFieldInfo> tableFieldInfoList = null;
    switch (dbType) {
      case MYSQL:
        tableFieldInfoList =
            mysqlTableFieldInfoRepository.findByTableName(properties.getGenTable());
        break;
      case ORACLE:
        tableFieldInfoList =
            oracleTableFieldInfoRepository.findByTableName(properties.getGenTable());
        break;
      case OTHER:
        break;
      default:
        throw new NotFoundException("The Database's Not Supported! DBType:" + dbType);
    }
    tableFieldInfoList.forEach(
        tableFieldInfo -> log.info(tableFieldInfo.getColumnName()));
    return tableFieldInfoList;
  }

  /**
   * 初始输出目标文件夹.
   */
  private void initDir() {
    log.info("==========================检查输出目标地址==========================");
    File dir = new File(properties.getOutDir());
    try {
      FileUtils.deleteDirectory(dir);
    } catch (IOException ioe) {
      log.error("删除文件夹出错", ioe);
    }
    if (!dir.exists()) {
      if (dir.mkdirs()) {
        log.info("创建目录：{}", properties.getOutDir());
      }
    }
  }

  /**
   * 初始化模板引擎.
   */
  private freemarker.template.Configuration initConfiguration() throws IOException {
    log.info("==========================初始化模板引擎==========================");
    freemarker.template.Configuration conf = new freemarker.template.Configuration();
    conf.setDefaultEncoding("utf-8");
    FileTemplateLoader templateLoaders = new FileTemplateLoader(
        new File(this.getClass().getResource("/").getFile(), "templates/"));
    conf.setTemplateLoader(templateLoaders);

    return conf;
  }

  /**
   * 初始化模型.
   */
  private Map initTemplateModel(TableInfo tableInfo, List<TableFieldInfo> tableFieldInfoList) {
    log.info("==========================初始化模板参数==========================");
    Map<String, Object> templateModel = Maps.newHashMap();
    String className = GeneratorUtils.underlineToCamel(tableInfo.getTableName());
    String classNameUpper = GeneratorUtils.firstToUpperCase(className);

    //JPA破玩意！！！害的我还得转个对象
    List<ColumnModel> columns = new ArrayList<>();
    tableFieldInfoList.forEach(
        tableFieldInfo -> {
          ColumnModel column = new ColumnModel();
          BeanUtils.copyProperties(tableFieldInfo, column);
          column.setColumnNameCamel(GeneratorUtils.underlineToCamel(column.getColumnName()));
          switch (dbType) {
            case MYSQL:
              column.setJavaType(
                      GeneratorUtils.transMysqlSqlType2JavaType.get(column.getDataType()));
              column.setJdbcType(
                      GeneratorUtils.transMysqlSqlType2JdbcType.get(column.getDataType()));
              column.setSwaggerType(
                      GeneratorUtils.transJavaType2SwaggerType.get(column.getJavaType()));
              break;
            case ORACLE:
              column.setJavaType(
                      GeneratorUtils.transOracleSqlType2JavaType.get(column.getDataType()));
              column.setJdbcType(
                      GeneratorUtils.transOracleSqlType2JdbcType.get(column.getDataType()));
              column.setSwaggerType(
                       GeneratorUtils.transJavaType2SwaggerType.get(column.getJavaType()));
              break;
            case OTHER:
              break;
          }
          columns.add(column);
        }
    );
    templateModel.put("tableName", tableInfo.getTableName());
    templateModel.put("tableAlias",tableInfo.getTableAlias());
    templateModel.put("comments",tableInfo.getComments());
    templateModel.put("className", className);
    templateModel.put("classNameUpper", classNameUpper);
    templateModel.put("columns", columns);
    templateModel.put("author", properties.getAuthor());
    templateModel.put("comPackage", properties.getComPackage());
    templateModel.put("mapperPackage", properties.getMapperPackage());
    templateModel.put("dtoPackage", properties.getDtoPackage());
    templateModel.put("modelPackage", properties.getModelPackage());
    templateModel.put("servicePackage", properties.getServicePackage());
    templateModel.put("implPackage", properties.getImplPackage());
    templateModel.put("controllerPackage", properties.getControllerPackage());
    return templateModel;
  }

  /**
   * 渲染模板.
   */
  private void render(Map templateModel, freemarker.template.Configuration conf)
      throws IOException, TemplateException {
    log.info("==========================模板渲染==========================");
    FileUtils
        .listFiles(new File(this.getClass().getResource("/").getFile(), "templates/"),
            TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)
        .forEach((File file) -> {
          log.info(file.getName());
          try {
            StringWriter stringout = new StringWriter();
            Template fileNametemplate = new Template("templateString...",
                new StringReader(file.getName()), conf);
            fileNametemplate.process(templateModel, stringout);
            String fileName = stringout.toString();
            Template template = conf.getTemplate(file.getName());
            Writer out = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(properties.getOutDir() + File.separator + fileName),
                    "utf-8"));
            template.process(templateModel, out);
          } catch (IOException | TemplateException ex) {
            log.error("挂了", ex);
          }
        });
  }

  /**
   * 自动打开输出目标.
   */
  private void autoOpenDir() {
    log.info("==========================生成完毕，尝试打开文件夹==========================");
    String osName = System.getProperty("os.name");
    if (osName != null) {
      try {
        if (osName.contains("Mac")) {
          Runtime.getRuntime().exec("open " + properties.getOutDir());
        } else if (osName.contains("Windows")) {
          Runtime.getRuntime().exec("cmd /c start " + properties.getOutDir());
        }
        log.info("已经帮您自动打开文件夹:{}", properties.getOutDir());
      } catch (IOException ex) {
        log.error("Oh NO,打开文件夹异常,自己去打开吧", ex);
      }
    }
    log.info("==========================代码生成器进入冷却==========================");
  }

  /**
   * 根据连接字符串获取db类型.
   */
  private DBType getDbType(String jdbcUrl) {
    if (jdbcUrl.startsWith("jdbc:mysql:") || jdbcUrl.startsWith("jdbc:cobar:")
        || jdbcUrl.startsWith("jdbc:log4jdbc:mysql:")) {
      return DBType.MYSQL;
    } else if (jdbcUrl.startsWith("jdbc:oracle:") || jdbcUrl.startsWith("jdbc:log4jdbc:oracle:")) {
      return DBType.ORACLE;
    } else {
      return DBType.OTHER;
    }
  }
}
