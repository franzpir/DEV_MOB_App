package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.interfaces.IBadgeRepository;
import com.example.dev_mob_houet_piron.model.Badge;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class AddBadgePresenterTests {

    @Test
    public void actuallyAskToAddABadge() {
        //Given
        IBadgeRepository mockedBadgeRepository = Mockito.mock(IBadgeRepository.class);
        AddBadgePresenter presenter = new AddBadgePresenter(mockedBadgeRepository);

        //When
        presenter.addBadge("expectedTitle", "expectedDescription", new ArrayList<>());

        //Expected
        Mockito.verify(mockedBadgeRepository, Mockito.times(1)).add(Mockito.any(Badge.class));
    }
}
