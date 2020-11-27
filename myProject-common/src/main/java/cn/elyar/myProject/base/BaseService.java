package cn.elyar.myProject.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author elyar
 * @date 2020/11/25 14:25
 * @description
 */
@Transactional(rollbackFor = Exception.class)
public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
