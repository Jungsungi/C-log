# C-log
### 프로젝트 개요

독서, 영화감상 등 다양한 문화생활을 하는 현대인들에게 자신의 문화생활을 기록하고 다른 사람들과 나눌 수 있는 
<br>
작지만 소소한 행복을 챙기는 문화기록 플랫폼
<hr>

## 프로젝트 구성

백엔드와 프론트엔드간 api통신으로 구성된 프로젝트
<br>

### 백엔드 API 정보

### Review API Spec
|Method|URL|Parameter|Detail|
|:-----:|:-----:|:-----:|:-----:|
|GET|http://localhost:8080/api/reviews|추후 검색 로직 추가 예정|작성된 리뷰를 검색 및 조회하는 통신|
|POST|http://localhost:8080/api/reviews/add|{"title" : "리뷰 제목", "content" : "리뷰 내용", "grade" : "리뷰 평점", "memberId" : "리뷰 작성자 id 값", "itemId" : "리뷰 작성하는 아이템 id 값"}|리뷰 등록하는 통신|
|GET|http://localhost:8080/pai/reviews/{reviewId}|reviewId : 조회하고자 하는 리뷰의 id 값|작성된 리뷰를 상세조회하는 통신|
|PATCH|http://localhost:8080/api/reviews/edit/{reviewId}|reviewId : 수정하고자 하는 리뷰의 id 값, {"id" : "리뷰 id 값", "title" : "리뷰 제목", "content" : "리뷰 내용", "grade" : "리뷰 평점"}|리뷰 등록하는 통신|
|DELETE|http://localhost:8080/api/reviews/delete/{reviewId}|reviewId : 삭제하고자 하는 리뷰의 id 값|작성된 리뷰를 삭제하는 통신|

### Moview API Spec
|Method|URL|Parameter|Detail|
|:-----:|:-----:|:-----:|:-----:|
|GET|http://localhost:8080/api/item/moviews/boxoffice|-|금주의 박스오피스 조회하는 통신|
|GET|http://localhost:8080/api/item/moviews/{movieId}|"movieId" : 조회하고자 하는 영화 id 값|특정 영화 상세 조회하는 통신|
|GET|http://localhost:8080/api/item/moviews|추후 검색 로직 추가 예정|등록된 영화를 검색 및 조회하는 통신|

### Book API Spec
|Method|URL|Parameter|Detail|
|:-----:|:-----:|:-----:|:-----:|
|GET|http://localhost:8080/api/item/books/bestSeller|-|현재 베스트셀러 조회하는 통신|
|GET|http://localhost:8080/api/item/books/{bookId}|"bookId" : 조회하고자 하는 도서 id 값|특정 도서 상세 조회하는 통신|
|GET|http://localhost:8080/api/item/books|추후 검색 로직 추가 예정|등록된 도서를 검색 및 조회하는 통신|
