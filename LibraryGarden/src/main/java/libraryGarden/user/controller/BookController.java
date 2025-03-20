package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jsp/user/book")
public class BookController {

    // 도서 예약 팝업으로 이동
    @GetMapping("/popBookReservationWrite")
    public String popBookReservationWrite() {
        return "user/book/popBookReservationWrite"; // JSP 파일 경로
    }
}