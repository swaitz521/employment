package com.employment.employments.controller;

import com.employment.employments.entity.News;
import com.employment.employments.service.NewsService;
import com.employment.employments.util.Result;
import com.employment.employments.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
@PostMapping("page")
    public Result newslist(@RequestBody NewsVo newsVo){
     return newsService.pagelist(newsVo);
}
    @PreAuthorize("@ss.hasPermi('ussystem:new:inforer：add')")
@PostMapping("add")

    public Result NewsAdd(@RequestBody News news){
    return newsService.add(news);
}
@PutMapping
    public Result update(@RequestBody News news){
    return newsService.update(news);
}

@DeleteMapping("{id}")
    public Result deleteById(@PathVariable("id") long id){
    return newsService.deleteById(id);
}

@GetMapping("search")
    public Result serach(@RequestParam("name") String name ){
    return newsService.search(name);
}
@GetMapping("list")
    public Result list(){
    return newsService.selectEmployeeList();
}
@DeleteMapping("/select/{id}")
    public Result selectNewsById(@PathVariable("id") Long id){
    return newsService.selectNewsById(id);
}
}
