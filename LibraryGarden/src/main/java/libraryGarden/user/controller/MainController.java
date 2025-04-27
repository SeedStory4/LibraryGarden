package libraryGarden.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.domain.LibraryBooks2Dto;
import libraryGarden.domain.LibraryBooksVo2;
import libraryGarden.user.service.LibraryBook2Service;


@Controller
@RequestMapping("/user")
public class MainController {
	
		@Autowired
	    private LibraryBook2Service libraryBook2Service;
		
		@GetMapping("/main.do")
		public String main(Model model) {
			List<LibraryBooks2Dto> topLoanBooks = libraryBook2Service.selectTopLoanBooksThisMonth();
	        List<LibraryBooksVo2> latestBooks = libraryBook2Service.getLatestBooks();

	        model.addAttribute("topLoanBooks", topLoanBooks);
	        model.addAttribute("latestBooks", latestBooks);

		    return "user/main";
		}
		
	

}
