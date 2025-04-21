-- APPROVAL 테이블 삭제 (가장 의존성이 높음)
DROP TABLE IF EXISTS APPROVAL;

-- RESERVATION 테이블 삭제
DROP TABLE IF EXISTS RESERVATION;

-- REQUEST 테이블 삭제
DROP TABLE IF EXISTS REQUEST;

-- OVERDUE 테이블 삭제
DROP TABLE IF EXISTS OVERDUE;

-- LOAN 테이블 삭제
DROP TABLE IF EXISTS LOAN;

-- LIBRARYBOOKS 테이블 삭제
DROP TABLE IF EXISTS LIBRARYBOOKS;

-- CATEGORY 테이블 삭제
DROP TABLE IF EXISTS CATEGORY;

-- BOOKS 테이블 삭제
DROP TABLE IF EXISTS BOOKS;

-- USER 테이블 삭제
DROP TABLE IF EXISTS USER;

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
    date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
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

-- LIBRARYBOOKS 테이블 생성
CREATE TABLE LIBRARYBOOKS (
    lbidx INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bidx INT NOT NULL,
    cidx INT NOT NULL,
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
    startDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    endDate DATETIME,
    CONSTRAINT fk_overdue_loan FOREIGN KEY (lidx) REFERENCES LOAN(lidx) 
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_overdue_user FOREIGN KEY (uidx) REFERENCES USER(uidx) 
        ON UPDATE CASCADE ON DELETE CASCADE
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

-- RESERVATION 테이블 생성 (예약 테이블)
CREATE TABLE RESERVATION (
    ridx INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    lbidx INT NOT NULL,                           
    uidx INT NOT NULL,                            
    reservationDate DATE NOT NULL,  
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

--------------------------------------------------------------------------------------------------------------------------------------

-- USER 샘플 데이터 생성
INSERT INTO USER(id, password, name, phone, email, address, userNumber, role) 
VALUES ('director','$2a$10$GlSGv1jjhBrWSk9ebqOa0uTeVUJ/RWqTLwkf1mA3pMN7ySNySkNQS','도서관장','01012345678','director@aaa.ccc','대한민국','00000000','도서관장'), 
 ('librarian','$2a$10$GlSGv1jjhBrWSk9ebqOa0uTeVUJ/RWqTLwkf1mA3pMN7ySNySkNQS','사서','01012566211','librarian@aaa.ccc','대한민국','00000010','사서'), 
 ('user1','$2a$10$GlSGv1jjhBrWSk9ebqOa0uTeVUJ/RWqTLwkf1mA3pMN7ySNySkNQS','홍길동','0101112223','ktiun9630@naver.com','대한민국','12344444','일반회원'),
 ('user2','$2a$10$GlSGv1jjhBrWSk9ebqOa0uTeVUJ/RWqTLwkf1mA3pMN7ySNySkNQS','이순신','0101112224','user2@aaa.ccs','대한민국','12344645','일반회원');


 

-- BOOKS 샘플 데이터 생성
INSERT INTO BOOKS(coverImg, title, originalTitle, subtitle, author, publisher, publishedYear, totalPages, isbn, sizeWidth, sizeHeight, weight, category, introduction, price, regdate, modify, delyn) 
VALUES('https://image.aladin.co.kr/product/4086/97/cover500/8936434128_2.jpg', '소년이 온다 - 2024 노벨문학상 수상작가', NULL, '2024 노벨문학상 수상작가', '한강 (지은이)', '창비',  '2014-05-19', 216, '9788936434120', '145', '210', '300', '국내도서>소설/시/희곡>한국소설>2000년대 이후 한국소설', 
'섬세한 감수성과 치밀한 문장으로 인간 존재의 본질을 탐구해온 작가 한강의 여섯번째 장편소설. ‘상처의 구조에 대한 투시와 천착의 서사’를 통해 한강만이 풀어낼 수 있는 방식으로 1980년 5월을 새롭게 조명한다.', 15000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/5606/62/cover500/8932027269_3.jpg', '사람, 장소, 환대', NULL, NULL, '김현경 (지은이)', '문학과지성사', '2015-03-31', 297, '9788932027265', '152', '223', '440', '국내도서>사회과학>사회학>사회학 일반', 
'현대의 지성 시리즈. 이 책의 키워드는 사람, 장소, 그리고 환대이다. 이 세 개념은 맞물려서 서로를 지탱한다. 사람임은 일종의 자격이며, 타인의 인정을 필요로 한다. 우리는 환대에 의해 사회 안에 들어가며 사람이 된다. 사람이 된다는 것은 자리/장소를 갖는다는 것이다.', 16000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/18831/19/cover500/893203530x_1.jpg', '날개 환상통', NULL, NULL, '김혜순 (지은이)', '문학과지성사', '2019-03-31', 312, '9788932035307', '128', '205', '350', '국내도서>소설/시/희곡>시>한국시', 
' 문학과지성 시인선 527권. 몸으로 시를 쓰는 시인, ‘시하는’ 시인, 하여 그 이름이 하나의 ‘시학’이 된 시인이 있다. 2019년 올해로 등단 40주년을 맞은 김혜순이다. 그가 전작 &lt;죽음의 자서전&gt;(문학실험실, 2016) 이후 3년 만에 열세번째 시집 &lt;날개 환상통&gt;을 출간했다.', 12000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/4482/44/cover500/8967351275_2.jpg', '21세기 자본 (양장)', 'Capital in the Twenty-First Century (2014년)', NULL, '토마 피케티 (지은이), 장경덕 (옮긴이), 이강국 (감수)', '글항아리', '2014-09-12', 820, '9788967351274', '158', '233', '1270', '국내도서>사회과학>사회사상/사회사상사>사회사상/사회사상사 일반', 
'전 세계에 ‘피케티 신드롬’을 불러일으킨 프랑스 파리경제대 토마 피케티 교수의 &lt;21세기 자본&gt;이 드디어 출간한다. 지난해 8월에 프랑스, 올해 4월에 미국에서 번역 출간된 이후 경제계는 물론 세계 지성인의 주목을 한 몸에 받아온 역작이다.', 33000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/19359/16/cover500/s152835852_1.jpg', '우리가 빛의 속도로 갈 수 없다면 - 2019 제43회 오늘의 작가상 수상작', NULL, '2019 제43회 오늘의 작가상 수상작', '김초엽 (지은이)', '허블', '2019-06-24', 344, '9791190090018', '130', '198', '492', '국내도서>소설/시/희곡>과학소설(SF)>한국 과학소설 ', 
'2017년 ‘관내분실’과 ‘우리가 빛의 속도로 갈 수 없다면’으로 제2회 한국과학문학상 중단편 대상과 가작을 수상하며 작품 활동을 시작한 김초엽 작품집. ‘순례자들은 왜 돌아오지 않는가’, ‘스펙트럼’, ‘공생가설’, ‘우리가 빛의 속도로 갈 수 없다면’, ‘감정의 물성’, ‘관내분실’, ‘나의 우주 영웅에 관하여’가 수록되었다.', 14000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/29496/39/cover500/s382931339_2.jpg', '파친코 1 - 개정판', 'Pachinko (2017년)', '개정판', '이민진 (지은이), 신승미 (옮긴이)', '인플루엔셜(주)', '2022-07-27', 388, '9791168340510', '140', '205', '561', '국내도서>소설/시/희곡>영미소설', 
'4대에 걸친 재일조선인 가족의 이야기를 그린 세계적 베스트셀러, 이민진 작가의 장편소설 《파친코》가 인플루엔셜에서 새로운 번역으로 출간되어 한국 독자들과 만난다. 《파친코》는 재미교포 1.5세인 이민진 작가가 30년에 달하는 긴 세월에 걸쳐 집필한 대하소설이다.', 15800, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/9401/86/cover500/k982635615_1.jpg', '당신 인생의 이야기', 'Stories of Your Life and Others (2002년)', NULL, '테드 창 (지은이), 김상훈 (옮긴이)', '엘리', '2016-10-14', 448, '9788956057842', '140', '210', '565', '국내도서>소설/시/희곡>과학소설(SF)>외국 과학소설', 
'단 한 권의 작품집으로 "전 시대를 통틀어 가장 위대한 과학 단편소설 작가 중의 한 명"이라는 명성을 얻은 테드 창의 &lt;당신 인생의 이야기&gt;. 최고의 과학소설에 수여되는 네뷸러상, 휴고상, 로커스상, 스터전상, 캠벨상, 아시모프상, 세이운상, 라츠비츠상을 모두 석권하였다.', 17000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/7672/12/cover500/8983717734_2.jpg', '멀고도 가까운 - 읽기, 쓰기, 고독, 연대에 관하여', 'The Faraway Nearby (2013년)', '읽기, 쓰기, 고독, 연대에 관하여', '리베카 솔닛 (지은이), 김현우 (옮긴이)', '반비', '2016-02-11', 384, '9788983717733', '130', '205', '450', '국내도서>인문학>인문 에세이', 
'저자 리베카 솔닛은 따뜻하고도 객관적인 시선으로 이야기들이 우리의 삶을 만들어내고 관계를 만들어내는 데 어떤 역할을 하는지 세밀하게 관찰한다. 내밀한 회고록이지만 읽기와 쓰기가 지닌 공적인 효과에 대해서도 유려하게 웅변하는 에세이이다.', 17000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/32293/72/cover500/896564285x_1.jpg', '세계 끝의 버섯', 'The Mushroom at the End of the World: On the Possibility of Life in Capitalist Ruins', NULL, '애나 로웬하웁트 칭 (지은이), 노고운 (옮긴이)', '현실문화', '2023-08-30', 544, '9788965642855', '140', '210', '762', '국내도서>인문학>인류학/고고학>인류학', 
'우리 시대의 가장 이상한 상품사슬의 하나를 따라 자본주의의 예상치 못한 구석을 탐험한다. 한편에 일본의 미식가, 자본주의적 기업가, 다른 한편에서 라오스, 캄보디아의 정글 투사와 백인 참전 용사, 중국 윈난성 소수민족의 염소 목동, 핀란드의 자연 가이드 등 송이버섯을 채집하는 사람들을 만난다.', 35000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/4898/84/cover500/s812035947_1.jpg', '정의란 무엇인가 - 한국 200만 부 돌파, 37개국에서 출간된 세계적 베스트셀러', 'ustice: What`s the Right Thing to Do? (2009년)', '한국 200만 부 돌파, 37개국에서 출간된 세계적 베스트셀러', '마이클 샌델 (지은이), 김명철 (옮긴이), 김선욱 (감수)', '와이즈베리', '2014-11-20', 444, '9788937834790', '152', '223', '795', '국내도서>인문학>서양철학>윤리학/도덕철학', 
'구제 금융, 대리 출산, 동성 결혼, 과거사 공개 사과 등 현대 사회에서 우리가 흔히 부딪히는 문제를 통해 ‘무엇이 정의로운가’에 대한 해답을 탐구했다. 이 책은 탁월한 정치 철학자들이 남긴 시대를 초월한 철학적인 질문을 알기 쉽게 소개한다', 18000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/29137/2/cover500/8936434594_2.jpg', '채식주의자 (리마스터판) - 2024 노벨문학상 수상작가', NULL, '2024 노벨문학상 수상작가', '한강 (지은이)', '창비', '2022-03-28', 276, '9788936434595', '128', '194', '358', '국내도서>소설/시/희곡>한국소설>2000년대 이후 한국소설', 
'2016년 인터내셔널 부커상을 수상하며 한국문학의 입지를 한단계 확장시킨 한강의 장편소설. 상처받은 영혼의 고통과 식물적 상상력의 강렬한 결합을 정교한 구성과 흡인력 있는 문체로 보여주며 섬뜩한 아름다움의 미학을 한강만의 방식으로 완성한 역작이다.', 15000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/9476/48/cover500/8937473135_1.jpg', '82년생 김지영', NULL, NULL, '조남주 (지은이)', '민음사', '2016-10-14', 192, '9788937473135', '135', '195', '294', '국내도서>소설/시/희곡>한국소설>2000년대 이후 한국소설', 
'오늘의 젊은 작가 13권. 조남주 장편소설. 시사 교양 프로그램에서 10년 동안 일한 방송 작가답게 서민들의 일상에서 발생하는 비극을 사실적이고 공감대 높은 스토리로 표현하는 데 특출 난 재능을 보이는 작가는 &lt;82년생 김지영&gt;에서 30대를 살고 있는 한국 여성들의 보편적인 일상을 완벽하게 재현한다.', 14000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/53/28/cover500/s772832909_1.jpg', '고래 - 제10회 문학동네소설상 수상작', NULL, '제10회 문학동네소설상 수상작', '천명관 (지은이)', '문학동네', '2004-12-24', 455, '9788982819278', '152', '223', '673', '내도서>소설/시/희곡>한국소설>2000년대 이후 한국소설', 
'문학동네 소설상이 오랜만에 당선작을 냈다. 주인공은 지난해 여름 \'문학동네 신인상\'을 통해 등단한 천명관씨. 등단작 \'프랭크와 나\'를 제외하곤 아무 작품도 발표하지 않은 진짜 신인이다.', 15000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/5132/31/cover500/8936472585_2.jpg', '금요일엔 돌아오렴 - 240일간의 세월호 유가족 육성기록', NULL, '240일간의 세월호 유가족 육성기록', '416세월호참사 작가기록단 (엮은이)', '창비', '2015-01-16', 348, '9788936472580', '152', '225', '510', '국내도서>에세이>한국에세이', 
'결코 망각될 수 없는 한국 현대사의 비극, 세월호 참사. 학생들은 3박 4일의 수학여행을 마치고 금요일에 돌아오기로 되어 있었다. 그러나 배에 갇힌 일반인 승객과 더불어 집으로 돌아오지 못했다. 이 책은 남겨진 가족들이 가닿을 수 없는 수백개의 금요일에 관한 기록이다.', 18000, '2025-03-22', NULL, 'N'),

('https://image.aladin.co.kr/product/93/70/cover500/8932017956_1.jpg', '끝과 시작 - 비스와바 쉼보르스카 시선집', 'Selected Poems of Wisława Szymborska', '비스와바 쉼보르스카 시선집', '비스와바 쉼보르스카 (지은이), 최성은 (옮긴이)', '문학과지성사', '2016-10-11', 508, '9788932029115', '152', '225', '965', '국내도서>소설/시/희곡>시>외국시', 
'1996년 노벨문학상을 수상한 폴란드의 여성 시인 비슬라바 쉼보르스카의 시선집. 1945년 등단작부터 2005년 작까지, 60여 년에 걸친 시인의 작품 세계를 한눈에 볼 수 있다. \'혼돈과 해체 속에서 사유의 조화로운 동참을 권유하는 미의식\'은, 쉼보르스카의 시학이 이룩한 가장 뛰어난 성과로 평가되어 왔다.', 22000, '2025-03-22', NULL, 'N');

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

-- LIBRARYBOOKS 샘플 데이터 생성
INSERT INTO LIBRARYBOOKS (bidx, cidx, code, callName, location, loanDate, dueDate, returnDate, status, regdate, modify) 
VALUES
(1, 81, 'SS000001', '800.161.01', '일반열람실', '2025-03-23', '2025-03-30', NULL, '대출중', '2025-03-23 10:00:00', NULL),
(2, 82, 'SS000002', '810.162.01', '일반열람실', '2025-03-21', '2025-03-28', '2025-03-28', '대출가능', '2025-03-21 11:00:00', '2025-03-28 12:00:00'),
(3, 83, 'SS000003', '820.163.01', '일반열람실', '2025-03-18', '2025-03-25', '2025-03-25', '대출가능', '2025-03-18 09:30:00', '2025-03-25 15:00:00'),
(4, 14, 'SS000004', '130.101.01', '일반열람실', '2025-03-22', '2025-03-29', NULL, '대출중', '2025-03-22 13:00:00', NULL), -- 대여중
(5, 15, 'SS000005', '140.102.01', '일반열람실', '2025-03-20', '2025-03-27', '2025-03-27', '대출가능', '2025-03-20 11:30:00', '2025-03-27 12:30:00'),
(6, 36, 'SS000006', '350.201.01', '일반열람실', '2025-03-23', '2025-03-30', NULL, '대출중', '2025-03-23 09:00:00', NULL), -- 대여중
(7, 37, 'SS000007', '360.202.01', '일반열람실', '2025-03-15', '2025-03-22', '2025-03-22', '대출가능', '2025-03-15 13:00:00', '2025-03-22 10:30:00'),
(8, 58, 'SS000008', '700.301.01', '일반열람실', '2025-03-20', '2025-03-27', '2025-03-27', '대출가능', '2025-03-20 14:00:00', '2025-03-27 15:30:00'),
(9, 69, 'SS000009', '680.401.01', '일반열람실', '2025-03-16', '2025-03-23', '2025-03-23', '대출가능', '2025-03-16 10:30:00', '2025-03-23 11:00:00'),
(10, 80, 'SS000010', '790.501.01', '일반열람실', '2025-03-14', '2025-03-21', '2025-03-23', '대출가능', '2025-03-14 08:30:00', '2025-03-23 17:00:00'),
(11, 1, 'SS000011', '000.111.01', '보전서고', NULL, NULL, NULL, '대출불가', '2025-03-14 15:30:00', NULL), -- 대출불가
(12, 2, 'SS000012', '010.112.01', '일반열람실', NULL, NULL, NULL, '예약대기', '2025-03-12 12:30:00', NULL),-- 예약중
(13, 3, 'SS000013', '020.113.01', '일반열람실', NULL, NULL, NULL, '대출가능', '2025-03-15 16:30:00', NULL),
(14, 4, 'SS000014', '030.114.01', '일반열람실', NULL, NULL, NULL, '대출가능', '2025-03-11 10:30:00', NULL),
(15, 5, 'SS000015', '040.115.01', '일반열람실', NULL, NULL, NULL, '대출가능', '2025-03-11 08:30:00', NULL),
(14, 4, 'SS000016', '030.114.02', '일반열람실', NULL, NULL, NULL, '대출가능', '2025-03-11 10:30:00', NULL),
(15, 5, 'SS000017', '040.115.02', '일반열람실', NULL, NULL, NULL, '대출가능', '2025-03-11 08:30:00', NULL);
-- LOAN 샘플 데이터 생성
INSERT INTO LOAN (lbidx, uidx, loanDate, dueDate, returnDate, status, regdate, modify) 
VALUES 
(1, 3, '2025-03-23', '2025-03-30', NULL, '대출중', '2025-03-23 10:00:00', NULL), -- 대여중
(2, 3, '2025-03-21', '2025-03-28', '2025-03-28', '반납완료', '2025-03-21 11:00:00', '2025-03-28 12:00:00'), -- 반납완료
(3, 3, '2025-03-18', '2025-03-25', '2025-03-25', '반납완료', '2025-03-18 09:30:00', '2025-03-25 15:00:00'), -- 반납완료
(4, 3, '2025-03-22', '2025-03-29', NULL, '대출중', '2025-03-22 13:00:00', NULL), -- 대여중
(5, 3, '2025-03-20', '2025-03-27', '2025-03-27', '반납완료', '2025-03-20 11:30:00', '2025-03-27 12:30:00'), -- 반납완료
(6, 4, '2025-03-23', '2025-03-30', NULL, '대출중', '2025-03-23 09:00:00', NULL), -- 대여중
(7, 4, '2025-03-15', '2025-03-22', '2025-03-22', '반납완료', '2025-03-15 13:00:00', '2025-03-22 10:30:00'), -- 반납완료
(8, 4, '2025-03-20', '2025-03-27', '2025-03-27', '반납완료', '2025-03-20 14:00:00', '2025-03-27 15:30:00'), -- 반납완료
(9, 4, '2025-03-16', '2025-03-23', '2025-03-23', '반납완료', '2025-03-16 10:30:00', '2025-03-23 11:00:00'), -- 반납완료
(10, 4, '2025-03-14', '2025-03-21', '2025-03-23', '연체반납', '2025-03-14 08:30:00', '2025-03-23 17:00:00'); -- 연체반납

-- OVERDUE 샘플 데이터 생성
INSERT INTO OVERDUE (lidx, uidx, status, startDate, endDate)
VALUES (10, 4, 'Y', '2025-03-23', '2025-03-27');

-- REQUEST 샘플 데이터 생성
INSERT INTO REQUEST(uidx,bidx,status,rejectionReason) 
VALUES (3, 1, '신청중', null),
(4, 2, '신청중', null),
(4, 3, '신청중', null),
(3, 4, '신청대기', null),
(3, 5, '신청완료', null),
(3, 6, '신청반려', '불필요한 도서'),
(4, 7, '신청중', null),
(4, 8, '신청중', null),
(3, 9, '신청중', null),
(3, 10, '신청중', null),
(3, 11, '신청중', null),
(4, 12, '신청중', null),
(4, 13, '신청중', null);


INSERT INTO RESERVATION (lbidx, uidx, reservationDate, pickupDate, status, dueDate, modify)
VALUES
(12, 3, '2025-03-24', '2025-03-25', '예약중', '2025-04-01', NULL),
(13, 4, '2025-03-23', '2025-03-24', '예약취소', '2025-3-31', '2025-03-24 15:00:00'),
(14, 4, '2025-03-22', '2025-03-23', '수령완료', '2025-03-30', '2025-03-23 09:00:00'),
(15, 3, '2025-03-27', '2025-03-29', '예약중', '2025-04-05', NULL);

-- APPROVAL 샘플 데이터 생성
INSERT INTO APPROVAL(uidx,rqidx,bidx,status,rejectionReason) 
VALUES  (2,1,null,'반려','예산부족'), 
(2,2,null,'승인',''), 
(2,3,null,'대기',''), 
(1,5,null,'승인',''), 
(1,6,null,'반려','불필요한 도서'), 
(1,7,null,'대기',''), 
(1,8,null,'승인',''), 
(1,9,null,'승인',''), 
(1,10,null,'대기',''), 
(2,11,null,'대기',''), 
(1,12,null,'승인',''), 
(1,13,null,'승인',''), 
(2,null,14,'대기',''), 
(1,null,15,'승인','');