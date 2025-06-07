package com.yihe.system.mapper;


import com.yihe.system.domain.NewThreeSimultaneous;

import java.util.List;

public interface NewThreeSimultaneousMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewThreeSimultaneous row);

    int insertSelective(NewThreeSimultaneous row);

    NewThreeSimultaneous selectByPrimaryKey(Integer id);

    NewThreeSimultaneous selectOrder();

    int updateByPrimaryKeySelective(NewThreeSimultaneous row);

    int updateByPrimaryKey(NewThreeSimultaneous row);

    List<NewThreeSimultaneous> selectByPage(NewThreeSimultaneous newThreeSimultaneous);

    int selectByPageCount(NewThreeSimultaneous newThreeSimultaneous);
}