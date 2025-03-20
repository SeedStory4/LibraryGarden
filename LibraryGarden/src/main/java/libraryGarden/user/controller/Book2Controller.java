package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Book2Controller {

	@GetMapping("/user/popBookReservationWrite.do")
    public String popBookReservationWrite() {
        return "user/book/popBookReservationWrite";
	    }
}