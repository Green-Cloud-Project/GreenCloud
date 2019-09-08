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
 - 해당 플렛폼에서 쓰일 키값

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
    
    /**
     각 플렛폼에서 서버를 통해 본인을 정보에 접근할 수 있는 키값
     @return 키값(엑세스토큰, authkey...)
     */
    String getKey();
}

```

## 각 플렛폼에서의 키값 정보

### 페이스북
엑세스토큰 제공
```
AccessToken accessToken = AccessToken.getCurrentAccessToken();
boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
```

### 구글
백엔드 연동 문서
https://developers.google.com/identity/sign-in/android/backend-auth

GoogleSignInAccount 클래스에 getIdToken()값으로 토큰을 가져 올 수 있다.
```
private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
    try {
        GoogleSignInAccount account = completedTask.getResult(ApiException.class);
        String idToken = account.getIdToken();

        // TODO(developer): send ID Token to server and validate

        updateUI(account);
    } catch (ApiException e) {
        Log.w(TAG, "handleSignInResult:error", e);
        updateUI(null);
    }
}
```

### 카카오톡

REST API 사이트
https://developers.kakao.com/docs/restapi/kakaotalk-api
Session 클래스에서 토큰을 가져올 수 있다. depricated가 되었으니 다른방법 업데이트 필요


### 네이버