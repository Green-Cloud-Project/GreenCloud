package KaKaoLoginTest;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.share.greencloud.R;
import com.share.greencloud.kakaologin.KakaoLoginActiviy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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