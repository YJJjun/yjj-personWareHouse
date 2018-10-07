package com.yjj.service_Impl;

import com.yjj.mapper.TbItemMapper;
import com.yjj.service.IitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yjj.pojo.TbItem;

@Service
public class ItemServiceImpl implements IitemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TbItem selectItemById(long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        return tbItem;

    }
}
