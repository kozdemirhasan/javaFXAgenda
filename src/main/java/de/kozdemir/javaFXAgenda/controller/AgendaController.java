package de.kozdemir.javaFXAgenda.controller;

import java.net.URL;
import java.text.DateFormat.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import de.kozdemir.javaFXAgenda.model.Person;
import de.kozdemir.javaFXAgenda.model.PersonRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AgendaController implements Initializable {

	@FXML
	private TextField fieldVorname, fieldNachname, fieldGeburstDate, fieldEmail, fieldTel, fieldAdress, fieldPlz,
			fieldStadt;
	@FXML
	private DatePicker fieldGeburstdate;
	@FXML
	private TableView<Person> tablePersons;
	@FXML
	private TableColumn<Person, String> spalteVorname;
	@FXML
	private TableColumn<Person, String> spalteNachname;
	@FXML
	private TabPane tabPaneAgenda;
	@FXML
	private Tab tabPerson;
	@FXML
	private Tab tabGrafik;
	@FXML
	private BarChart<String, Integer> barGrafik;
	@FXML
	private CategoryAxis axisMonths;

	private ObservableList<Person> persons = FXCollections.observableArrayList();
	private ObservableList<String> monthsName = FXCollections.observableArrayList();
	
	private PersonRepository pr;

	public void geburtsGrafikErstellen(List<Person> persons) {

		barGrafik.getData().clear(); // erste grafik inhalt clear

		int[] monthCount = new int[12];
		for (Person p : persons) {
			int monthIndex = p.getGeburstdatum().getMonthValue() - 1;
			monthCount[monthIndex]++;
		}

		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		for (int i = 0; i < monthCount.length; i++) {
			series.getData().add(new XYChart.Data<>(monthsName.get(i), monthCount[i]));
		}

		barGrafik.getData().add(series); // auf dem grafik print

	}

	public void tablePersonsFuellen() {
		spalteVorname.setCellValueFactory(new PropertyValueFactory<Person, String>("vorname"));
		spalteNachname.setCellValueFactory(new PropertyValueFactory<Person, String>("nachname"));
		tablePersons.setItems(persons);
		tablePersons.refresh();
	}

	public void personAnzeige(Person person) {
		if (person != null) {
			fieldVorname.setText(person.getVorname());
			fieldNachname.setText(person.getNachname());
			fieldGeburstdate.setValue(person.getGeburstdatum());
			fieldEmail.setText(person.getEmail());
			fieldTel.setText(person.getTelefon());
			fieldAdress.setText(person.getAdress());
			fieldPlz.setText("" + person.getPlz());
			fieldStadt.setText(person.getStadt());

		} else {
			fieldVorname.setText("");
			fieldNachname.setText("");
			fieldGeburstdate.setValue(LocalDate.of(1900, 01, 01));
			fieldEmail.setText("");
			fieldTel.setText("");
			fieldAdress.setText("");
			fieldPlz.setText("");
			fieldStadt.setText("");
		}

	}

	@FXML
	private void btnNewAction() {
		fieldVorname.setText("");
		fieldNachname.setText("");
		fieldGeburstdate.setValue(LocalDate.of(1990, 01, 01));
		fieldEmail.setText("");
		fieldTel.setText("");
		fieldAdress.setText("");
		fieldPlz.setText("");
		fieldStadt.setText("");
	}

	@FXML
	private void btnNewPersonAdd() {
		try {			
			Person newPerson  =new Person(fieldVorname.getText(), fieldNachname.getText(), fieldGeburstdate.getValue(),
					fieldEmail.getText(), fieldTel.getText(), fieldAdress.getText(),
					Integer.parseInt(fieldPlz.getText()), fieldStadt.getText());
		
			pr.add(newPerson);
			persons.add(newPerson);
			
			tablePersonsFuellen();
			
		} catch (NumberFormatException
				ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("WARNUNG!!");
			alert.setHeaderText("Ungültige PLZ");
			alert.setContentText(alert.toString());
			alert.showAndWait();
		}
	}

	@FXML
	private void btnBearbeitenAction() {
		try {
			int index = tablePersons.getSelectionModel().getSelectedIndex();
//			System.out.println("Auswahl index: "+index);
			if (index != -1) {
				Person p = new Person();
				p.setVorname(fieldVorname.getText());
				p.setNachname(fieldNachname.getText());
				p.setGeburstdatum(fieldGeburstdate.getValue());
				p.setEmail(fieldEmail.getText());
				p.setTelefon(fieldTel.getText());
				p.setAdress(fieldAdress.getText());
				p.setPlz(Integer.parseInt(fieldPlz.getText()));
				p.setStadt(fieldStadt.getText());
							
				persons.get(index).setVorname(fieldVorname.getText());
				persons.get(index).setNachname(fieldNachname.getText());
				persons.get(index).setGeburstdatum(fieldGeburstdate.getValue());
				persons.get(index).setEmail(fieldEmail.getText());
				persons.get(index).setTelefon(fieldTel.getText());
				persons.get(index).setAdress(fieldAdress.getText());
				persons.get(index).setPlz(Integer.parseInt(fieldPlz.getText()));
				persons.get(index).setStadt(fieldStadt.getText());
				
				pr.update(index, p); //repository verändert

				tablePersonsFuellen();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("WARNUNG!!");
				alert.setHeaderText("Ungültige PLZ");
				alert.setContentText(alert.toString());
				alert.showAndWait();
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("WARNUNG!!");
			alert.setHeaderText("Ungültige PLZ");
			alert.setContentText(alert.toString());
			alert.showAndWait();
		}
	}

	@FXML
	private void btnLoeschenAction() {
		try {
			int index = tablePersons.getSelectionModel().getSelectedIndex();
			if (index != -1) {
				pr.delete(persons.get(index));
				persons.remove(index);
				tablePersonsFuellen();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("WARNUNG!!");
				alert.setHeaderText("Person wurde nicht auswählen!");
				alert.setContentText(alert.toString());
				alert.showAndWait();
			}

		} catch (Exception e) {

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(pr==null)
			pr = new PersonRepository();
		
//		pr.add(new Person("Hasan", "Demir", LocalDate.of(1982, 3, 22), "h.demir@gmail.com", "0564457644",
//				"Cumhuriyet mah.", 34786, "Istanbul"));
//
//		pr.add(new Person("Ali", "Kara", LocalDate.of(1983, 5, 14), "ali.kara@gmail.com", "05327864563",
//				"Yukari Mah. mah.", 57546, "Sinop"));
//		pr.add(new Person("Osman", "Ak", LocalDate.of(1982, 2, 12), "o.ak@gmail.com", "05437881232",
//				"Asagi mah mah.", 25, "Erzurum"));
//		pr.add(new Person("Ayse", "Telli", LocalDate.of(1982, 1, 27), "ayse.telli@gmail.com", "0555453627",
//				"Atatürk mah.", 42345, "Konya"));
//		pr.add(new Person("Nuri", "Can", LocalDate.of(1978, 3, 8), "n.can@gmail.com", "05789453627", "Köse mah.",
//				42345, "Konya"));
		
		persons.addAll(pr.getAll());

		tablePersonsFuellen();

		SingleSelectionModel<Tab> selectionModel = tabPaneAgenda.getSelectionModel();
		tablePersons.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			personAnzeige(newValue);
			selectionModel.select(tabPerson);
		} // select tabPerson and person details printed
		);

//		Grafik wirt erstellt
		String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AGU", "SEP", "OKT", "NOV", "DEZ" };
		monthsName.addAll(Arrays.asList(months));
		axisMonths.setCategories(monthsName);

		// listener
		tabPaneAgenda.getSelectionModel().selectedItemProperty().addListener(

				new ChangeListener<Tab>() {
					@Override
					public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
						if (newValue.equals(tabGrafik)) { // wenn mann tabGrafik geklickt hat
							geburtsGrafikErstellen(persons);
						}

					}
				});

	}

}
