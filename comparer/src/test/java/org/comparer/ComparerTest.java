package org.comparer;

import org.comparer.annotation.Comparer;
import org.comparer.model.CityVo;
import org.comparer.model.DepartmentVo;
import org.comparer.model.LogUpdateVo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

/**
 * @author hao.zhou02
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
        return new LogUpdateVo().setCityVo(getSourceCity()).setDepartmentVo(getSourceDepartment());
    }

    private LogUpdateVo targetLog() {
        return new LogUpdateVo().setCityVo(getTargetCity()).setDepartmentVo(getTargetDepartment());
    }

    private CityVo getSourceCity() {
        return new CityVo().setId(1L).setName("天津").setCode("022").setType(1);
    }

    private DepartmentVo getSourceDepartment() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new DepartmentVo().setId(2L).setName("一部").setCode(2001L).setCreateTime(calendar.getTime());
    }

    private CityVo getTargetCity() {
        return new CityVo().setId(1L).setName("天津卫").setCode("02201").setType(2);
    }

    private DepartmentVo getTargetDepartment() {

        return new DepartmentVo().setId(2L).setName("二部").setCode(200101L).setCreateTime(Calendar.getInstance().getTime());
    }
}
