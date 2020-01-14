package org.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.model.enums.VehicleNoType;

/**
 * @author nurhier
 * @date 2020/1/14
 */
public class EnumUtilsTest {
    @Test
    public void getEnumNameByCodeTest() {
        Assert.assertEquals(VehicleNoType.BLUE.getName(),
                            EnumUtils.getEnumNameByCode(VehicleNoType.class, VehicleNoType.BLUE.getCode()));
    }

    @Test
    public void getEnumCodeByNameTest() {
        Assert.assertEquals(VehicleNoType.GREEN.getCode(),
                            EnumUtils.getEnumCodeByName(VehicleNoType.class, VehicleNoType.GREEN.getName()));
    }

    @Test
    public void getEnumByCodeTest() {
        Assert.assertEquals(VehicleNoType.BLUE,
                            EnumUtils.getEnumByCode(VehicleNoType.class, VehicleNoType.BLUE.getCode()));
        Assert.assertNotEquals(VehicleNoType.GREEN,
                               EnumUtils.getEnumByCode(VehicleNoType.class, VehicleNoType.BLUE.getCode()));
    }

    @Test
    public void getEnumByNameTest() {
        Assert.assertEquals(VehicleNoType.GREEN,
                            EnumUtils.getEnumByName(VehicleNoType.class, VehicleNoType.GREEN.getName()));

        Assert.assertNotEquals(VehicleNoType.BLUE,
                               EnumUtils.getEnumByName(VehicleNoType.class, VehicleNoType.GREEN.getName()));
    }
}
