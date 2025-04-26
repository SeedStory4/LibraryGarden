-- 데이터베이스 삭제 후 재생성
DROP DATABASE IF EXISTS librarygarden;
CREATE DATABASE librarygarden;
USE librarygarden;


-- USER 테이블 생성
CREATE TABLE USER (
    uidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id VARCHAR(50) NOT NULL,
    password VARCHAR(225) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(225) NOT NULL UNIQUE,
    address VARCHAR(225) NOT NULL,
    userNumber VARCHAR(20) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL DEFAULT '일반회원',
    regdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify DATETIME,
    delyn CHAR(1) NOT NULL DEFAULT 'N'
);

-- BOOKS 테이블 생성
CREATE TABLE BOOKS (
    bidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    coverImg TEXT NOT NULL, -- (api 필드명)cover
    title VARCHAR(255) NOT NULL, -- (api 필드명)title
    originalTitle VARCHAR(255), -- (api 필드명)originalTitle
    subtitle VARCHAR(255), -- (api 필드명)subtitle
    author VARCHAR(100) NOT NULL, -- (api 필드명)author
    publisher VARCHAR(100), -- (api 필드명)publisher
    publishedYear DATE, -- (api 필드명)pubdate
    totalPages INT, -- (api 필드명)itemPage
    isbn VARCHAR(20) NOT NULL, -- (api 필드명)isbn13(숫자)
    sizeWidth VARCHAR(30), -- (api 필드명)packing < sizeWidth (mm 기준)
    sizeHeight VARCHAR(30), -- (api 필드명)packing < sizeHeight (mm 기준)
    weight VARCHAR(30), -- (api 필드명)packing < weight (그램 기준)
    category VARCHAR(255), -- (api 필드명)categoryName
    introduction TEXT NOT NULL, -- (api 필드명)description
    price INT NOT NULL, -- (api 필드명)priceStandard
    regDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify DATETIME,
    delyn CHAR(1) NOT NULL DEFAULT 'N'
);


-- CATEGORY 테이블 생성
CREATE TABLE CATEGORY (
    cidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    parentCode VARCHAR(3) NOT NULL,
    childCode VARCHAR(3) NOT NULL,
    level INT NOT NULL,
    regdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify DATETIME,
    delyn CHAR(1) NOT NULL DEFAULT 'N'
);

-- REQUEST 테이블 생성
CREATE TABLE REQUEST (
    rqidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uidx INT NOT NULL,
    bidx INT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT '신청중',
    rejectionReason VARCHAR(255),
    regDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify DATETIME,
    delyn CHAR(1) NOT NULL DEFAULT 'N',
    CONSTRAINT fk_request_user FOREIGN KEY (uidx) REFERENCES USER(uidx) 
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_request_books FOREIGN KEY (bidx) REFERENCES BOOKS(bidx) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- APPROVAL 테이블 생성
CREATE TABLE APPROVAL (
    aidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uidx INT NOT NULL,
    rqidx INT,
    bidx INT,
    status varchar(50) NOT NULL DEFAULT '대기',
    rejectionReason VARCHAR(255),
    approvalDate DATETIME,
    regDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify DATETIME,
    delyn CHAR(1) NOT NULL DEFAULT 'N',
    regyn CHAR(1) NOT NULL DEFAULT 'N',
    CONSTRAINT fk_approval_user FOREIGN KEY (uidx) REFERENCES USER(uidx) 
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_approval_request FOREIGN KEY (rqidx) REFERENCES REQUEST(rqidx) 
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_approval_books FOREIGN KEY (bidx) REFERENCES BOOKS(bidx) 
        ON UPDATE CASCADE ON DELETE CASCADE
);


-- LIBRARYBOOKS 테이블 생성
CREATE TABLE LIBRARYBOOKS (
    lbidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bidx INT NOT NULL,
    cidx INT NOT NULL,
    aidx INT NOT NULL,
    code VARCHAR(30) NOT NULL UNIQUE ,
    callName VARCHAR(30) NOT NULL UNIQUE ,
    location VARCHAR(50) NOT NULL DEFAULT '일반열람실',
    loanDate DATE,
    dueDate DATE,
    returnDate DATE,
    status VARCHAR(50) NOT NULL DEFAULT '대출가능',
    extended CHAR(1) NOT NULL DEFAULT 'N',
    regdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify DATETIME,
    delyn CHAR(1) NOT NULL DEFAULT 'N',
    CONSTRAINT fk_librarybooks_books FOREIGN KEY (bidx) REFERENCES BOOKS(bidx) 
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_librarybooks_category FOREIGN KEY (cidx) REFERENCES CATEGORY(cidx) 
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_librarybooks_approval FOREIGN KEY (aidx) REFERENCES APPROVAL(aidx) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- LOAN 테이블 생성
CREATE TABLE LOAN (
    lidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    lbidx INT NOT NULL,
    uidx INT NOT NULL,
    loanDate DATE NOT NULL,
    dueDate DATE NOT NULL,
    returnDate DATE,
    status VARCHAR(50) NOT NULL DEFAULT '대출중',
    extended CHAR(1) NOT NULL DEFAULT 'N',
    regdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify DATETIME,
    delyn CHAR(1) NOT NULL DEFAULT 'N',
    CONSTRAINT fk_loan_librarybooks FOREIGN KEY (lbidx) REFERENCES LIBRARYBOOKS(lbidx) 
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_loan_user FOREIGN KEY (uidx) REFERENCES USER(uidx) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- OVERDUE 테이블 생성
CREATE TABLE OVERDUE (
    oidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    lidx INT NOT NULL,
    uidx INT NOT NULL,
    status CHAR(1) NOT NULL DEFAULT 'Y',
    startDate DATE NOT NULL,
    endDate DATE,
    CONSTRAINT fk_overdue_loan FOREIGN KEY (lidx) REFERENCES LOAN(lidx) 
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_overdue_user FOREIGN KEY (uidx) REFERENCES USER(uidx) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- RESERVATION 테이블 생성 (예약 테이블)
CREATE TABLE RESERVATION (
    ridx INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    lbidx INT NOT NULL,                           
    uidx INT NOT NULL,                            
    regDate DATETIME NOT NULL,  
    pickupDate DATE NOT NULL,                      
    status VARCHAR(50) NOT NULL DEFAULT '예약중',  
    dueDate DATE NOT NULL,                      
    modify DATETIME,                    
    delyn CHAR(1) NOT NULL DEFAULT 'N',          
    CONSTRAINT fk_reservation_librarybooks FOREIGN KEY (lbidx)
        REFERENCES LIBRARYBOOKS(lbidx) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_reservation_user FOREIGN KEY (uidx)
        REFERENCES USER(uidx) ON UPDATE CASCADE ON DELETE CASCADE
);



--------------------------------------------------------------------------------------------------------------------------------------

-- USER 샘플 데이터 생성
INSERT INTO USER(id, password, name, phone, email, address, userNumber, role) 
VALUES ('director','$2a$10$GlSGv1jjhBrWSk9ebqOa0uTeVUJ/RWqTLwkf1mA3pMN7ySNySkNQS','도서관장','01012345678','director@aaa.ccc','대한민국','00000000','도서관장');

-- CATEGORY 샘플 데이터 생성
INSERT INTO CATEGORY (name, parentCode, childCode, level) VALUES
('총류', '000', '000', 1),
('도서학, 서지학', '000', '010', 2),
('문헌정보학', '000', '020', 2),
('백과사전', '000', '030', 2),
('강연집, 수필집, 연설문집', '000', '040', 2),
('일반 연속간행물', '000', '050', 2),
('일반 학회, 단체, 협회, 기관', '000', '060', 2),
('신문, 언론, 저널리즘', '000', '070', 2),
('일반 전집, 총서', '000', '080', 2),
('향토자료', '000', '090', 2),
('철학', '100', '100', 1),
('형이상학', '100', '110', 2),
('인식론, 인과론, 인간학', '100', '120', 2),
('철학의 체계', '100', '130', 2),
('경학', '100', '140', 2),
('아시아철학, 사상', '100', '150', 2),
('서양철학', '100', '160', 2),
('논리학', '100', '170', 2),
('심리학', '100', '180', 2),
('윤리학, 도덕철학', '100', '190', 2),
('종교', '200', '200', 1),
('비교종교', '200', '210', 2),
('불교', '200', '220', 2),
('기독교', '200', '230', 2),
('도교', '200', '240', 2),
('천도교', '200', '250', 2),
('신도', '200', '260', 2),
('파라문교, 인도교', '200', '270', 2),
('이슬람교(회교)', '200', '280', 2),
('기타 제종교', '200', '290', 2),
('사회과학', '300', '300', 1),
('통계학', '300', '310', 2),
('경제학', '300', '320', 2),
('사회학, 사회문제', '300', '330', 2),
('정치학', '300', '340', 2),
('행정학', '300', '350', 2),
('법학', '300', '360', 2),
('교육학', '300', '370', 2),
('풍속, 민속학', '300', '380', 2),
('국방, 군사학', '300', '390', 2),
('자연과학', '400', '400', 1),
('수학', '400', '410', 2),
('물리학', '400', '420', 2),
('화학', '400', '430', 2),
('천문학', '400', '440', 2),
('지학', '400', '450', 2),
('광물학', '400', '460', 2),
('생명과학', '400', '470', 2),
('식물학', '400', '480', 2),
('동물학', '400', '490', 2),
('기술과학', '500', '500', 1),
('의학', '500', '510', 2),
('농업, 농학', '500', '520', 2),
('공학, 공학일반', '500', '530', 2),
('건축공학', '500', '540', 2),
('기계공학', '500', '550', 2),
('전기공학, 전자공학', '500', '560', 2),
('화학공학', '500', '570', 2),
('제조업', '500', '580', 2),
('가정학 및 가정생활', '500', '590', 2),
('예술', '600', '600', 1),
('건축술', '600', '610', 2),
('조각', '600', '620', 2),
('공예, 장식미술', '600', '630', 2),
('서예', '600', '640', 2),
('회화, 도화', '600', '650', 2),
('사진술', '600', '660', 2),
('음악', '600', '670', 2),
('연극', '600', '680', 2),
('오락, 운동', '600', '690', 2),
('언어', '700', '700', 1),
('한국어', '700', '710', 2),
('중국어', '700', '720', 2),
('일본어 ', '700', '730', 2),
('영어', '700', '740', 2),
('독일어', '700', '750', 2),
('프랑스어', '700', '760', 2),
('스페인어', '700', '770', 2),
('이탈리아어', '700', '780', 2),
('기타 제어', '700', '790', 2),
('문학', '800', '800', 1),
('한국문학', '800', '810', 2),
('중국문학', '800', '820', 2),
('일본문학', '800', '830', 2),
('영미문학', '800', '840', 2),
('독일문학', '800', '850', 2),
('프랑스문학', '800', '860', 2),
('스페인문학', '800', '870', 2),
('이탈리아문학', '800', '880', 2),
('기타 제문학', '800', '890', 2),
('역사', '900', '900', 1),
('아시아', '900', '910', 2),
('유럽', '900', '920', 2),
('아프리카', '900', '930', 2),
('북아메리카', '900', '940', 2),
('남아메리카', '900', '950', 2),
('오세아니아', '900', '960', 2),
('양극지방', '900', '970', 2),
('지리', '900', '980', 2),
('전기', '900', '990', 2);
