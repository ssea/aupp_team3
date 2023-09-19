package team3.controller;

import jakarta.servlet.ServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url_prefix.api.secure}")
public class CategoryController {

    @GetMapping("/category")
    public void getCategories(ServletRequest servletRequest){
    	System.out.print("== Get category ==");
    }

}
