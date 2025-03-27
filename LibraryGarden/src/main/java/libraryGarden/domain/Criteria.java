package libraryGarden.domain;

//페이징을 하기 위해서 기준이 되는 데이터를 담는클래스
public class Criteria {
	private int page = 1;  // 보여줄 페이지 번호
	private int perPageNum = 12;  // 한 화면에 보여줄 게시물 수
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	
}