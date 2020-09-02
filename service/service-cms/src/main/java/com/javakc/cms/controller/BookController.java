package com.javakc.cms.controller;

import com.javakc.cms.entity.Book;
import com.javakc.cms.service.BookService;
import com.javakc.cms.vo.BookQuery;
import com.javakc.commonutils.api.APICODE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "书籍管理")
@RestController
@RequestMapping("/cms/book")
@CrossOrigin  //跨域
public class BookController {
    @Autowired
    private BookService bookService;
    @ApiOperation(value = "查询所有书籍")
    @GetMapping
    public APICODE findBook(){

        List<Book> list= bookService.findBook();
        return APICODE.OK().data("items",list);
    }

    @ApiOperation(value = "根据条件进行分页查询")
    @PostMapping("{pageNo}/{pageSize}")
    public APICODE findPageBook(@RequestBody(required = false) BookQuery bookQuery,
                                @PathVariable(name = "pageNo") int pageNo,@PathVariable(name = "pageSize") int pageSize){

        //调用service方法  根据条件查询
        Page<Book> page=bookService.findPageBook(bookQuery, pageNo,pageSize);
       //获取总数  count()
        long totalElements= page.getTotalElements();
       //当前页的数据
        List<Book>list=page.getContent();

        return APICODE.OK().data("total",totalElements).data("items",list);

    }
    /*  1、新增方法  */
    @ApiOperation(value = "新增书籍")
    @PostMapping("{saveBook}")
    public APICODE savaBook(@RequestBody Book book){
        Book book1=bookService.saveOrUpdate(book);
        System.out.println(book1.getId());
        return  APICODE.OK();
    }

    /*  2、根据ID查询数据  */
    @ApiOperation(value = "根据ID获取书籍信息")
    @GetMapping("{bookId}")
    public APICODE getBookById(@PathVariable String bookId){
        Book book=bookService.getById(bookId);
        return APICODE.OK().data("book",book);
    }

    /* 3、根据ID修改数据  */
    @ApiOperation(value = "修改书籍")
    @PostMapping("{updateBook}")
    public APICODE updateBook(@RequestBody Book book){

        bookService.saveOrUpdate(book);
        return APICODE.OK();

    }

    /* 4、根据ID删除  */
    @ApiOperation(value = "根据ID删除书籍")
    @DeleteMapping("deleteById/{bookId}")
    public APICODE deleteById(@PathVariable String bookId){
        bookService.removeById(bookId);
        return APICODE.OK();
    }



}
