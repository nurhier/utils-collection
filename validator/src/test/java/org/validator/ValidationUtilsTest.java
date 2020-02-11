package org.validator;

import org.junit.Assert;
import org.junit.Test;
import org.validator.bean.Vehicle;
import org.validator.bean.Check;
import org.validator.utils.ValidationUtils;

/**
 * @author nurhier
 * @date 2020/2/10
 */
public class ValidationUtilsTest {
    @Test
    public void validateTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setFrameNo("123456789012345678");
        vehicle.setVehicleNo("京A8W88889");
        vehicle.setModelId("a001");
        ValidationResult<Vehicle> result = ValidationUtils.validate(vehicle);
        Assert.assertFalse(result.isPassed());
        printResult(result);
    }

    @Test
    public void validateGroupTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setFrameNo("123456789012345678");
        ValidationResult<Vehicle> result = ValidationUtils.validate(vehicle, Check.class);
        Assert.assertTrue(vehicle.getFrameNo().length() > 17);
        Assert.assertTrue(result.isPassed());
        printResult(result);
    }

    private void printResult(ValidationResult<?> validationResult) {
        System.out.println(validationResult.getErrorMessageList().toString());
    }
}
