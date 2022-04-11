package org.parts.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * easyui分页统一返回实体类
 * 
 * @author ltl
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EasyUiPageVo<T> implements Serializable {
	private static final long serialVersionUID = -5123560345302553119L;
	private List<T> rows;
	private Long total;
}
