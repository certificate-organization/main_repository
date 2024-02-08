
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


@5binn

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

@ryuhanseon
### 🚨 Issue 3
### 🚧 합계 이슈

A. 이슈 내역
- <br>

문제점 설명
- <br>
## 🛑 원인
- 

## 🚥 해결
- 
<br>
@kimjeongbae
### 🚨 Issue 4
### 🚧 합계 이슈

A. 이슈 내역
- <br>

문제점 설명
- <br>
## 🛑 원인
- 

## 🚥 해결
- 
<br>
