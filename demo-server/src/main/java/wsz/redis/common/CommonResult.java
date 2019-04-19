package wsz.redis.common;

import lombok.Data;

import java.util.Map;

/**
 * @description: 通用返回
 * @author: WSZ
 * @create: 2019-04-18 14:45
 **/
@Data
public class CommonResult<T> {

    private Integer code;

    private String errMsg;

    private T data;

    private Long total;

    private Integer pageTotal;

    private Map<String, Object> exData;
}