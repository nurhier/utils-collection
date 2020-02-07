package org.common.generator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author nurhier
 * @date 2020/2/7
 */
public class UUIDUtilsTest {
    @Test
    public void generateUUIDTest() {
        String uuid = UUIDUtils.generateUUID();
        Assert.assertNotNull(uuid);
        Assert.assertEquals(uuid.length(), 32);
    }
}
