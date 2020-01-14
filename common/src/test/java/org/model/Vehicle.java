package org.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author nurhier
 * @date 2020/1/14
 */
@Getter
@Setter
@Accessors(chain = true)
public class Vehicle {
    /**
     * 车架号
     */
    private String frameNo;
    /**
     * 车牌号
     */
    private String vehicleNo;

    private Long id;

    private Date createTime;

    private Date updateTime;
}
