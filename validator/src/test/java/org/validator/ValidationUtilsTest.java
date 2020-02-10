package org.validator;

import org.junit.Test;
import org.validator.bean.Vehicle;

/**
 * @author nurhier
 * @date 2020/2/10
 */
public class ValidationUtilsTest {
    @Test
    public void validateTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setFrameNo("123456789012345678");
        vehicle.setModelId("a001");
        ValidationUtils.validate(vehicle);
    }
}
