package com.yy.platform.component.starter.result.pager;

import cn.hutool.db.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private Long count = 0L;

    private List<T> data;

    /**
     * 当前页
     */
    private Long current = 1L;
    /**
     * 每页数
     */
    private Long size = 10L;

    /**
     * 总共有多少页
     */
    private Long pageCount = 1L;

    /**
     * 是否有下一页
     */
    private boolean isNextPage = true;

    /**
     * 是否有上一页
     */
    private boolean isLastPage = true;

    public static PageResult.Builder builder() {
        return new PageResult.Builder();
    }

    public static class Builder<T>{

        private Long count = 0L;

        private List<T> data;
        private Long current = 1L;
        private Long size = 10L;

        private Long pageCount = 1L;


        public PageResult.Builder data(List<T> data){
            this.data = data;
            return this;
        }
        public PageResult.Builder count(Long count) {
            this.count = count;
            return this;
        }
        public PageResult.Builder current(Long current) {
            this.current = current;
            return this;
        }
        public PageResult.Builder size(Long size) {
            this.size = size;
            return this;
        }

        public PageResult build() {
            PageResult pageResult = new PageResult();
            pageResult.data = this.data;
            pageResult.current = this.current;
            pageResult.size = this.size;
            pageResult.count = this.count;
            if(pageResult.count != 0)
                pageResult.pageCount =  (this.count%this.size == 0) ? this.count/this.size : (this.count/this.size)+1;

            if(pageResult.current == pageResult.pageCount){
                pageResult.isNextPage = false;
            }
            if(pageResult.current == 1){
                pageResult.isLastPage = false;
            }
            return pageResult;
        }

    }

}
