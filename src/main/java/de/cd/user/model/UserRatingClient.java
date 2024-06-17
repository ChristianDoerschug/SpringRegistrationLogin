package de.cd.user.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${review.service.url}", name = "UserRatingService")
public interface UserRatingClient {

    @RequestMapping(method = RequestMethod.GET, value = "/random?min=1&max=200&count=1")
    int[] getRating();
}
