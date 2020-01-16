package org.comparer;

import org.comparer.annotation.Comparer;
import org.comparer.model.CityVo;
import org.comparer.model.DepartmentVo;
import org.comparer.model.Employee;
import org.comparer.model.LogUpdateVo;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author nurhier
 * @date 2019/9/27
 */
public class ComparerTest extends AbstractComparer {
    @Test
    public void testComparerDiff() {
        List<String> diffs = compareDiff(LogUpdateVo.class, sourceLog(), targetLog());
        Assert.assertNotNull(diffs);
        for (String diff : diffs) {
            System.out.println(diff);
        }
    }

    @Override
    protected String formatOutput(Comparer logCompare, String sourceValue, String targetValue) {
        return logCompare.name() + "(" + sourceValue + "-->" + targetValue + ")";
    }

    private LogUpdateVo sourceLog() {
        return new LogUpdateVo().setCityVo(getSourceCity()).setDepartmentVo(getSourceDepartment())
                                .setEmployeeList(getSourceEmployeeList());
    }

    private LogUpdateVo targetLog() {
        return new LogUpdateVo().setCityVo(getTargetCity()).setDepartmentVo(getTargetDepartment())
                                .setEmployeeList(getTargetEmployeeList());
    }

    private CityVo getSourceCity() {
        return new CityVo().setId(1L).setName("天津").setCode("022").setType(1);
    }

    private DepartmentVo getSourceDepartment() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new DepartmentVo().setId(2L).setName("天津一部").setCode(22001L).setCreateTime(calendar.getTime());
    }

    private CityVo getTargetCity() {
        return new CityVo().setId(1L).setName("北京").setCode("010").setType(2);
    }

    private DepartmentVo getTargetDepartment() {
        return new DepartmentVo().setId(2L).setName("北京二部").setCode(10011L)
                                 .setCreateTime(Calendar.getInstance().getTime());
    }

    private List<Employee> getSourceEmployeeList() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee().setName("张氏").setPhoneCode("022-23456789"));
        list.add(new Employee().setName("张王氏").setPhoneCode("022-02345678"));
        return list;
    }

    private List<Employee> getTargetEmployeeList() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee().setName("张氏二").setPhoneCode("010-23456789"));
        list.add(new Employee().setName("张王氏二").setPhoneCode("010-23456789"));
        return list;
    }
}
