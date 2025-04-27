package libraryGarden.domain;
/**
 * [설명] 도서관에 있는 책들에 관련된 Dto
 * 
 * [주요기능]
 * - 도서관에 현재 있는 책의 대한 정보와 대출상태를 나타냄
 *  
 * @author SiYeon
 * 
 * 
 */ 

public class LibraryBooksDto {

	private int bidx; // 책 인덱스
	private String coverImg; // 책 표지
    private String title; // 책 체목
    private String originalTitle; // 책 원제
	private String subTitle; // 책 부제
	private String author; // 책 저자 
	private String publisher; // 책 출판사
	private String publishedYear; // 책 출판일
	private int totalPages; // 책 쪽수
	private String isbn; // 책 isbn
    private String sizeWidth; // 책 넓이
    private String sizeHeight;// 책 높이
    private String weight;// 책 무게
	private String category; // 책 카테고리(분류)
	private String introduction; // 책 한줄요약
	private int price; // 책 정가
	
	private int lbidx; // 도서관 책 인덱스
	private String code; // 도서관 책 구분	
	private String callName; // 도서관 책 청구기호	
	private String location; // 도서관 책 자료실
	private String dueDate; // 도서관 책 반납예정일
	private String status; // 도서관 책 상태(대출중, 대출가능, 예약대기)
	
	private int cidx; // 도서관 책 카테고리 인덱스
	private String name; // 카테고리 이름(문학, 등)	
	private String parentCode; // 카테고리 상위 코드
	private String childCode; // 카테고리 하위 코드
	
	private int aidx; //  도서관 책 결재 승인 된 인덱스
	
	public int getBidx() {
		return bidx;
	}
	public void setBidx(int bidx) {
		this.bidx = bidx;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOriginalTitle() {
		return originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublishedYear() {
		return publishedYear;
	}
	public void setPublishedYear(String publishedYear) {
		this.publishedYear = publishedYear;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getSizeWidth() {
		return sizeWidth;
	}
	public void setSizeWidth(String sizeWidth) {
		this.sizeWidth = sizeWidth;
	}
	public String getSizeHeight() {
		return sizeHeight;
	}
	public void setSizeHeight(String sizeHeight) {
		this.sizeHeight = sizeHeight;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getLbidx() {
		return lbidx;
	}
	public void setLbidx(int lbidx) {
		this.lbidx = lbidx;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCidx() {
		return cidx;
	}
	public void setCidx(int cidx) {
		this.cidx = cidx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getChildCode() {
		return childCode;
	}
	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}
	public int getAidx() {
		return aidx;
	}
	public void setAidx(int aidx) {
		this.aidx = aidx;
	}

}
