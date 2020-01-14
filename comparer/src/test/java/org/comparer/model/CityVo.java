package org.comparer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.comparer.annotation.Comparer;
import org.comparer.enums.CityType;
import org.comparer.impl.CityComparerServiceImpl;

/**
 * @author nurhier
 * @date 2019/9/27
 */
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class CityVo {
    private Long id;
    @Comparer(name = "城市名称")
    private String name;
    @Comparer(name = "城市编码", beanClass = CityComparerServiceImpl.class)
    private String code;
    @Comparer(name = "城市类型", enumClass = CityType.class)
    private Integer type;
}
