package org.common.json;

import org.common.json.enums.JsonEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.model.Vehicle;

/**
 * @author nurhier
 * @date 2020/1/19
 */
public class JsonTest {
    private Vehicle vehicle;
    private String vehicleJson;

    @Before
    public void beforeTest() {
        vehicle = new Vehicle();
        vehicle.setFrameNo("NFC12323");
        vehicleJson = "{\"frameNo\":\"NFC12323\"}";
    }

    @Test
    public void toJsonDefaultTest() {
        String jsonString = JsonUtils.toJSONString(vehicle);
        System.out.println(jsonString);
        Assert.assertNotNull(jsonString);
    }

    @Test
    public void fromJsonDefaultTest() {
        Vehicle vehicle = JsonUtils.parseObject(vehicleJson, Vehicle.class);
        System.out.println(vehicle);
        Assert.assertNotNull(vehicle);
    }

    @Test
    public void toJsonEnumsTest() {
        String gson = JsonUtils.toJSONString(JsonEnum.GSON, vehicle);
        String fastjson = JsonUtils.toJSONString(JsonEnum.FAST_JSON, vehicle);
        System.out.println("gson:" + gson);
        System.out.println("fastJson:" + fastjson);
        Assert.assertEquals(gson, fastjson);
    }

    @Test
    public void fromJsonEnumsTest() {
        Vehicle gsonVehicle = JsonUtils.parseObject(JsonEnum.GSON, vehicleJson, Vehicle.class);
        Vehicle fastJsonVehicle = JsonUtils.parseObject(JsonEnum.FAST_JSON, vehicleJson, Vehicle.class);
        System.out.println("gson:" + gsonVehicle);
        System.out.println("fastJson:" + fastJsonVehicle);
        Assert.assertEquals(gsonVehicle.getFrameNo(), fastJsonVehicle.getFrameNo());
    }
}
