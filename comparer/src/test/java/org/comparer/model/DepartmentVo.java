package org.comparer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.comparer.annotation.Comparer;
import org.comparer.impl.DepartmentComparerServiceImpl;

import java.util.Date;

/**
 * @author nurhier
 * @date 2019/9/27
 */
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DepartmentVo {
    private Long id;
    @Comparer(name = "部门名称")
    private String name;
    @Comparer(name = "部门编码", beanClass = DepartmentComparerServiceImpl.class)
    private Long code;
    @Comparer(name = "成立日期", datePattern = "yyyy-MM-dd")
    private Date createTime;
}
