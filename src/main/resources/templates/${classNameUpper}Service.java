package ${servicePackage};

import ${dtoPackage}.${classNameUpper}Dto;
import ${comPackage}.common.base.BaseService;
import ${comPackage}.common.utils.ExecuteResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ${classNameUpper}Service extends BaseService<${classNameUpper}Dto> {

        List<${classNameUpper}Dto> queryList(${classNameUpper}Dto ${className});

        PageInfo<${classNameUpper}Dto> queryPager(${classNameUpper}Dto ${className});

        ExecuteResult<Integer> deleteByIds(List<String> list);

}
