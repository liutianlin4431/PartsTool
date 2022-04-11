package org.parts.mp.ser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 重构mybatsi plus IService
 * 
 * @author ltl
 *
 * @param <T>
 */
public interface MyIService<T> extends IService<T> {
	/**
	 * 查询单个实体-传入实体非空属性全部使用“=”增加到where条件
	 * 
	 * @param entity
	 * @return
	 */
	default T getOne(T entity) {
		return this.getOne(this.toQueryWrapper(entity));
	};

	/**
	 * 将实体转成QueryWrappe实体，条件全部使用“=”
	 * 
	 * @param entity
	 * @return
	 */
	default QueryWrapper<T> toQueryWrapper(T entity) {
		return new QueryWrapper<T>().setEntity(entity);
	}

	/**
	 * 根据实体查询集合，条件全部使用“=”
	 * 
	 * @param entity
	 * @return
	 */
	default List<T> list(T entity) {
		return this.list(this.toQueryWrapper(entity));
	}

	/**
	 * 根据实体删除，条件全部使用“=”
	 * 
	 * @param entity
	 * @return
	 */
	default boolean remove(T entity) {
		return this.remove(this.toQueryWrapper(entity));
	}
}
