package com.yihe.system.service;

import com.yihe.system.domain.NewThreeSimultaneous;

import java.util.List;

public interface NewThreeSimultaneousService {
    int insetNew(NewThreeSimultaneous newThreeSimultaneous);

    int updateNew(NewThreeSimultaneous newThreeSimultaneous);

    NewThreeSimultaneous selectById(NewThreeSimultaneous newThreeSimultaneous);

    List<NewThreeSimultaneous> selectByPage(NewThreeSimultaneous newThreeSimultaneous);

    int selectByPageCount(NewThreeSimultaneous newThreeSimultaneous);

    int deleteById(NewThreeSimultaneous newThreeSimultaneous);
}
