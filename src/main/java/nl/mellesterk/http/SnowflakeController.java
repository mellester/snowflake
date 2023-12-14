package nl.mellesterk.http;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import nl.mellesterk.SnowflakeGenerator;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/projects/snowflake")
public class SnowflakeController {
    private static final Logger logger = LoggerFactory.getLogger(SnowflakeController.class);
    @GetMapping(path = "/index.html", produces = MediaType.TEXT_HTML_VALUE)
    public String get() {
        return "projects/index"; 
    }

    @GetMapping("/")
    public RedirectView redirectWithUsingRedirectView(
            RedirectAttributes attributes) {
        return new RedirectView("index.html");
    }


        @GetMapping(path = "/update", produces = "image/svg+xml")
        @ResponseBody
        @Cacheable("snowflake")
        public ResponseEntity<String> update(@RequestParam("level") Integer value) {
            if (value == null) {
                value = 0;
            }
            logger.debug("Slider value: {}", value);
            
            // Use the 'value' parameter in your logic
            
            return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.DAYS)).body(SnowflakeGenerator.generateSnowflakeSVG(value));
        }
}