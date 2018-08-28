package ${controllerPackage};

import lombok.extern.slf4j.Slf4j;
import com.github.pagehelper.PageInfo;
import ${comPackage}.common.utils.Constant;
import ${comPackage}.common.utils.ExecuteResult;
import ${dtoPackage}.${classNameUpper}Dto;
import ${servicePackage}.${classNameUpper}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/**
 * Created by ${author}.
 */
@RestController
@RequestMapping("/${tableName}")
@Api(value = "这是显示值", description = "这是描述")
@Slf4j
public class ${classNameUpper}Controller {

@Autowired
private ${classNameUpper}Service ${className}Service;

    @PostMapping(value = "add")
    @ApiOperation(value = "这是方法名字", notes = "这是方法描述(${author})")
    @ApiImplicitParams({
        <#list columns as column>
            <#if column.columnNameCamel != "id" && column.columnNameCamel != "createBy"
                && column.columnNameCamel != "createTime" && column.columnNameCamel != "updateBy"
                && column.columnNameCamel != "updateTime"
                && column.columnNameCamel != "state">
            @ApiImplicitParam(name = "${column.columnNameCamel}", value = "${column.comments}",
                    required = false, paramType = "form", dataType = "${column.swaggerType}"),
            </#if>
        </#list>
    })
    public ResponseEntity<${classNameUpper}Dto> add(@ApiIgnore ${classNameUpper}Dto dto) {
        ExecuteResult<${classNameUpper}Dto> executeResult = ${className}Service.insertSelective(dto);
        return ResponseEntity.ok(executeResult.getData());
    }

    @PostMapping(value = "update")
    @ApiOperation(value = "这是方法名字", notes = "这是方法描述(${author})")
    @ApiImplicitParams({
            <#list columns as column>
                <#if column.columnNameCamel != "createBy"
                    && column.columnNameCamel != "createTime" && column.columnNameCamel != "updateBy"
                    && column.columnNameCamel != "updateTime"
                    && column.columnNameCamel != "state">
                @ApiImplicitParam(name = "${column.columnNameCamel}", value = "${column.comments}",
                required = false, paramType = "form", dataType = "${column.swaggerType}"),
                </#if>
            </#list>
    })
    public ResponseEntity<String> update(@ApiIgnore ${classNameUpper}Dto dto) {
        ExecuteResult executeResult = ${className}Service.updateByPrimaryKeySelective(dto);
        return ResponseEntity.ok(Constant.StatusManager.UP_SUCEESS);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除(批删)${comments}", notes = "删除(批删)${comments}(${author})")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "${comments}id",
                    required = true, paramType = "query", dataType = "String")
    })
    public ResponseEntity<String> delete(String ids) {
        ExecuteResult executeResult = ${className}Service.deleteByIds(Arrays.asList(ids.split(",")));
        return ResponseEntity.ok(Constant.StatusManager.DEL_SUCCESS);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询${comments}列表(模糊,分页)", notes = "查询${comments}列表(模糊,分页)(${author})")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码",
                    required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "rows", defaultValue = "10", value = "页数量",
                    required = true, paramType = "query", dataType = "int"),
    })
    public ResponseEntity<PageInfo<${classNameUpper}Dto>> list(@ApiIgnore ${classNameUpper}Dto dto) {
        PageInfo<${classNameUpper}Dto> ${className}DtoList =  ${className}Service.queryPager(dto);
        return ResponseEntity.ok(${className}DtoList);
    }

    @GetMapping(value = "/get")
    @ApiOperation(value = "根据id查询${comments}对象", notes = "根据id查询${comments}对象(${author})")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "${comments}的id",
                    required = true, paramType = "query", dataType = "int")
    })
    public ResponseEntity<${classNameUpper}Dto> get(@ApiIgnore ${classNameUpper}Dto ${className}) {
        ${classNameUpper}Dto dto = ${className}Service.selectByPrimaryKey(${className}).getData();
        return ResponseEntity.ok(dto);
    }

}
