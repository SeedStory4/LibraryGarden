package libraryGarden.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.domain.LibraryBookDto2;
import libraryGarden.domain.LibraryBooksVo2;
import libraryGarden.user.service.LibraryBookService2;


@Controller
@RequestMapping("/user")
public class MainController {
	
		@Autowired
	    private LibraryBookService2 libraryBookService2;
		
		@GetMapping("/main.do")
		public String main(Model model) {
		   

		    return "user/main";
		}
		
		@GetMapping("/userHeader.do")
	    public String userHeader() {
	        return "user/userHeader";		  
	    }

}
