# 인스타그램 클론코딩 프로젝트입니다.

## 주요기능
- 게시글
  - 게시글 CRUD
  - 사진등록(AWS S3 사용)
  - 좋아요 등록, 취소
  - 댓글 CRUD
 
 
- 회원
  - 로그인(JWT, Spring Security)
  - 회원가입(정규식)
  - 팔로우 기능
 
## ERD

![클론코딩 (1)](https://user-images.githubusercontent.com/95573777/218987113-2dbc05f4-972e-4799-8f38-77377711e97c.png)

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
- WebSocket

## 2. 트러블 슈팅

 - 나의 게시글에서 내가 게시한 글만 받아올때 전체조회 기능만 있는 오류
 - 게시글 좋아요 기능을 했을때 해당하는 postman에서 결과는 likeCheck가 true가 뜨지만 전체조회를하면 false로 결과값이 바뀌지 않는 오류
 - 게시글을 삭제 했을때 게시글은 삭제가 되었지만 DB에서 게시글좋아요 및 댓글이 삭제되지 않았던 오류


### 1. (이미지 타입 예외)
문제 상황 : postman으로 사진을 전송하는데 '415 Unsupported Media Type' 이라는 예외 상황을 마주침
</br>문제 원인 : 'Content Type'을 설정해 주지 않음
</br>해결 방법 : 'Content Type'을 application/json 으로 바꿔주니 정상적으로 동작됨.

### 2. (pull 받는 과정에서의 오류)
문제 상황 : 다른 팀원의 수정된 코드를 테스트를 위해 pull를 받고 프로그램을 실행 시켰는데 requesetMappingHandlerMapping 이라는 오류가 뜸
</br>문제 원인 : 수정내용을 pull 받는 과정에서 중복된 url을 사용하여 mapping을 할 수 없다는 오류가 뜸
</br>해결 방법 : Controller에 중복된 요청 하나를 제거해주니 문제없이 해결됨.

### 3. (파일 업로드 예외)
문제 상황 : 새로 업데이트 된 소스를 테스트 해보기 위해 게시글의 파일업로드를 하는 과정에서 java.lang.NullPointerException: null 이라는 예외가 뜸
</br>문제 원인 : 
- 가정 1. 업데이트 된 소스를 pull 받아서 테스트하는 과정에서 딜레이로 인해 소스가 최신화 되지 않음
- 가정 2. 파일의 크기가 너무 커서 파일을 전송할 수 없어서 null이 뜸
</br>해결 방법 : 설정에 파일 용량을 늘려주는 설정을 추가해서 해결함

### 4. (DB 오류)
문제 상황 : 프로그램이 실행되면 table이 초기화 될 수 있도록 설정해 두었는데 프로그램을 종료 후 실행해도 초기화 되지 않음
</br>문제 원인 : DB를 공유하고 있을 때 내가 프로그램을 종료하더라도 다른 쪽에서 종료하지 않고 실행 중이면 table 이 초기화 되지 않음
</br>해결 방법 : 같은 DB를 공유 중이 프로그램을 모두 종료하니 table 이 초기화 됨

### 5. (특정필드에 의존주입이 실패했던 오류)
문제 상황 : 프로젝트를 실행헀을 때 [UnsatisfiedDependencyException/BeanCreationException error]
</br>문제 원인 : 파일을 실행했을때 특정 필드에 의존주입이 실패했기 때문임
</br>해결 방법 : 단순 어노테이션 오류였음 @Service어노테이션을 넣지 않아서 생긴 문제였음

### 6. (나의 게시글을 조회 기능)
문제 상황 : Postman에서 실행을 했을때 나의 게시글 조회를 했는데 전체 게시글 조회가 되는 상황
</br>문제 원인 : mypostRepository에서 jpa가 findbypostId중복되서 나오는 단순 오타오류 
</br>해결 방법 :  jpa를 findbyMemberId로 바꿔주고 해당하는 문을  추가해줘서 해결했습니다

### 7. (좋아요 조회기능 오류)
문제 상황 : 좋아요 API를 Postman으로 실행했을때 boolean type으로 true실행이 되지만 전체조회를 했을때 likecheck가 false로 뜨는 점
</br>문제 원인 : PostService에서 ResponseDto에 좋아요 기능을 넣지않아 생겼음
</br>해결 방법 : PostService에서 DB에 저장되어있는 likeCheck를 ResponseDto로 넣어주면서 해결
