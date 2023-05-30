package com.employment.employments.controller;

import com.employment.employments.entity.Notice;
import com.employment.employments.service.NoticeService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.NoticeVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @PostMapping("page")
    public Result noticelist(@RequestBody NoticeVo  noticeVo){
        return noticeService.noticelist(noticeVo);
    }

    @PostMapping("add")
    public Result add(@RequestBody Notice notice){
        return noticeService.add(notice);
    }

    @PutMapping
    public Result update(@RequestBody Notice notice){
        return noticeService.update(notice);
    }
@DeleteMapping("{id}")
    public Result deleteById(@PathVariable Long id){
        return noticeService.deleteById(id);
}
@GetMapping("search")
    public Result search(@RequestParam("name") String title){
        return noticeService.search(title);
}
@GetMapping("list")
    public Result select(){
        return noticeService.list();
}
@DeleteMapping("/select/{id}")
    public Result selectNoticeById(@PathVariable("id") Long id){
        return noticeService.selectNoticeById(id);
}
@GetMapping("noticeList")
    public Result select1(){
        return noticeService.list1();
}
@GetMapping("noticeList1")
    public Result select2(){
        return noticeService.list2();
}
}
