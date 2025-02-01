package com.examenopdracht.electroman.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.examenopdracht.electroman.data.database.ElectromanDatabase;
import com.examenopdracht.electroman.data.entity.User;
import com.examenopdracht.electroman.data.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RegisterViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final MutableLiveData<String> firstName = new MutableLiveData<>();
    private final MutableLiveData<String> lastName = new MutableLiveData<>();
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> birthdate = new MutableLiveData<>();
    private final MutableLiveData<String> birthdateString = new MutableLiveData<>();
    private final MutableLiveData<String> municipality = new MutableLiveData<>();
    private final MutableLiveData<String> postalCode = new MutableLiveData<>();
    private final MutableLiveData<String> street = new MutableLiveData<>();
    private final MutableLiveData<String> houseNumber = new MutableLiveData<>();
    private final MutableLiveData<String> box = new MutableLiveData<>();
    private final MutableLiveData<Boolean> termsAndConditionsAccepted = new MutableLiveData<>();


    private final MutableLiveData<Boolean> navigateBack = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showDatePicker = new MutableLiveData<>();

    // Getters
    public MutableLiveData<String> getFirstName() { return firstName; }
    public MutableLiveData<String> getLastName() { return lastName; }
    public MutableLiveData<String> getUsername() { return username; }
    public MutableLiveData<String> getPassword() { return password; }
    public MutableLiveData<LocalDate> getBirthdate() { return birthdate; }
    public MutableLiveData<String> getBirthdateString() { return birthdateString; }
    public MutableLiveData<String> getMunicipality() { return municipality; }
    public MutableLiveData<String> getPostalCode() { return postalCode; }
    public MutableLiveData<String> getStreet() { return street; }
    public MutableLiveData<String> getHouseNumber() { return houseNumber; }
    public MutableLiveData<String> getBox() { return box; }
    public MutableLiveData<Boolean> getTermsAndConditionsAccepted() { return termsAndConditionsAccepted; }

    // Getters for one-way observables
    public LiveData<Boolean> getNavigateBack() { return navigateBack; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getShowDatePicker() { return showDatePicker; }

    public RegisterViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void showDatePicker() {
        showDatePicker.setValue(true);
    }

    public void setDate(LocalDate date) {
        //TODO: Format date for db
        birthdate.setValue(date);
        birthdateString.setValue(dateFormatter.format(date));
        Log.d("Birthdate", birthdateString.toString());

    }

    public void onSaveClick() {
        if (validateInput()) {
            //TODO: save user to db
            User user = new User(
                    firstName.getValue(),
                    lastName.getValue(),
                    username.getValue(),
                    password.getValue(),
                    birthdate.getValue(),
                    municipality.getValue(),
                    postalCode.getValue(),
                    street.getValue(),
                    houseNumber.getValue(),
                    box.getValue()
            );

            Log.d("User", user.toString());
            userRepository.insertUser(user);
            navigateBack.setValue(true);
        }
    }
    private boolean validateInput() {
        if (isEmpty(firstName.getValue())) {
            errorMessage.setValue("First name is required");
            return false;
        }
        if (isEmpty(lastName.getValue())) {
            errorMessage.setValue("Last name is required");
            return false;
        }
        if (isEmpty(username.getValue())) {
            errorMessage.setValue("Username is required");
            return false;
        }
        if (isEmpty(password.getValue())) {
            errorMessage.setValue("Password is required");
            return false;
        }
        if (birthdate == null) {
            errorMessage.setValue("Birthdate is required");
            return false;
        }
        if (isEmpty(municipality.getValue())) {
            errorMessage.setValue("Municipality is required");
            return false;
        }
        if (isEmpty(postalCode.getValue())) {
            errorMessage.setValue("Postal code is required");
            return false;
        }
        if (isEmpty(street.getValue())) {
            errorMessage.setValue("Street is required");
            return false;
        }
        if (isEmpty(houseNumber.getValue())) {
            errorMessage.setValue("House number is required");
            return false;
        }
        return true;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public void clearError() {
        errorMessage.setValue(null);
    }
}
