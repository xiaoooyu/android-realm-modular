package com.xiaoyu.sample.library;

import android.content.Context;

import com.xiaoyu.sample.library.model.Cat;
import com.xiaoyu.sample.library.model.Dog;
import com.xiaoyu.sample.library.model.Person;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Copyright ©2016 by Teambition
 */

public class RealmFacade {

    private final RealmConfiguration realmConfig;
    private final Realm realm;
    private final RealmOut mOut;

    public RealmFacade(Context context, RealmOut out) {
        Realm.init(context);
        realmConfig = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfig);
        mOut = out;
    }

    public void basicCRUD() {
        mOut.showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations");

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName("Young Person");
                person.setAge(14);
            }
        });

        final Person person = realm.where(Person.class).findFirst();
        mOut.showStatus(person.getName() + ":" + person.getAge());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                person.setName("Senior Person");
                person.setAge(99);
                mOut.showStatus(person.getAge() + " got older: " + person.getAge());
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Person.class);
            }
        });
    }

    public void basicQuery() {
        mOut.showStatus("\n Performing basic Query operation...");
        mOut.showStatus("Number of persons: " + realm.where(Person.class).count());

        RealmResults<Person> results = realm.where(Person.class).equalTo("age", 99).findAll();

        mOut.showStatus("Size of result set: " + results.size());
    }

    public void basicLinkQuery() {
        mOut.showStatus("\n Performing basic Link Query operation...");
        mOut.showStatus("Number of persons: " + realm.where(Person.class).count());

        RealmResults<Person> results = realm.where(Person.class).equalTo("cats.name", "Tiger").findAll();
        mOut.showStatus("Size of result set: " + results.size());
    }

    public String complexQuery() {
        String status = "\n Performing complex Read/Write operation...";

        Realm realm = Realm.getInstance(realmConfig);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog fido = realm.createObject(Dog.class);
                fido.name = "fido";
                for (int i = 0; i < 10; i++) {
                    Person person = realm.createObject(Person.class);
                    person.setId(i);
                    person.setName("Person no. " + i);
                    person.setAge(i);
                    person.setDog(fido);

                    for (int j = 0; j < i; j++) {
                        Cat cat = realm.createObject(Cat.class);
                        cat.name = "Cat_" + j;
                        person.getCats().add(cat);
                    }
                }

            }
        });

        status += "\nNumber of persons: " + realm.where(Person.class).count();

        for (Person pers : realm.where(Person.class).findAll()) {
            String dogName;
            if (pers.getDog() == null) {
                dogName = "none";
            } else {
                dogName = pers.getDog().name;
            }

            status += "\n" + pers.getName() + ":" + pers.getAge() + " : " + dogName + " : " + pers.getCats().size();
        }

        RealmResults<Person> sortedPersons = realm.where(Person.class).findAllSorted("age", Sort.DESCENDING);
        status += "\nSorting " + sortedPersons.last().getName() + " == " + realm.where(Person.class).findFirst().getName();

        realm.close();
        return status;
    }

    public String complexReadWrite() {
        String status = "\n\nPerforming complex Query operation...";
        Realm realm = Realm.getInstance(realmConfig);
        status += "\nNumber of persons: " + realm.where(Person.class).count();

        RealmResults<Person> results = realm.where(Person.class)
            .between("age", 7, 9)
            .beginsWith("name", "Person").findAll();
        status += "\nSize of result set: " + results.size();

        realm.close();
        return status;
    }

    public void close() {
        realm.close();
    }
}
