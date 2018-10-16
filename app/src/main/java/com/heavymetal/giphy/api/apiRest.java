package com.heavymetal.giphy.api;

import com.heavymetal.giphy.config.Config;
import com.heavymetal.giphy.entity.ApiResponse;
import com.heavymetal.giphy.entity.Category;
import com.heavymetal.giphy.entity.Comment;
import com.heavymetal.giphy.entity.Gif;
import com.heavymetal.giphy.entity.Language;
import com.heavymetal.giphy.entity.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by hsn on 28/09/2017.
 */

public interface apiRest {

    @GET("version/check/{code}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> check(@Path("code") Integer code);

    @FormUrlEncoded
    @POST("support/add/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> addSupport(@Field("email") String email, @Field("name") String name, @Field("message") String message);



    @GET("category/image/all/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Category>> categoriesImageAll();



    @GET("language/all/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Language>> languageAll();


    @GET("image/all/{page}/{order}/{language}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Gif>> imageAll(@Path("page") Integer page, @Path("order") String order, @Path("language") String language);

    @GET("image/by/category/{page}/{order}/{language}/{category}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Gif>> imageByCategory(@Path("page") Integer page, @Path("order") String order, @Path("language") String language, @Path("category") Integer category);

    @GET("image/by/random/{language}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Gif>> ImageByRandom(@Path("language") String language);



    @FormUrlEncoded
    @POST("image/add/copied/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageAddDownload(@Field("id") Integer id);


    @FormUrlEncoded
    @POST("image/add/like/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageAddLike(@Field("id") Integer id);


    @FormUrlEncoded
    @POST("image/add/love/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageAddLove(@Field("id") Integer id);


    @FormUrlEncoded
    @POST("image/add/angry/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageAddAngry(@Field("id") Integer id);


    @FormUrlEncoded
    @POST("image/add/haha/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageAddHaha(@Field("id") Integer id);


    @FormUrlEncoded
    @POST("image/add/sad/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageAddSad(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("image/add/woow/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageAddWoow(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("image/delete/like/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageDeleteLike(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("image/delete/love/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageDeleteLove(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("image/delete/angry/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageDeleteAngry(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("image/delete/haha/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageDeleteHaha(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("image/delete/sad/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageDeleteSad(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("image/delete/woow/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<Integer> imageDeleteWoow(@Field("id") Integer id);

    @Multipart
    @POST("image/upload/"+ Config.TOKEN_APP+"/")
    Call<ApiResponse> uploadImage(@Part MultipartBody.Part file, @Part("id") String id, @Part("key") String key, @Part("title") String title, @Part("language") String language, @Part("categories") String categories);

    @FormUrlEncoded
    @POST("user/token/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> editToken(@Field("user") Integer user, @Field("key") String key, @Field("token_f") String token_f);

    @FormUrlEncoded
    @POST("user/register/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> register(@Field("name") String name, @Field("username") String username, @Field("password") String password, @Field("type") String type, @Field("image") String image);

    @GET("device/{tkn}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> addDevice(@Path("tkn") String tkn);

    @GET("image/by/query/{order}/{language}/{page}/{query}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Gif>> searchImage(@Path("order") String order, @Path("language") String language, @Path("page") Integer page, @Path("query") String query);

    @GET("image/by/user/{page}/{order}/{language}/{user}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Gif>> userImage(@Path("order") String order, @Path("language") String language, @Path("user") Integer user, @Path("page") Integer page);

    @GET("image/by/follow/{page}/{language}/{user}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Gif>> followImage(@Path("page") Integer page, @Path("language") String language, @Path("user") Integer user);

    @GET("image/by/me/{page}/{user}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Gif>> meImage(@Path("page") Integer page, @Path("user") Integer user);

    @GET("comment/list/{id}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<Comment>> getComments(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("comment/add/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> addCommentImage(@Field("user") String user, @Field("id") Integer id, @Field("comment") String comment);

    @GET("user/follow/{user}/{follower}/{key}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> follow(@Path("user") Integer user, @Path("follower") Integer follower, @Path("key") String key);


    @GET("user/followers/{user}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<User>> getFollowers(@Path("user") Integer user);

    @GET("user/followings/{user}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<List<User>> getFollowing(@Path("user") Integer user);


    @GET("user/get/{user}/{me}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> getUser(@Path("user") Integer user, @Path("me") Integer me);

    @FormUrlEncoded
    @POST("user/edit/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> editUser(@Field("user") Integer user, @Field("key") String key, @Field("name") String name, @Field("email") String email, @Field("facebook") String facebook, @Field("twitter") String twitter, @Field("instagram") String instagram);


    @GET("install/add/{id}/"+ Config.TOKEN_APP+"/"+ Config.ITEM_PURCHASE_CODE+"/")
    Call<ApiResponse> addInstall(@Path("id") String id);


}
