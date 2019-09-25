package nacosconsumerfeign.controller;

import nacosconsumerfeign.service.NacosProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shiyu
 * @date 2019/9/25 15:58
 * @Description
 */
@RestController
public class NacosProviderController {

    @Autowired
    private NacosProviderService nacosProviderService;

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        return nacosProviderService.echo(message);
    }

}
