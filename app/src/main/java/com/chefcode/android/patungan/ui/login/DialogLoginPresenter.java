package com.chefcode.android.patungan.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.chefcode.android.patungan.R;
import com.chefcode.android.patungan.firebase.model.User;
import com.chefcode.android.patungan.services.ServiceConfigs;
import com.chefcode.android.patungan.services.api.LoginService;
import com.chefcode.android.patungan.services.response.LoginResponse;
import com.chefcode.android.patungan.utils.Constants;
import com.chefcode.android.patungan.utils.MD5Utils;
import com.chefcode.android.patungan.utils.StringUtils;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DialogLoginPresenter {

    private LoginService service;
    private DialogLoginView view;
    private SharedPreferences preferences;
    private Context context;

    @Inject
    public DialogLoginPresenter(LoginService service, SharedPreferences preferences,
                                Context context) {
        this.service = service;
        this.preferences = preferences;
        this.context = context;
    }

    public void init(DialogLoginView view) {
        this.view = view;
    }

    public void loginMandiriEcash() {
        if (isPhoneNumberEmpty()) {
            view.phoneNumberError();
            return;
        }

        if (isPasswordEmpty()) {
            view.passwordError();
            return;
        }

        view.onLogin(true);
        view.setDialogCancelable(false);
        view.showLoginContainer(false);


        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Call<LoginResponse> call = service.login(view.getInputPhoneNumber(),
                androidId, view.getInputPassword());

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                // Success response (2xx code)
                if (response.isSuccess()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.status.toLowerCase().equals(ServiceConfigs.RESPONSE_VALID)) {

                        preferences.edit().putString(Constants.MANDIRI_TOKEN,
                                loginResponse.token).apply();
                        preferences.edit().putString(Constants.MSISDN,
                                loginResponse.msisdn).apply();

                        // Convert number to email
                        final String phoneMail = view.getInputPhoneNumber() + "@patungan.com";
                        final String password = view.getInputPassword();

                        // TODO must think about this chain request
                        final Firebase firebaseRef = new Firebase(Constants.FIREBASE_BASE_URL);
                        firebaseRef.createUser(phoneMail, password,
                                new Firebase.ValueResultHandler<Map<String, Object>>() {
                                    @Override
                                    public void onSuccess(Map<String, Object> stringObjectMap) {
                                        loginForFirebaseUser(phoneMail);
                                    }

                                    @Override
                                    public void onError(FirebaseError firebaseError) {
                                        handleFireBaseError(firebaseError, phoneMail);
                                    }
                                });

                    } else {
                        handleError(context.getString(R.string.error_auth_with_mandiri_ecash));
                    }
                } else {
                    // TODO handle error properly
                    handleError("Server error.");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                handleError(context.getString(R.string.error_message_failed_sign_in_no_network));
            }
        });
    }

    private void handleFireBaseError(FirebaseError firebaseError, String phoneMail) {
        switch (firebaseError.getCode()) {
            case FirebaseError.EMAIL_TAKEN:
                loginForFirebaseUser(phoneMail);
                break;
            case FirebaseError.NETWORK_ERROR:
                handleError(context.getString(R.string.error_message_failed_sign_in_no_network));
                break;
            default:
                handleError(firebaseError.getMessage());
                break;
        }
    }

    private void handleError(String message) {
        view.onLogin(false);
        view.showLoginContainer(true);
        view.setDialogCancelable(true);
        view.showToastError(message);
    }

    private void loginForFirebaseUser(final String phoneMail) {
        Firebase loginRef = new Firebase(Constants.FIREBASE_BASE_URL);
        loginRef.authWithPassword(phoneMail, view.getInputPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                if (authData != null) {
                    String encodedEmail = StringUtils.encodedEmail(phoneMail);
                    createUserWithFirebaseHelper(encodedEmail, phoneMail);

                    preferences.edit().putString(Constants.ENCODED_EMAIL, encodedEmail).apply();
                }
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                handleFireBaseError(firebaseError, null);
            }
        });
    }

    private void createUserWithFirebaseHelper(final String encodedEmail, final String phoneMail) {
        final Firebase userLocation = new Firebase(Constants.FIREBASE_USER_URL).child(encodedEmail);
        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    HashMap<String, Object> timestampJoin = new HashMap<>();
                    timestampJoin.put(Constants.FIREBASE_TIMESTAMP_PROPERTY, ServerValue.TIMESTAMP);

                    String generateProfilePict = String.format(Constants.DEFAULT_PROFILE_IMAGES,
                            MD5Utils.md5Hex(phoneMail));

                    preferences.edit().putString(Constants.AVATAR, generateProfilePict).apply();

                    User user = new User(encodedEmail,
                            view.getInputPhoneNumber(),
                            Constants.DEFAULT_ACCOUNT_BALANCE,
                            generateProfilePict,
                            timestampJoin);

                    userLocation.setValue(user);
                } else {
                    User user = dataSnapshot.getValue(User.class);
                    preferences.edit().putString(Constants.AVATAR, user.getProfilePict()).apply();
                }

                view.onLogin(false);
                view.dismissDialogLogin();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public boolean isPhoneNumberEmpty() {
        return view.getInputPhoneNumber().isEmpty() || view.getInputPhoneNumber().length() < 0;
    }

    public boolean isPasswordEmpty() {
        return view.getInputPassword().isEmpty() || view.getInputPassword().length() < 0;
    }
}
