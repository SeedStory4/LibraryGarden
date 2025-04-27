package libraryGarden.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.LibraryBooksVo;
import libraryGarden.user.service.LibraryBooksService;


@Controller
@RequestMapping("/user")
public class MainController {
	
		@Autowired
	    private LibraryBooksService libraryBooksService;
		
		@GetMapping("/main.do")
		public String main(Model model) {
			List<LibraryBooksDto> topLoanBooks = libraryBooksService.selectTopLoanBooksThisMonth();
			List<LibraryBooksDto> latestBooks = libraryBooksService.getLatestLibraryBooks();	   

	        model.addAttribute("topLoanBooks", topLoanBooks);
	        model.addAttribute("latestBooks", latestBooks);

		    return "user/main";
		}
		
	

}
