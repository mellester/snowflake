package nl.mellesterk.http;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/")
public class MainController {

    
    @GetMapping
    public String start(Model model) {
        model.addAttribute("now", new Date().toInstant());
        model.addAttribute("item", "Get Stuff Done");
        return "index";
    }

    @DeleteMapping(path = "/delete", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String delete() {
        return "";
    }

    @GetMapping(path = "/projects", produces = MediaType.TEXT_HTML_VALUE)
    public String projects() {
        return "projects";
    }


    /**
     * Thymeleaf will let you use the fragment syntax in a controller, as shown below.
     * https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html#defining-and-referencing-fragments
     */
    @PostMapping(path = "/create")
    public String create(@RequestParam("new-todo") String todo, Model model) {
        model.addAttribute("item", todo);

        // Currently, IntelliJ doesn't recognize a Thymeleaf fragment returned in a controller.
        // https://youtrack.jetbrains.com/issue/IDEA-276625
        //
        //noinspection SpringMVCViewInspection
        return "todo :: todo";
    }
}