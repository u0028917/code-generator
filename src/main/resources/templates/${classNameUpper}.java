package ${modelPackage};

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import ${comPackage}.common.base.BaseModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ${classNameUpper} extends BaseModel {

  <#list columns as column>
        <#if column.columnNameCamel != "id" && column.columnNameCamel != "createBy"
        && column.columnNameCamel != "createTime" && column.columnNameCamel != "updateBy"
        && column.columnNameCamel != "updateTime"
        && column.columnNameCamel != "state">
        <#if column.javaType =="Date" >
        @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private ${column.javaType} ${column.columnNameCamel}; //${column.comments}
        <#else>
        private ${column.javaType} ${column.columnNameCamel}; //${column.comments}
        </#if>
        </#if>
  </#list>
}
