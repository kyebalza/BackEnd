# 0. BackClone

## Member

 - 계현준
 - 이기재
 - 장경원
 - 정규재
 
## Dependencies
- Lombok
- MySQL Driver
- H2 Database
- Spring Web
- Spring Security
- Validation
- Spring Data JPA
- Spring Boot Dev Tools

## 2. 트러블 슈팅

 - 415 Unsupported Media Type
 - requesetMappingHandlerMapping
 - mysql table column 초기화 안되는 오류
 - 프론트와 연결했을때 상세페이지 보일때 있고 안보일때 있는 오류
 - 
 - 나의 게시글에서 내가 게시한 글만 받아올때 전체조회 기능만 있는 오류
 - 게시글 좋아요 기능을 했을때 해당하는 postman에서 결과는 likeCheck가 true가 뜨지만 전체조회를하면 false로 결과값이 바뀌지 않는 오류
 - 게시글을 삭제 했을때 게시글은 삭제가 되었지만 DB에서 게시글좋아요 및 댓글이 삭제되지 않았던 오류


### 1. (이미지 타입 예외)
문제 상황 : postman으로 사진을 전송하는데 '415 Unsupported Media Type' 이라는 예외 상황을 마주침
문제 원인 : 'Content Type'을 설정해 주지 않음
해결 방법 : 'Content Type'을 application/json 으로 바꿔주니 정상적으로 동작됨.

### 2. (pull 받는 과정에서의 오류)
문제 상황 : 다른 팀원의 수정된 코드를 테스트를 위해 pull를 받고 프로그램을 실행 시켰는데 requesetMappingHandlerMapping
		이라는 오류가 뜸
문제 원인 : 수정내용을 pull 받는 과정에서 중복된 url을 사용하여 mapping을 할 수 없다는 오류가 뜸
해결 방법 : Controller에 중복된 요청 하나를 제거해주니 문제없이 해결됨.

### 3. (파일 업로드 예외)
문제 상황 : 새로 업데이트 된 소스를 테스트 해보기 위해 게시글의 파일업로드를 하는 과정에서 java.lang.NullPointerException: null
		이라는 예외가 뜸
문제 원인 : 
- 가정 1. 업데이트 된 소스를 pull 받아서 테스트하는 과정에서 딜레이로 인해 소스가 최신화 되지 않음
- 가정 2. 파일의 크기가 너무 커서 파일을 전송할 수 없어서 null이 뜸
해결 방법 : 설정에 파일 용량을 늘려주는 설정을 추가해서 해결함
  
  
  
  
test
