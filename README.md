
![주석 2024-01-23 175158](https://github.com/5binn/accountBook/assets/149226397/25dfd90b-4895-48fd-8177-aaeab5628e4e)

### 서비스 설명
#### 회원 관련 기능
- 회원가입
- 로그인/로그아웃
- 소셜 로그인(카카오)
- 회원정보 열람/수정

#### 게시판 관련 기능
- 게시글 작성, 검색, 수정 , 삭제
- 게시글 조회수, 좋아요 기능.
- 댓글 작성, 수정, 삭제
- 대댓글 작성, 수정, 삭제

#### 신고 관련 기능
- 게시글 신고 
- 댓글, 대댓글 신고
- 신고 카테고리에 따른 신고 제출

#### 관리자 권한 기능
- 신고글 열람 가능 
- 댓글, 게시글 삭제 가능 
- MBTI  상세 정보 수정 기능

#### 채팅 관련 기능
- 해당 MBTI 카테고리 입장시 
- 카테고리별 실시간 채팅 기능

## 🛠 개발환경
| 분류 | 설명 |
|:--------:|:--------:|
| 운영체제  | Chrome   |
| 통항 개발 환경   | IntelliJ   |
| 프로그래밍 언어   | Java   |
| 버전 관리 시스템   | Git, Github   |
| 데이터베이스   | MySQL   |
| 프레임워크   | Spring   |


<br/>
<br/>

## ☁️ ERD

![image](https://github.com/5binn/accountBook/assets/149226397/1fa2db17-d002-443a-a952-0e16d2c95964)


<br>
<br>

## 👀 시연영상


[![Video Label]

## 🔥 트러블 슈팅

### 🚨 Issue 1
### 🚧 비밀번호 검증

A. 이슈 내역
- 비밀 번호 일치 확인 실패<br>

B. 문제점 설명
- 비밀번호가 일치하는지 검증하는 메서드에서 비밀번호를 맞게 입력했는데도 일치하지 않는다고 뜨는 문제.<br>
## 🛑 원인
전제로, 회원가입을 할 때에 입력한 비밀번호 평문은 PasswordEncoder에 의해서 인코딩 되어 난수로 DB에 저장이 된다.

```java
public void create(... , String password, ...) {
        Member member = Member.builder()

                (생략)

                .password(passwordEncoder.encode(password))

                (생략)

                .build();

        this.memberRepository.save(member);
    }
```

- 실제 저장값

```
예시)

$2a$10$kIZDs4KJQfGZHS8yL9M4b.mBCkg6zceRFtXaPQlKm6Ry47FyBg6eS
```

따라서,

```java

passwordConfirm 메서드)

public boolean passwordConfirm(String password,Member member) {
        if(password.equals(member.getPassword())){
            return true;
        }
        return false;
}
```

실제 DB에 있는 Member타입의 member객체 내에있는 member.getPassword()는 위와같은 난수임.

로그인과 같은 비밀번호 검증을 할 시에 입력받는 비밀번호 평문인
passwordConfirm메서드의 String 타입 매개변수 password는 다른 값임.

## 🚥 해결
PasswordEncoder의 matches 메서드를 사용하면
매개변수로 받은 password 변수를 메서드 내에서 인코딩해서 비교해 boolean값으로 리턴한다.

passwordEncoder.matchs(입력받은 평문 비밀번호 , 실제 DB내에 있는 인코딩된 비밀번호)으로 사용할 수 있다.

```java
public boolean passwordConfirm(String password, Member member) {
    return passwordEncoder.matches(password, member.getPassword());
}
```
<br>

### 🚨 Issue 2
### 🚧 출력 이슈

A. 이슈 내역
- List<String>타입으로 DB에 데이터를 저장하려함<br>

문제점 설명
![Image](https://github.com/mbti-organization/main_repository/assets/149226397/f7b490e9-d6c7-4ef9-a505-357136528702)
- MBTI엔티티에서 요소들을 List<String> elements 로 선언하고 저장하려 했으나 다른 타입으로 저장이 됨<br>
## 🛑 원인
- JPA에서 따로 설정을 하지 않으면 List<String>타입을 인식하지 못하고 매핑을 시도하지 않습니다.


## 🚥 해결
- 컨버터를 사용하여 데이터를 변환하도록 하였습니다.
```java
//칼럼이 List<String> 타입일 때, 변환하여 DB에 저장/사용
@Converter(autoApply = true)
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ",";
	
    //String형태로 변환
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute != null ? String.join(DELIMITER, attribute) : null;
    }
    
	//다시 List형태로 변환
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.asList(dbData.split(DELIMITER)) : null;
    }
}
```
<br>
- 

<br>

### 🚨 Issue 3
### 🚧 합계 이슈

A. 이슈 내역
- 처음 댓글 신고에 대한 모달 라디오 버튼을 구현하여 잘 작동 되었지만
게시글에 대한 신고 모달 라디오 버튼을 구현하였을 때는 먼저 구현한 댓글신고 라디오버튼이 작동 되지 않았다.
<br>
## 🛑 원인

```html

<div aria-hidden="true" aria-labelledby="reportModalArticle" class="modal fade" id="reportArticleModal"tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
                	<div class="modal-header">
                    		<h1 class="modal-title fs-5" id="reportModalArticle">신고내용</h1>
                    			<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
		</div>
	</div>
</div>
```



```html
<div aria-hidden="true" aria-labelledby="reportModalComment" class="modal" id="reportCommentModal"tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="reportModalComment">신고내용</h1>
					<button aria-label="Close" class="btn-close" data-bs-dismiss="modal" type="button"></button>
			</div>
		</div>
	</div>
</div>
```

```HTML
<button class="btn btn-sm btn-outline-secondary" data-bs-target="#reportArticleModal"
	data-bs-toggle="modal" sec:authorize="isAuthenticated()"
	type="button">신고
</button>
```
처음에는 id값만 다르게 해줘서 정상 작동되게 해주려 했지만
게시글은 하나인데 댓글은 여러개가 달릴수도 있다는 사실을 간과 하였다.
그래서 따로 댓글을 반복해줘야 하는데 그걸 구현을 안했었다.

## 🚥 해결
그래서 구현을 해주려 했지만 그 과정보다는 자바스크립트를 이용하는 방법이 더 간결하고 가독성이 좋아서 
자바스크립트를 사용하였다.

```HTML
    <script>
        $(".reportComment").on('click', function() {
              var commentId = $(this).attr('data-comment-id');
              $("input[name=commentId]").val(commentId)
        })
    </script>
```
<br>

### 🚨 Issue 4
### 🚧 합계 이슈

A. 이슈 내역
- html에 공통적으로 사용 하기 위해 navbar를 `navbar.html` 새로 만들고 `layout.html`에 `th:replace`속성으로포함시켜서 사용을 하던 도중에 팀원들과 PR을 한 이후에  로그인,회원가입 페이지에서`net::ERR_INCOMPLETE_CHUNKED_ENCODING 200 (OK)` 라는 오류와 함께 페이지가 일부만 로딩 되거나 아예 로딩이 되지 않는 현상이 발생 하였다.
<br>

## 🛑 원인

### 다크모드를 위해 Img 대신 svg
  
  ### 1. SVG
  `(Scalable Vector Graphics)는 XML 기반의 파일 형식`
  
  다크모드를 구현하기 위해 Img 대신 Svg 파일을 넣은 이후 PR을 하였기 때문에
  SVG가 로딩 되면서 에러를 일으켰을거라고 생각을 하였음 .
  
  실제로 navbar에서 svg 파일을 주석처리하거나 img파일로 대체 하였을때
  `net::ERR_INCOMPLETE_CHUNKED_ENCODING 200 (OK)`가 발생하지 않았음.
  
  하지만 프로젝트를 진행하면서 이것도 일시적 현상 이였다는걸 확인하였음.
  
  ### 2. Spring Security 및 CSRF 토큰 확인
  
 주로 로그인 / 회원가입 페이지에서 확정적으로 발생을 하였고, 에러 메세지를 확인한 결과  
 form에서 th:action="@{} 를 사용하면 해당 오류가 발생하는것을 확인 하였음. 또한 `net::ERR_INCOMPLETE_CHUNKED_ENCODING 200 (OK)`이 확정적으로 발생하는 지점을 확인 하였는데 로그인 이후 -> 로그아웃 -> 회원 가입 으로 페이지를 가면 무조건 CHUNKED 에러가 발생하고 로그인 상태에서 회원가입 페이지로 계속 넘어가도 CHUNKED 에러는 발생하지 않는 현상을 발견하였음.
 ```
 An error happened during template parsing
 [dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] 
 in context with path [] threw exception [Request processing failed: 
 org.thymeleaf.exceptions.TemplateInputException: An error happened during template parsing
 Cannot render error page for request [/member/signup] and exception 
 [An error happened during template parsing
 as the response has already been committed.
 As a result, the response may have the wrong status code.
 ```
1.세션 생성 시점 확인: 세션을 생성하는 코드가 응답이 이미 커밋된 후에 실행되지 않도록 확인
2.응답 커밋 확인: 응답이 커밋되기 전에 세션을 생성하도록 코드를 구성 응답이 커밋된 후에는 세션을 생성할 수 없습니다.
3.Spring Security 및 CSRF 토큰 확인: 스프링 시큐리티와 관련이 있는 것으로 보이는데, CSRF 토큰과 관련된 코드를 살펴보고 세션을 올바르게 관리하는지 확인
<br>
 
 # ❗ 주 원인

`net::ERR_INCOMPLETE_CHUNKED_ENCODING 200 (OK)` 오류의 주된 원인은 Thymeleaf가 콘텐츠를 생성하자마자 즉시 출력하기 때문. 프로세서가 갑자기 양식에 도달하면 Spring Security가 시작되고 양식에 CSRF 토큰을 삽입하기 위해 새 세션을 생성하려고 합니다


## 🚥 해결

 ## 👍 해결 방안 1.Spring Security 세션 정책 변경 방법
  - sessionCreationPolicy(SessionCreationPolicy.ALWAYS) :  스프링 시큐리티가 항상 세션 생성
- sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) : 스프링 시큐리티가 필요 시 생성(기본 값)
- sessionCreationPolicy(SessionCreationPolicy.NEVER) : 스프링 시큐리티가 생성하지 않지만 이미 존재하면 사용
- sessionCreationPolicy(SessionCreationPolicy.STATELESS) : 스프링 시큐리티가 생성하지도 않고 존재해도 사용하지 않음
 
 ``` java
   protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
    }
      
  ```
  Spring Security 세션 정책을 기본값에서 항상 세션을 생성 하는 방법으로 변경을 하면 해결할 수 있습니다.
  하지만 항상 세션을 생성 하는게 기본값이 되면 서버측 리소스를 사용 하므로 불필요한 자원의 낭비와 보안에 대해 취약성을 가질 수 있으므로 이 방법은 권장하지 않습니다. 
  <br>
## 👍 해결 방안 2. Meta tag (메타 태그) 사용법
### Meta tag란 ?

메타 태그는 검색 엔진 최적화(SEO)와 같은 목적으로 사용되며, 브라우저가 페이지를 렌더링하는 방식을 제어하는 데도 사용됩니다. 페이지의 내용을 설명하고 브라우저가 페이지를 적절하게 렌더링하도록 돕는 중요한 역할
  
 ### 1. Meta 태그를 사용하여 csrf 토큰 값 전달하는 방법
 
  layout.html  또는 필요한 html `head` 부분에 
 ` <meta name="_csrf" th:content="${_csrf.token}"/>`
 `<meta name="_csrf_header" th:content="${_csrf.headerName}"/>`
 meta 태그로 csrf 값을 넣어주고
 ``` html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" th:content="${_csrf.token}"/>
    <meta name="_csrf_header" th:content="${_csrf.headerName}"/>
  </head>
  ```

 
로그인 / 회원가입 같이 csrf토큰 처리해주는 뷰에 다음과 같은 스크립트를 사용한다
 
 ``` javascript
<script th:inline="javascript">
    $(function () {
      var token = $("meta[name='_csrf']").attr("content");
      var header = $("meta[name='_csrf_header']").attr("content");
      $(document).ajaxSend(function(e, xhr, options) {
          xhr.setRequestHeader(header, token);
      });
  });
</script>
```

### 2. csrfMetaTags를 사용하여 한번에 적용하는 방법

``` javascript
<script type="application/javascript" th:inline="javascript" th:fragment="ajax-csrf-header">
    $(function () {
        var csrfToken = /*[[${_csrf.token}]]*/ '';
        var csrfHeader = /*[[${_csrf.headerName}]]*/ '';
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        });
    });
</script>
```
Thyemleaf 템플릿 엔진을 이용하여 csrf토큰값과 헤더 이름을 가져와서 
AJAX 요청에 대한 보안헤더를 설정하고 설정된 csrf토큰과 헤더는 모든 AJAX요청이
서버로 전송될떄 함께 전송된다.

AJAX `Asynchronous JavaScript and XML` 란?
웹 페이지가 새로고침되지 않고도 서버와 비동기적으로 데이터를 교환할 수 있는 기술
사용자 경험 향상,서버 부담 감소,자원 효율성,동적 업데이트 


xhr `XMLHttpRequest` 란?
AJAX(Asynchronous JavaScript and XML) 기술에서 사용되며 데이터 요청 , 전송 , 
비동기 통신 의 기능을 수행 할수 있는 자바스크립트 객체

## 👍 해결 방안 3. application.yml 파일 설정

`application.yml` 에서
  ```
  thymeleaf:
    servlet:
      produce-partial-output-while-processing: false
  ```
해당 설정은 Thymeleaf가 템플릿을 처리하는 방식을 제어하여 출력의 생성 시점을 조정하는 데 사용하는데 false로 설정을하여 Thymeleaf가 템플릿을 완전히 처리한 후에야 출력을 생성하도록 합니다. 즉, 전체 템플릿이 처리된 후에야 클라이언트에게 결과를 전송하도록 합니다.
