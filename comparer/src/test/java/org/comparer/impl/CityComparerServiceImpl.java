package org.comparer.impl;

import org.comparer.ComparerService;

/**
 * @author nurhier
 * @date 2019/9/27
 */
public class CityComparerServiceImpl implements ComparerService<String> {
    @Override
    public String getName(String param) {
        if (param == null) {
            return "empty";
        }
        return "city:" + param;
    }
}
