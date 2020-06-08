package com.yy.platform.component.starter.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author dincoo
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class BaseService<M extends BaseMapper<T>, T extends BaseModel<T>> extends ServiceImpl<M, T> implements IBaseService<T>, ApplicationContextAware {
	 protected Logger logger = LogManager.getLogger(getClass());
	    @Autowired
	    protected M mapper;
	    protected ApplicationContext applicationContext;

	    public void setApplicationContext(ApplicationContext applicationContext) {
	        this.applicationContext = applicationContext;
	    }


}
