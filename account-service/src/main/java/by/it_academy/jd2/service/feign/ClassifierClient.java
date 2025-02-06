package by.it_academy.jd2.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "classifier-service", url = "${client.classifier-service.url}")
public interface ClassifierClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<UUID, String> getCurrencyNames(@RequestBody UUID[] currencies);
}
