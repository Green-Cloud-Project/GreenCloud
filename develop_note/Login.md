# 로그인 기능 만들기

## 프로바이더 만들기
각 플렛폼에대한 로그인 기능을 제공하는 프로바이더 클래스를 생성
```
FacebookLoginProvider
KakaoLoginProvider
NaverLoginProvider
GoogleLoginProvider
```

## 프로바이더 인터페이스 생성
로그인에 필요한 공통 기능을 정의
 - 로그인
 - 로그아웃
 - 현재 해당 플렛폼에 대한 로그인 상태인지?

```
/**
 로그인을 하기위한 공통기능 정의
 */
public interface ILoginProvider {
    /**
     로그인을 한다.
     */
    void signIn(AppCompatActivity appCompatActivity);

    /**
     로그아웃을 한다.
     */
    void signOut();

    /**
     현재 로그인 상태인지?
     */
    boolean isSignIn();

    /**
     로그인 성공 실패여부
     */
    void signInResult(boolean isSuccess);
}

```