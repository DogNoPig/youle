package com.youle.item.web;

import com.youle.common.vo.PageResult;
import com.youle.item.pojo.Spu;
import com.youle.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xw
 * @date 2019/6/27 10:07
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> list(@RequestParam(value = "key",required = false)String key,
                                           @RequestParam(value = "saleable",required = false)Boolean saleable,
                                           @RequestParam(value = "page",defaultValue = "1")Integer page,
                                           @RequestParam(value = "rows",defaultValue = "5")Integer rows){
        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,key,saleable));
    }
}
