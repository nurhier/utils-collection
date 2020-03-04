package org.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author nurhier
 * @date 2020/3/3
 */
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Car {
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

    private String type;
}
