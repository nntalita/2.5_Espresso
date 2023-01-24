package ru.kkuzmichev.simpleappforespresso;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testMainActivityText() {

        ViewInteraction textView = onView(withId(R.id.text_home));
        textView.check(matches(isDisplayed()));
        textView.check(matches(withText("This is home fragment")));
    }

    @Test
    public void testNavigationDrawerHome() {
        openMenu();

        ViewInteraction linearLayoutCompatHome = onView(withId(R.id.nav_home));
        linearLayoutCompatHome.check(matches(isDisplayed()));
        ViewInteraction checkedTextHomeView = onView(
                CoreMatchers.allOf(withId(androidx.navigation.ui.R.id.design_menu_item_text), withText("Home")
                ));
        checkedTextHomeView.check(matches(isDisplayed()));

    }

    @Test
    public void testNavigationDrawerGallery() {
        openMenu();

        ViewInteraction linearLayoutCompatGallery = onView(withId(R.id.nav_gallery));
        linearLayoutCompatGallery.check(matches(isDisplayed()));
        ViewInteraction checkedTextGalleryView = onView(
                CoreMatchers.allOf(withId(androidx.navigation.ui.R.id.design_menu_item_text), withText("Gallery")
                ));
        checkedTextGalleryView.check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationDrawerSladeshow() {
        openMenu();

        ViewInteraction linearLayoutCompatSlideshow = onView(withId(R.id.nav_slideshow));
        linearLayoutCompatSlideshow.check(matches(isDisplayed()));
        ViewInteraction checkedTextSlideshowView = onView(
                CoreMatchers.allOf(withId(androidx.navigation.ui.R.id.design_menu_item_text), withText("Slideshow")
                ));
        checkedTextSlideshowView.check(matches(isDisplayed()));
    }

    @Test
    public void testItemViewSlideshowText() {
        openMenu();
        ViewInteraction navigationMenuItemView = onView(withId(R.id.nav_slideshow));
        navigationMenuItemView.check(matches(isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction textView = onView(withId(R.id.text_slideshow));
        textView.check(matches(isDisplayed()));
        textView.check(matches(withText("This is slideshow fragment")));
    }


    private void openMenu() {

        ViewInteraction appCompatImageButton = onView(
                CoreMatchers.allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                CoreMatchers.allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(CoreMatchers.is("com.google.android.material." +
                                                        "appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
