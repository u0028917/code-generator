package ${mapperPackage};

import ${modelPackage}.${classNameUpper};
import ${dtoPackage}.${classNameUpper}Dto;
import ${comPackage}.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface ${classNameUpper}Mapper extends BaseMapper<${classNameUpper}> {

        /**
         * 删除根据IDs
         * @param ${className} ${className}
         * @return int
         */
        int deleteByIds(${classNameUpper}Dto ${className});

        /**
         * 查询列表
         * @param ${className} ${className}
         * @return List
         */
        List<${classNameUpper}Dto> queryList(${classNameUpper}Dto ${className});

        /**
         * 获取单个
         * @param ${className} ${className}
         * @return ImageTeamDto
         */
        ${classNameUpper}Dto getOne(${classNameUpper}Dto ${className});

}