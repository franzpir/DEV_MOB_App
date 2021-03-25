package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.interfaces.IPersonRepository;
import com.example.dev_mob_houet_piron.data.room.repository.PersonRepository;
import com.example.dev_mob_houet_piron.model.Person;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayParameters;

public class ParametersPresenter {

    private final IDisplayParameters parameterDisplayer;
    private final IPersonRepository personRepository;
    private Person person;

    public ParametersPresenter(IDisplayParameters parameterDisplayer) {
        this.personRepository = PersonRepository.getInstance();
        this.parameterDisplayer = parameterDisplayer;
    }

    public ParametersPresenter(IPersonRepository personRepository, IDisplayParameters parameterDisplayer) {
        this.personRepository = personRepository;
        this.parameterDisplayer = parameterDisplayer;
    }

    public void loadPerson() {
        personRepository.getPerson("user1").observeForever((person) -> {
            this.person = person;
            this.parameterDisplayer.setCurrentValues(person.getName());
        });
    }

    public boolean updateUser(String userName) {
        if(userName != null && userName.length() > 0) {
            this.person.setName(userName);
            personRepository.updatePerson(person);
            return true;
        } else {
            return false;
        }
    }
}
