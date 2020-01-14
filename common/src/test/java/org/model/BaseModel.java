package org.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author nurhier
 * @date 2020/1/14
 */
@Getter
@Setter
public class BaseModel {
    private Date createTime;

    private Date updateTime;
}
