package com.jack.userservice.config.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2021年11月19日15:09
 */

@Configuration
@RibbonClient(name = "user-service",configuration = RibbonConfiguration.class)
public class UserServiceRibbonConfiguration {
}
