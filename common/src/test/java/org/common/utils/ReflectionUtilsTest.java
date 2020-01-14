package org.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.model.BaseModel;
import org.model.Vehicle;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nurhier
 * @date 2020/1/14
 */
public class ReflectionUtilsTest {
    @Test
    public void isPropertiesNullTest() {
        Assert.assertTrue(ReflectionUtils.isPropertiesNull(null, BaseModel.class, "id"));

        Assert.assertTrue(ReflectionUtils.isPropertiesNull(new Vehicle(), BaseModel.class, "id"));

        Assert.assertTrue(ReflectionUtils.isPropertiesNull(new Vehicle().setFrameNo("398828"), "frameNo"));

        Assert.assertFalse(ReflectionUtils.isPropertiesNull(new Vehicle().setFrameNo("LSR393"), BaseModel.class, "id"));

        Set<String> properties = new HashSet<>();
        properties.add("id");
        properties.add("createTime");
        properties.add("updateTime");
        properties.add("frameNo");
        properties.add("vehicleNo");
        Assert.assertTrue(ReflectionUtils.isPropertiesNull(
                new Vehicle().setId(1L).setFrameNo("LSR393").setVehicleNo("京A6R349").setCreateTime(new Date())
                             .setUpdateTime(new Date()), properties));
    }
}
