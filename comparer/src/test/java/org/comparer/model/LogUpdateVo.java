package org.comparer.model;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.comparer.annotation.Comparer;

/**
 * @author hao.zhou02
 * @date 2019/9/27
 */
@Accessors(chain = true)
@Setter
public class LogUpdateVo {
    @Comparer(name = "城市")
    private CityVo cityVo;
    @Comparer(name = "部门")
    private DepartmentVo departmentVo;
}
