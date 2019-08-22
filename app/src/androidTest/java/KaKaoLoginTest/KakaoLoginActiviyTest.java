package KaKaoLoginTest;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.share.greencloud.R;
import com.share.greencloud.kakaologin.KakaoLoginActiviy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class KakaoLoginActiviyTest {

    @Rule
    public ActivityTestRule<KakaoLoginActiviy> activityTestRule
            = new ActivityTestRule<>(KakaoLoginActiviy.class);

    @Test
    public void clickLoginButton() {

        onView(withId(R.id.com_kakao_login)).perform(click());

    }

}