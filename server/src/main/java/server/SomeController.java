package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class SomeController {

    /**
     * The GetMapping for the webpage at the server's root: localhost:PORT/
     * @return String that is output to the webpage at the URL
     */
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "The server is running";
    }


}