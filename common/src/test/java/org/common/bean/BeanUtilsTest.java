package org.common.bean;

import org.common.utils.BeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.model.Car;
import org.model.Vehicle;
import org.model.VehicleInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nurhier
 * @date 2020/3/3
 */
public class BeanUtilsTest {
    @Test
    public void testCopyProperties() {
        Car car = new Car().setFrameNo("CAR2020000111").setId(1L).setVehicleNo("京C78787").setCreateTime(new Date());
        String frameNo = "CAR2020000112";
        Vehicle vehicle = new Vehicle().setFrameNo(frameNo).setId(12L).setVehicleNo("京C78782")
                                       .setCreateTime(new Date()).setUpdateTime(new Date());
        BeanUtils.copyProperties(car, vehicle);
        Assert.assertEquals(vehicle.getId(), car.getId());
        Assert.assertEquals(vehicle.getFrameNo(), car.getFrameNo());
        Assert.assertNull(vehicle.getUpdateTime());
        Assert.assertNotEquals(vehicle.getFrameNo(), frameNo);

    }

    @Test
    public void testCopyNotNullProperties() {
        Car car = new Car().setFrameNo("CAR2020000111").setId(1L).setVehicleNo("京C78787").setCreateTime(new Date());
        String frameNo = "CAR2020000112";
        Date time = new Date();
        VehicleInfo vehicle = new VehicleInfo().setFrameNo(frameNo).setId(12L).setVehicleNo("京C78782")
                                               .setCreateTime(new Date()).setUpdateTime(time);
        BeanUtils.copyNonNullProperties(car, vehicle);
        Assert.assertEquals(vehicle.getId(), car.getId());
        Assert.assertEquals(vehicle.getFrameNo(), car.getFrameNo());
        Assert.assertEquals(vehicle.getUpdateTime(), time);
        Assert.assertNotEquals(vehicle.getFrameNo(), frameNo);
    }

    @Test
    public void testCopy() {
        Car car = new Car().setFrameNo("CAR2020000111").setId(1L).setVehicleNo("京C78787").setCreateTime(new Date());
        VehicleInfo vehicle = BeanUtils.copy(car, VehicleInfo.class);
        Assert.assertEquals(vehicle.getId(), car.getId());
        Assert.assertEquals(vehicle.getFrameNo(), car.getFrameNo());
        Assert.assertNull(vehicle.getUpdateTime());
    }

    @Test
    public void testCopyWithFiledMap() {
        Car car = new Car().setFrameNo("CAR2020000111").setId(1L).setVehicleNo("京C78787").setType("suv");
        Map<String, String> filedMap = new HashMap<>();
        filedMap.put("type", "vehicleType");
        VehicleInfo vehicle = BeanUtils.copy(car, VehicleInfo.class, filedMap);
        Assert.assertEquals(vehicle.getVehicleType(), car.getType());
        Assert.assertNull(vehicle.getUpdateTime());
    }

    @Test
    public void testCopyIgnore() {
        Car car = new Car().setFrameNo("CAR2020000111").setId(1L).setVehicleNo("京C78787").setCreateTime(new Date());
        String[] s = {"id", "frameNo",};
        VehicleInfo vehicle = BeanUtils.copy(car, VehicleInfo.class, s);
        Assert.assertNull(vehicle.getId());
        Assert.assertNull(vehicle.getFrameNo());
        Assert.assertEquals(vehicle.getVehicleNo(), car.getVehicleNo());
        Assert.assertNull(vehicle.getUpdateTime());

        Car car1 = new Car().setFrameNo("CAR2020000111").setId(1L).setVehicleNo("京C787872").setCreateTime(new Date());
        String[] s1 = {"frameNo", "vehicleNo"};
        VehicleInfo vehicle1 = BeanUtils.copy(car, VehicleInfo.class, s1);
        Assert.assertNull(vehicle1.getVehicleNo());
        Assert.assertNull(vehicle1.getFrameNo());
        Assert.assertEquals(vehicle1.getId(), car1.getId());
        Assert.assertNull(vehicle1.getUpdateTime());
    }

    @Test
    public void testCopyIgnoreNull() {
        Car car = new Car().setFrameNo("CAR2020000111").setId(1L).setVehicleNo("京C78787").setCreateTime(new Date());
        VehicleInfo vehicle = BeanUtils.copy(car, VehicleInfo.class, (String) null, null);
        Assert.assertEquals(vehicle.getVehicleNo(), car.getVehicleNo());
    }
}
