package nacosconsumerfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-provider")
public interface NacosProviderService {

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message);

}
