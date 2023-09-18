package team3.controller;

import jakarta.servlet.ServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url_prefix.api.secure}")
@Log4j2
public class DailySpendingController {
    @GetMapping("/daily_spend")
    public void getCategories(ServletRequest servletRequest){
        log.info("== Get Daily spending ==");
    }
}
