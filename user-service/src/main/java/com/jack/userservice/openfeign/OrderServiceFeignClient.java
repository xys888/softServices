package com.jack.userservice.openfeign;

/*
interface GitHub[Object] {
  @RequestMapping("GET /repos/{owner}/{repo}/contributors")
  List<Contributor> contributors[method](@Param("owner") String owner, @Param("repo") String repo);

  @RequestMapping("POST /repos/{owner}/{repo}/issues")
  void createIssue(Issue issue, @Param("owner") String owner, @Param("repo") String repo);

}
 */


import com.jack.userservice.config.feign.OrderServiceFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

// Object#method
// 注册中心上的服务名称
// 要创建该接口的实现类，并且放到Spring IoC容器中
//@FeignClient(name = "order-service",configuration = OrderServiceFeignConfiguration.class)
@FeignClient(name = "order-service")
public interface OrderServiceFeignClient {
    @RequestMapping("/order/query")
    String query() ;
}
