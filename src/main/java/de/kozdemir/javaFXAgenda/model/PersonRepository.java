package de.kozdemir.javaFXAgenda.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonRepository {

	private static final String FILE_NAME = "data.ser";
	private List<Person> persons;

	public PersonRepository() {
		readFromFile();
	}

	public void add(Person product) {
		persons.add(product);
		saveToFile();
	}
	
	public void update(int index, Person person) {
		persons.remove(index);
		persons.add(index,person);
		
//		persons.get(index).setVorname(person.getVorname());
//		persons.get(index).setNachname(person.getNachname());
//		persons.get(index).setGeburstdatum(person.getGeburstdatum());
//		persons.get(index).setEmail(person.getEmail());
//		persons.get(index).setTelefon(person.getTelefon());
//		persons.get(index).setAdress(person.getAdress());
//		persons.get(index).setAdress(person.getAdress());
//		persons.get(index).setPlz(person.getPlz());
//		persons.get(index).setStadt(person.getStadt());		
				
		saveToFile();
	}

	public void delete(Person p) {
		persons.remove(p);
		saveToFile();
	}

	public List<Person> getAll() {
		return Collections.unmodifiableList(persons);

	}

	private void readFromFile() {
		try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(FILE_NAME)))) {
			persons = (List<Person>) in.readObject();
			// in wird automatisch geschlossen
		} catch (Exception e) {
			// ...oder eine neue leere Liste gebaut
			persons = new ArrayList<>();
		}
	}

	private void saveToFile() {
		// Einheitlichen Standard wie z.B. XML, JSON oder CSV verwenden

		try (ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(FILE_NAME)))) {

			out.writeObject(persons);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
