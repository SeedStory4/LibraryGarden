package libraryGarden.domain;

import java.util.List;

/**
 * [설명] 알라딘 페이징을 위한 Dto
 * 
 * [주요기능]
 * - 알라딘 상품 검색 리스트를 담음
 * - 알라딘 상품 전체 개수를 담음
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */

public class ApiBookPageDto {
    private List<BookVo> blist;
    private int totalCount;

    public List<BookVo> getBlist() {
        return blist;
    }

    public void setBlist(List<BookVo> blist) {
        this.blist = blist;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}