package org.parts.mp.ser.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.parts.mp.ser.MyIService;

/**
 * 重构mybatsi plus ServiceImpl
 *
 * @param <M>
 * @param <T>
 * @author ltl
 */
public class MyServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements MyIService<T> {
}
