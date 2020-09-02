package com.javakc.cms.service;

import com.javakc.cms.dao.BookDao;
import com.javakc.cms.entity.Book;
import com.javakc.cms.vo.BookQuery;
import com.javakc.commonutils.jpa.base.service.BaseService;
import com.javakc.commonutils.jpa.dynamic.SimpleSpecificationBuilder;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookService  extends BaseService<BookDao,Book> {


    /**
     * 查询所有书籍
     * @return
     */
    public List<Book> findBook(){

        return dao.findAll();
    }
    /**
     * 根据条件进行分页查询
     * @param bookQuery
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Book> findPageBook(BookQuery bookQuery, int pageNo, int pageSize){
        //设置查询条件
        SimpleSpecificationBuilder simpleSpecificationBuilder=new SimpleSpecificationBuilder();
        if (null!=bookQuery) {
            if (!StringUtils.isEmpty(bookQuery.getTitle())) {
                // like
                simpleSpecificationBuilder.and("title", ":", bookQuery.getTitle());
            }
        }
        Specification<Book> specification=simpleSpecificationBuilder.getSpecification();
        // 查询页
        Page page=dao.findAll(specification, PageRequest.of(pageNo-1,pageSize));
        return page;
    }


}
