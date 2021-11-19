package com.jack.userservice.config.feign;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;

/**
 * @date 2021年11月19日15:35
 */
//@Configuration
public class OrderServiceFeignConfiguration {

    /**
     * none 无记录
     * basic 只记录请求方法和url以及响应状态代码和响应时间
     * headers 记录基本信息以及请求和响应标头
     * full 记录请求和响应的头文件，正文和元数据。
     * @return
     */
    //@Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
