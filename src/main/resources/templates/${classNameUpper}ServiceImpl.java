package ${implPackage};

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${comPackage}.common.utils.ExecuteResult;
import ${dtoPackage}.${classNameUpper}Dto;
import ${mapperPackage}.${classNameUpper}Mapper;
import ${modelPackage}.${classNameUpper};
import ${servicePackage}.${classNameUpper}Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ${classNameUpper}ServiceImpl implements ${classNameUpper}Service{

  @Autowired
  private ${classNameUpper}Mapper ${className}Mapper;


  @Override
  public ExecuteResult<${classNameUpper}Dto> insertSelective(${classNameUpper}Dto dto){
    ${classNameUpper} ${className} = new ${classNameUpper}();
    BeanUtils.copyProperties(dto,${className});
    ${className}Mapper.insertSelective(${className});
    ExecuteResult<${classNameUpper}Dto> result = new ExecuteResult<>();
    BeanUtils.copyProperties(${className},dto);
    result.setData(dto);
    return result;
  }


  @Override
  public ExecuteResult<Integer> updateByPrimaryKeySelective(${classNameUpper}Dto dto){
    ${classNameUpper} ${className} = new ${classNameUpper}();
    BeanUtils.copyProperties(dto,${className});
    int num = ${className}Mapper.updateByPrimaryKeySelective(${className});
    ExecuteResult<Integer> result = new ExecuteResult<>();
    result.setData(num);
    return result;
  }


  @Override
  public ExecuteResult<Integer> deleteByPrimaryKey(${classNameUpper}Dto dto){
    ${classNameUpper} ${className} = new ${classNameUpper}();
    BeanUtils.copyProperties(dto,${className});
    ExecuteResult<Integer> result = new ExecuteResult<>();
    result.setData(${className}Mapper.deleteByPrimaryKey(${className}));
    return result;
  }

    @Override
    public ExecuteResult<${classNameUpper}Dto> selectByPrimaryKey(${classNameUpper}Dto obj) {
    ${classNameUpper}Dto ${className}Dto = ${className}Mapper.getOne(obj);
    ExecuteResult<${classNameUpper}Dto> result = new ExecuteResult<>();
    result.setData(${className}Dto);
    return result;
    }

    @Override
    public ExecuteResult<Integer> deleteByIds(List<String> list) {
    ${classNameUpper}Dto dto = new ${classNameUpper}Dto();
    dto.setIds(list);
    int num = ${className}Mapper.deleteByIds(dto);
    ExecuteResult<Integer> result = new ExecuteResult<>();
    result.setData(num);
    return result;
    }

    @Override
    public List<${classNameUpper}Dto> queryList(${classNameUpper}Dto dto) {
    List<${classNameUpper}Dto> list = ${className}Mapper.queryList(dto);
    return list;
    }

    @Override
    public PageInfo<${classNameUpper}Dto> queryPager(${classNameUpper}Dto dto) {
    PageHelper.startPage(dto.getPage(), dto.getRows());
    List<${classNameUpper}Dto> logRecordList = ${className}Mapper.queryList(dto);
    PageInfo<${classNameUpper}Dto> pageResult = new PageInfo<>(logRecordList);
    pageResult.setList(logRecordList);
    return pageResult;
    }

}
