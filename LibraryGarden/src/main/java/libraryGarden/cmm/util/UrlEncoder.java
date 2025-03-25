package libraryGarden.cmm.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncoder {
	
	public String encoding(String url) {

		String encodeUrl = "";
		
		if(url != null) {
			try {
			    // URLEncoder를 사용하여 URL 인코딩
				encodeUrl = URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
			}
		}
		
		return encodeUrl;
	}
}
