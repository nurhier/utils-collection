package org.comparer.model;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.comparer.annotation.Comparer;

import java.util.List;

/**
 * @author nurhier
 * @date 2019/9/27
 */
@Accessors(chain = true)
@Setter
public class LogUpdateVo {
    @Comparer(name = "城市")
    private CityVo cityVo;
    @Comparer(name = "部门")
    private DepartmentVo departmentVo;
    @Comparer(name = "员工列表")
    private List<Employee> employeeList;
}
