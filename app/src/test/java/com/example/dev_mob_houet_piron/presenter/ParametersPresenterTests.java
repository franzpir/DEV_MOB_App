package com.example.dev_mob_houet_piron.presenter;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dev_mob_houet_piron.data.interfaces.IPersonRepository;
import com.example.dev_mob_houet_piron.data.interfaces.IPlaceRepository;
import com.example.dev_mob_houet_piron.model.Person;
import com.example.dev_mob_houet_piron.model.Place;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayParameters;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mockito;

import java.util.Observable;

public class ParametersPresenterTests {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void actuallyAskToLoadPerson() {
        //Given
        IPersonRepository mockedPersonRepository = Mockito.mock(IPersonRepository.class);
        IDisplayParameters mockedParametersDisplayer = Mockito.mock(IDisplayParameters.class);
        ParametersPresenter presenter = new ParametersPresenter(mockedPersonRepository, mockedParametersDisplayer);
        MutableLiveData<Person> observablePerson = new MutableLiveData<>();
        Mockito.when(mockedPersonRepository.getPerson(Mockito.anyString())).thenReturn(observablePerson);

        //When
        presenter.loadPerson();
        observablePerson.setValue(new Person());

        //Expected
        Mockito.verify(mockedPersonRepository, Mockito.times(1)).getPerson("user1");
    }

    @Test
    public void doesNotAskUpdateIfNameIsEmpty(){
        //Given
        IPersonRepository mockedPersonRepository = Mockito.mock(IPersonRepository.class);
        IDisplayParameters mockedParametersDisplayer = Mockito.mock(IDisplayParameters.class);
        ParametersPresenter presenter = new ParametersPresenter(mockedPersonRepository, mockedParametersDisplayer);

        //When
        boolean returned =presenter.updateUser("");

        //Expected
        Mockito.verify(mockedPersonRepository, Mockito.never()).updatePerson(Mockito.any());
        Assert.assertFalse(returned);

    }

    @Test
    public void doesNotAskUpdateIfNameIsNull(){
        //Given
        IPersonRepository mockedPersonRepository = Mockito.mock(IPersonRepository.class);
        IDisplayParameters mockedParametersDisplayer = Mockito.mock(IDisplayParameters.class);
        ParametersPresenter presenter = new ParametersPresenter(mockedPersonRepository, mockedParametersDisplayer);

        //When
        boolean returned = presenter.updateUser(null);

        //Expected
        Mockito.verify(mockedPersonRepository, Mockito.never()).updatePerson(Mockito.any());
        Assert.assertFalse(returned);
    }

    @Test
    public void askUpdateIfNameIsValid(){
        //Given
        IPersonRepository mockedPersonRepository = Mockito.mock(IPersonRepository.class);
        IDisplayParameters mockedParametersDisplayer = Mockito.mock(IDisplayParameters.class);
        ParametersPresenter presenter = new ParametersPresenter(mockedPersonRepository, mockedParametersDisplayer);
        MutableLiveData<Person> observablePerson = new MutableLiveData<>();
        Mockito.when(mockedPersonRepository.getPerson(Mockito.anyString())).thenReturn(observablePerson);

        //When
        presenter.loadPerson();
        observablePerson.setValue(new Person());
        boolean returned = presenter.updateUser("validName");

        //Expected
        Mockito.verify(mockedPersonRepository, Mockito.times(1)).updatePerson(Mockito.any());
        Assert.assertTrue(returned);
    }
}
