package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.interfaces.IPlaceRepository;
import com.example.dev_mob_houet_piron.model.Place;

import org.junit.Test;
import org.mockito.Mockito;

public class AddPlacePresenterTests {

    @Test
    public void actuallyAskToAddAPlace() {
        //Given
        IPlaceRepository mockedPlaceRepository = Mockito.mock(IPlaceRepository.class);
        AddPlacePresenter presenter = new AddPlacePresenter(mockedPlaceRepository);

        //When
        presenter.addPlace(5, 55, "expectedName", "expectedDescription");

        //Expected
        Mockito.verify(mockedPlaceRepository, Mockito.times(1)).add(Mockito.any(Place.class));
    }
}
