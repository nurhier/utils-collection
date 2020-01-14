package org.comparer.impl;


import org.comparer.ComparerService;

/**
 * @author nurhier
 * @date 2019/9/27
 */
public class DepartmentComparerServiceImpl implements ComparerService<Long> {
    @Override
    public String getName(Long param) {
        if (param == null) {
            return "empty";
        }
        return "department" + param;
    }
}
