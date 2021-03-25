package com.example.dev_mob_houet_piron.view;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.data.room.repository.LocalPlaceRepository;
import com.example.dev_mob_houet_piron.model.Place;
import com.example.dev_mob_houet_piron.utils.LiveDataTestUtil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddPlaceTests {

    @Rule
    public ActivityTestRule<MainActivity> mIntentTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule = GrantPermissionRule.grant("android.permission.ACCESS_FINE_LOCATION");

    @Before
    public void setUp() {
        Intents.init();
        clearDataBase("TestName", "TestDescription");
    }

    @After
    public void cleanUp() {
        Intents.release();
        clearDataBase("TestName", "TestDescription");
    }

    @Test
    public void displayAddPlaceFragmentOnMainActivity() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_marker_button),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(R.id.activity_mainpage_fragment_container),
                                                0)),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.addCache_title), withText("Ajout d'une géocache"), isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    @Test
    public void addPlaceDisplayPlacePicker() {
        String writtenName = "TestName";
        String writtenDescription = "TestDescription";

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_marker_button),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(R.id.activity_mainpage_fragment_container),
                                                0)),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.add_cache_name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText(writtenName), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.add_cache_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText(writtenDescription), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.place_pick_btn), withText("Localisation"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        //Expected: Après avoir cliqué sur le boutton "Localisation", on veut que l'activité
        //PlacePickerActivity soit lancée:
        intended(hasComponent(PlacePickerActivity.class.getName()));
    }

    @Test
    public void addPlaceActuallyAddAPlace() {
        String writtenName = "TestName";
        String writtenDescription = "TestDescription";

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_marker_button),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(R.id.activity_mainpage_fragment_container),
                                                0)),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.add_cache_name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText(writtenName), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.add_cache_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText(writtenDescription), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.place_pick_btn), withText("Localisation"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.place_pick_validation),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.save_btn), withText("Sauvegarder"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        checkIfPlaceHasBeenSavedProperly(writtenName, writtenDescription);
    }

    private void checkIfPlaceHasBeenSavedProperly(String expectedName, String expectedDescription) {
        try {
            List<Place> placesFound = LiveDataTestUtil.getOrAwaitValue(LocalPlaceRepository.getInstance().getPlacesForTest(expectedName, expectedDescription));
            assertEquals(1, placesFound.size());
            assertEquals(placesFound.get(0).getName(), expectedName);
            assertEquals(placesFound.get(0).getDescription(), expectedDescription);
        } catch (InterruptedException e) {
            Assert.fail();
        }
    }

    private void clearDataBase(String testName, String testDescription) {
        LocalPlaceRepository.getInstance().clearForTests(testName, testDescription);
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
