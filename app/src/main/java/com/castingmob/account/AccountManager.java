package com.castingmob.account;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description : The Account Manager manages
               all the account related operation
 Date        : 14/01/16
 ==============================================
 */

import com.castingmob.account.model.Account;
import com.castingmob.account.model.PhotoItem;
import com.castingmob.account.model.Profile;
import com.castingmob.account.model.UserDetail;
import com.castingmob.logger.Logger;
import com.castingmob.network.NetworkManager;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class AccountManager {

    Logger logger = new Logger(AccountManager.class.getSimpleName());

    private static final AccountManager accountManager = new AccountManager();

    private AccountManager(){}

    public static AccountManager getInstance() {
        return accountManager;
    }

    /**
     * Create Account for Casting MOB
     * @param account
     * @param createAccountCallback
     */
    public void createAccount(Account account, Callback<Void> createAccountCallback){

        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        Call<Void> register = accountEndpointInterface.register(account.getEmail(),
                account.getType(),account.getSubType(),account.getWebsite());

        register.enqueue(createAccountCallback);
    }

    /**
     * Activate Account depending upon the activation code sent to your email
     * @param email
     * @param activationCode
     * @param profileCallback
     */
    public void activateAccount(String email, String activationCode, Callback<Profile> profileCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);
        Call<Profile> profileCall = accountEndpointInterface.activate(email, activationCode);
        profileCall.enqueue(profileCallback);
    }

    /**
     * Set the password for your email address
     * @param email
     * @param password
     * @param setPasswordCallback
     */
    public void setPassword(String email, String password, Callback<Void> setPasswordCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);
        Call<Void> passwordCall = accountEndpointInterface.setPassword(email, password);

        passwordCall.enqueue(setPasswordCallback);
    }

    /**
     * Login for your valid account
     * @param email
     * @param password
     * @param loginCallback
     */
    public void login(String email, String password, Callback<Profile> loginCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);
        Call<Profile> loginCall = accountEndpointInterface.login(email, password);

        loginCall.enqueue(loginCallback);
    }

    /**
     * Update the Profile
     * @param profile
     * @param createAccountCallback
     */
    public void updateAccount(Profile profile, Callback<Void> createAccountCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        Call<Void> profileUpdate = accountEndpointInterface.update(profile.getToken(),
                profile.getFirstName(), profile.getLastName(), profile.getDisplayName(),
                String.valueOf(profile.getGender()), profile.getDateOfBirth(), profile.getCity(), profile.getCountry(),
                profile.getEyeColor(), profile.getHairColor(), profile.getHeight(), profile.getWeight(),
                profile.getChest(), profile.getHip(), profile.getWaist(), profile.getEthnicity(),
                profile.getMin_hourrate(), profile.getMax_hourrate(), profile.getMin_halfdayrate(),
                profile.getMax_halfdayrate(), profile.getMin_fulldayrate(), profile.getMax_fulldayrate(),
                profile.getMax_travel(), String.valueOf(profile.getWill_travel()), String.valueOf(profile.getPublished()),
                String.valueOf(profile.getPromoted()));

        profileUpdate.enqueue(createAccountCallback);
    }

    /**
     * Upload cover photo
     * @param accessToken
     * @param filePath
     * @param uploadCallback
     */
    public void uploadCoverPhoto(String accessToken, String filePath, Callback<PhotoItem> uploadCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        File file = new File(filePath);

        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Call<PhotoItem> uploadCall
                = accountEndpointInterface.uploadPhoto(accessToken, "cover", requestBody);

        uploadCall.enqueue(uploadCallback);
    }

    /**
     * Upload Profile Photo
     * @param accessToken
     * @param filePath
     * @param uploadCallback
     */
    public void uploadProfilePhoto(String accessToken, String filePath, Callback<PhotoItem> uploadCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        File file = new File(filePath);

        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Call<PhotoItem> uploadCall
                = accountEndpointInterface.uploadPhoto(accessToken, "profile", requestBody);

        uploadCall.enqueue(uploadCallback);
    }

    /**
     * Upload Casting Photo
     * @param accessToken
     * @param filePath
     * @param uploadCallback
     */
    public void uploadCastingPhoto(String accessToken, String filePath, Callback<PhotoItem> uploadCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        File file = new File(filePath);

        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Call<PhotoItem> uploadCall
                = accountEndpointInterface.uploadPhoto(accessToken, "casting", requestBody);

        uploadCall.enqueue(uploadCallback);
    }

    /**
     * Upload polaroid photo
     * @param accessToken
     * @param filePath
     * @param uploadCallback
     */
    public void uploadPolaroidPhoto(String accessToken, String filePath, Callback<PhotoItem> uploadCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        File file = new File(filePath);

        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Call<PhotoItem> uploadCall
                = accountEndpointInterface.uploadPhoto(accessToken, "polaroid", requestBody);

        uploadCall.enqueue(uploadCallback);
    }

    /**
     * Get the Selected Feed Item Details
     * @param access_token
     * @param userDetailCallback
     */
    public void getSelectedUserDetail(String access_token,Callback<UserDetail> userDetailCallback ){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        Call<UserDetail> userDetailCall = accountEndpointInterface.getUserDetails(access_token);

        userDetailCall.enqueue(userDetailCallback);
    }

    public void getChatConversationProfilePic(String token,Callback<ResponseBody> responseBodyCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        Call<ResponseBody> userDetailCall = accountEndpointInterface.getChatProfilePic(token);

        userDetailCall.enqueue(responseBodyCallback);
    }

    /**
     * Block User
     * @param access_token
     * @param targetUserToken
     * @param responseBodyCallback
     */
    public void blockUser(String access_token,String targetUserToken,Callback<ResponseBody> responseBodyCallback){
        AccountEndpointInterface accountEndpointInterface
                = NetworkManager.createService(AccountEndpointInterface.class);

        Call<ResponseBody> blockUserCall = accountEndpointInterface.blockUser(access_token, targetUserToken);

        blockUserCall.enqueue(responseBodyCallback);
    }
}
