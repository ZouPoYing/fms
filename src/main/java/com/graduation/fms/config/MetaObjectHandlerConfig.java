package com.graduation.fms.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = null;
        Object updateTime = null;
        if(metaObject.hasGetter("createTime") ){
            createTime = getFieldValByName("createTime", metaObject);
        }
        if(metaObject.hasGetter("updateTime")){
            updateTime = getFieldValByName("updateTime", metaObject);
        }
        Date now = new Date();
        if (createTime == null && metaObject.hasSetter("createTime")){
            setFieldValByName("createTime",now, metaObject);
        }
        if (updateTime == null && metaObject.hasSetter("updateTime")){
            setFieldValByName("updateTime",now, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if(metaObject.hasSetter("updateTime")){
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}