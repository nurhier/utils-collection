package org.comparer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.comparer.annotation.Comparer;

/**
 * @author nurhier
 * @date 2020/1/14
 */
@Getter
@Setter
@Accessors(chain = true)
public class Employee {
    @Comparer(name = "员工名称")
    private String name;

    @Comparer(name = "员工电话号")
    private String phoneCode;
}
