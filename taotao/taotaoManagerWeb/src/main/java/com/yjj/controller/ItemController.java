package com.yjj.controller;

import com.yjj.pojo.TbItem;
import com.yjj.service.IitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理controller
 */

@Controller
public class ItemController  {

    @Autowired(required = false)
    private IitemService iitemService;


    @RequestMapping(value = "/item/{id}")
    @ResponseBody
    public TbItem getItemById(@PathVariable long id){
        TbItem tbItem = iitemService.selectItemById(id);
        return tbItem;
    }
}
