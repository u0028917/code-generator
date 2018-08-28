package com.camelot.personal.codegenerator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 生成器配置属性.
 */
@Data
@ConfigurationProperties(prefix = "generator")
public class GeneratorProperties {

  private String author; //作者

  private String outDir; //输出目录

  private String genTable; //生成目标表

  private String comPackage; //包名

  private String mapperPackage;

  private String dtoPackage;

  private String modelPackage;

  private String servicePackage;

  private String implPackage;

  private String controllerPackage;

}
