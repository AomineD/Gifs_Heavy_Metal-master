package com.heavymetal.giphy.ui.fragement;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.heavymetal.giphy.adapter.CommentAdapter;
import com.heavymetal.giphy.adapter.GifAdapter;
import com.heavymetal.giphy.api.apiClient;
import com.heavymetal.giphy.api.apiRest;
import com.heavymetal.giphy.config.Config;
import com.heavymetal.giphy.entity.ApiResponse;
import com.heavymetal.giphy.entity.Category;
import com.heavymetal.giphy.entity.Comment;
import com.heavymetal.giphy.entity.Gif;
import com.heavymetal.giphy.entity.Language;
import com.heavymetal.giphy.manager.FavoritesStorage;
import com.heavymetal.giphy.manager.PrefManager;
import com.heavymetal.giphy.ui.GifActivity;
import com.heavymetal.giphy.ui.LoginActivity;
import com.heavymetal.giphy.ui.PermissionActivity;
import com.heavymetal.giphy.ui.UserActivity;
import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.squareup.picasso.Picasso;
import com.heavymetal.giphy.R;
import com.whygraphics.gifview.gif.GIFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class GifFragment extends Fragment {
    private int id;
    private String title;
    private String extension;
    private int userid;
    private String user;
    private String userimage;
    private String original;
    private String tags;
    private String image;
    private String thumbnail;
    private String path;
    private Boolean downloading =false;
    private int downloads;
    private boolean review;


    private int like;
    private int love;
    private int angry;
    private int haha;
    private int woow;
    private int sad;
    private boolean trusted;

    private String type;


    private CircleImageView circle_image_view_fragement_image_user;
    private TextView text_view_fragement_image_title;
    private TextView text_view_fragement_image_name_user;
    private String created;
    private LikeButton like_button_whatsapp_fragement_image;
    private LikeButton like_button_messenger_fragement_image;
    private LikeButton like_button_twitter_fragement_image;
    private LikeButton like_button_snapshat_fragement_image;
    private LikeButton like_button_hike_fragement_image;
    private LikeButton like_button_fav_fragement_image;
    private LikeButton like_button_copy_fragement_image;
    private LikeButton like_botton_share_fragement_image;


    private static final String COPY_ID="com.android.copy";

    private static final String WHATSAPP_ID="com.whatsapp";
    private static final String FACEBOOK_ID="com.facebook.katana";
    private static final String MESSENGER_ID="com.facebook.orca";
    private static final String INSTAGRAM_ID="com.instagram.android";
    private static final String SHARE_ID="com.android.all";
    private static final String DOWNLOAD_ID="com.android.download";
    private static final String TWITTER_ID="com.twitter.android";
    private static final String SNAPSHAT_ID="com.snapchat.android";
    private static final String HIKE_ID="com.bsb.hike";


    private PrefManager prefManager;
    private String language = "0";
    private Object from;
    private InterstitialAd mInterstitialAdDownload;
    private int open_action;


    private TextView text_view_wallpaper_comments_count;
    private RelativeLayout relative_layout_comment_section;
    private EditText edit_text_comment_add;
    private ProgressBar progress_bar_comment_add;
    private ProgressBar progress_bar_comment_list;
    private ImageView image_button_comment_add;
    private RecyclerView recycle_view_comment;
    private ImageView imageView_empty_comment;
    private int comments  = 0;
    private LikeButton like_button_comments_wallpaper;
    private CardView card_view_wallpaper_comments;
    private boolean comment;


    private ArrayList<Comment> commentList= new ArrayList<>();
    private CommentAdapter commentAdapter;
    private LinearLayoutManager linearLayoutManagerCOmment;
    private RelativeLayout relative_layout_dialog_top;
    private Button button_follow_user_activity;
    private RelativeLayout relative_layout_main;
    private ScrollView scroll_view_main;
    private CardView card_view_fragement_image;
    private CardView card_view_fragement_image_share;
    private View view;
    private TextView text_view_comment_box_count;
    private ImageView image_view_comment_box_close;
    private TextView text_view_sad_fragement_image;
    private TextView text_view_angry_fragement_image;
    private TextView text_view_haha_fragement_image;
    private TextView text_view_love_fragement_image;
    private TextView text_view_like_fragement_image;
    private TextView text_view_woow_fragement_image;
    private LikeButton like_button_sad_fragement_image;
    private LikeButton like_button_angry_fragement_image;
    private LikeButton like_button_woow_fragement_image;
    private LikeButton like_button_like_fragement_image;
    private LikeButton like_button_haha_fragement_image;
    private LikeButton like_button_love_fragement_image;

    private RelativeLayout relative_layout_progress_fragement_image;
    private TextView text_view_progress_fragement_image;
    private ProgressBar progress_bar_fragement_image;
    private LikeButton like_button_instagram_fragement_image;
    private LikeButton like_button_download_fragement_image;
    private LikeButton like_button_facebook_fragement_image;
    private LinearLayout linear_layout_reactions_loading;
    private ImageView image_view_image_fragment_image;
    private int strtext;
    private GIFView gif_view_fragment_image;
    private RelativeLayout relativeLayout_progress_fragment_image;


    private List<Gif> gifList =new ArrayList<>();
    private List<Category> categoryList =new ArrayList<>();
    private GifAdapter gifAdapter;
    private PeekAndPop peekAndPop;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view_status_load_more;
    private ImageView image_view_user_iten_trusted;

    public GifFragment() {
        // Required empty public constructor
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            if (from!=null){
              //  addCopied(id);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        checkPermission();
        loadLang();

        this.view =  inflater.inflate(R.layout.fragment_gif, container, false);
        Bundle bundle =  this.getArguments();


        this.from =  bundle.getString("from");


        this.id = bundle.getInt("id");
        this.title = bundle.getString("title");
        this.thumbnail = bundle.getString("thumbnail");
        this.userid = bundle.getInt("userid");
        this.user = bundle.getString("user");
        this.userimage = bundle.getString("userimage");
        this.type = bundle.getString("type");
        this.original = bundle.getString("original");
        this.image = bundle.getString("image");
        this.extension = bundle.getString("extension");
        this.trusted = bundle.getBoolean("trusted");
        this.comment = bundle.getBoolean("comment");
        this.downloads = bundle.getInt("downloads");
        this.tags = bundle.getString("tags");
        this.review = bundle.getBoolean("review");
        this.comments = bundle.getInt("comments");
        this.created = bundle.getString("created");

        this.woow = bundle.getInt("woow");
        this.like = bundle.getInt("like");
        this.love = bundle.getInt("love");
        this.angry = bundle.getInt("angry");
        this.sad = bundle.getInt("sad");
        this.haha = bundle.getInt("haha");

/*


        Toast.makeText(getActivity(), "Fragement"+id, Toast.LENGTH_SHORT).show();*/


        this.prefManager= new PrefManager(getActivity().getApplicationContext());
        this.language=prefManager.getString("LANGUAGE_DEFAULT");




        initView();
       initAction();
        initInterstitialAdPrepare();
        initStatus();
        getUser();
        setReaction(prefManager.getString("reaction_"+id));

        if (Config.REALTED_VIDEO_BOTTOM){
            loadMore();
        }
        initAds();
        return view;
    }


    private void initStatus() {


        Picasso.with(getActivity().getApplicationContext()).load(original).error(R.drawable.bg_transparant).placeholder(R.drawable.bg_transparant).into(this.image_view_image_fragment_image);
        Picasso.with(getActivity().getApplicationContext()).load(userimage).error(R.drawable.profile).placeholder(R.drawable.profile).into(this.circle_image_view_fragement_image_user);



        gif_view_fragment_image.setGifResource("url:"+original);
        relativeLayout_progress_fragment_image.setVisibility(View.VISIBLE);
        gif_view_fragment_image.setVisibility(View.GONE);
        gif_view_fragment_image.setOnSettingGifListener(new GIFView.OnSettingGifListener() {
            @Override
            public void onSuccess(GIFView view, Exception e) {
                relativeLayout_progress_fragment_image.setVisibility(View.GONE);
                gif_view_fragment_image.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(GIFView view, Exception e) {

            }
        });

        this.text_view_fragement_image_title.setText(title);
        this.text_view_fragement_image_name_user.setText(user);
        this.text_view_wallpaper_comments_count.setText(comments+"");
        if (this.comment){
            relative_layout_comment_section.setVisibility(View.VISIBLE);
        }else{
            relative_layout_comment_section.setVisibility(View.GONE);
        }
        if (this.comment){
            relative_layout_comment_section.setVisibility(View.VISIBLE);
        }else{
            relative_layout_comment_section.setVisibility(View.GONE);
        }


        this.text_view_wallpaper_comments_count.setText(comments+"");
        this.text_view_comment_box_count.setText(comments+" "+getActivity().getResources().getString(R.string.comments));
        if (this.trusted){
            image_view_user_iten_trusted.setVisibility(View.VISIBLE);
        }else{
            image_view_user_iten_trusted.setVisibility(View.GONE);
        }

        this.text_view_like_fragement_image.setText(format(like));
        this.text_view_love_fragement_image.setText(format(love));
        this.text_view_angry_fragement_image.setText(format(angry));
        this.text_view_haha_fragement_image.setText(format(haha));
        this.text_view_woow_fragement_image.setText(format(woow));
        this.text_view_sad_fragement_image.setText(format(sad));



    }

    private void initView() {
        this.recycler_view_status_load_more=(RecyclerView) view.findViewById(R.id.recycler_view_status_load_more);
        this.peekAndPop = new PeekAndPop.Builder(getActivity())
                .parentViewGroupToDisallowTouchEvents(recycler_view_status_load_more)
                .peekLayout(R.layout.dialog_view)
                .build();
        this.linearLayoutManager=  new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        gifAdapter =new GifAdapter(gifList,categoryList,getActivity(),peekAndPop);
        recycler_view_status_load_more.setHasFixedSize(true);
        recycler_view_status_load_more.setAdapter(gifAdapter);
        recycler_view_status_load_more.setLayoutManager(linearLayoutManager);

        this.image_view_user_iten_trusted=(ImageView) view.findViewById(R.id.image_view_user_iten_trusted);
        this.relativeLayout_progress_fragment_image =(RelativeLayout) view.findViewById(R.id.relativeLayout_progress_fragment_image);
        this.gif_view_fragment_image =(GIFView) view.findViewById(R.id.gif_view_fragment_image);
        this.image_view_image_fragment_image=(ImageView) view.findViewById(R.id.image_view_image_fragment_image);
        this.linear_layout_reactions_loading=(LinearLayout) view.findViewById(R.id.linear_layout_reactions_loading);
        this.like_button_download_fragement_image=(LikeButton) view.findViewById(R.id.like_button_download_fragement_image);
        this.like_button_facebook_fragement_image=(LikeButton) view.findViewById(R.id.like_button_facebook_fragement_image);
        this.like_button_instagram_fragement_image=(LikeButton) view.findViewById(R.id.like_button_instagram_fragement_image);
        this.progress_bar_fragement_image=(ProgressBar) view.findViewById(R.id.progress_bar_fragement_image);
        this.text_view_progress_fragement_image=(TextView) view.findViewById(R.id.text_view_progress_fragement_image);
        this.relative_layout_progress_fragement_image=(RelativeLayout) view.findViewById(R.id.relative_layout_progress_fragement_image);
        this.like_button_angry_fragement_image=(LikeButton) view.findViewById(R.id.like_button_angry_fragement_image);
        this.like_button_like_fragement_image=(LikeButton) view.findViewById(R.id.like_button_like_fragement_image);
        this.like_button_love_fragement_image=(LikeButton) view.findViewById(R.id.like_button_love_fragement_image);
        this.like_button_sad_fragement_image=(LikeButton) view.findViewById(R.id.like_button_sad_fragement_image);
        this.like_button_woow_fragement_image=(LikeButton) view.findViewById(R.id.like_button_woow_fragement_image);
        this.like_button_haha_fragement_image=(LikeButton) view.findViewById(R.id.like_button_haha_fragement_image);

        this.text_view_sad_fragement_image=(TextView) view.findViewById(R.id.text_view_sad_fragement_image);
        this.text_view_angry_fragement_image=(TextView) view.findViewById(R.id.text_view_angry_fragement_image);
        this.text_view_haha_fragement_image=(TextView) view.findViewById(R.id.text_view_haha_fragement_image);
        this.text_view_love_fragement_image=(TextView) view.findViewById(R.id.text_view_love_fragement_image);
        this.text_view_like_fragement_image=(TextView) view.findViewById(R.id.text_view_like_fragement_image);
        this.text_view_woow_fragement_image=(TextView) view.findViewById(R.id.text_view_woow_fragement_image);
        this.image_view_comment_box_close=(ImageView) view.findViewById(R.id.image_view_comment_box_close);
        this.text_view_comment_box_count=(TextView) view.findViewById(R.id.text_view_comment_box_count);
        this.scroll_view_main=(ScrollView) view.findViewById(R.id.scroll_view_main);
        this.relative_layout_main=(RelativeLayout) view.findViewById(R.id.relative_layout_main);
        this.like_button_whatsapp_fragement_image=(LikeButton) view.findViewById(R.id.like_button_whatsapp_fragement_image);
        this.like_button_messenger_fragement_image=(LikeButton) view.findViewById(R.id.like_button_messenger_fragement_image);
        this.like_button_twitter_fragement_image=(LikeButton) view.findViewById(R.id.like_button_twitter_fragement_image);
        this.like_button_snapshat_fragement_image=(LikeButton) view.findViewById(R.id.like_button_snapshat_fragement_image);
        this.like_button_hike_fragement_image=(LikeButton) view.findViewById(R.id.like_button_hike_fragement_image);

        this.like_button_fav_fragement_image=(LikeButton) view.findViewById(R.id.like_button_fav_fragement_image);
        this.like_botton_share_fragement_image=(LikeButton) view.findViewById(R.id.like_botton_share_fragement_image);

        this.text_view_fragement_image_name_user=(TextView) view.findViewById(R.id.text_view_fragement_image_name_user);
        this.text_view_fragement_image_title=(TextView) view.findViewById(R.id.text_view_fragement_image_title);
        this.circle_image_view_fragement_image_user=(CircleImageView) view.findViewById(R.id.circle_image_view_fragement_image_user);





        this.linearLayoutManagerCOmment=  new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        this.text_view_wallpaper_comments_count=(TextView) view.findViewById(R.id.text_view_wallpaper_comments_count);
        this.relative_layout_comment_section=(RelativeLayout) view.findViewById(R.id.relative_layout_comment_section);
        this.edit_text_comment_add=(EditText) view.findViewById(R.id.edit_text_comment_add);
        this.progress_bar_comment_add=(ProgressBar) view.findViewById(R.id.progress_bar_comment_add);
        this.progress_bar_comment_list=(ProgressBar) view.findViewById(R.id.progress_bar_comment_list);
        this.image_button_comment_add=(ImageView) view.findViewById(R.id.image_button_comment_add);
        this.recycle_view_comment=(RecyclerView) view.findViewById(R.id.recycle_view_comment);
        this.commentAdapter = new CommentAdapter(commentList, getActivity().getApplication());
        this.recycle_view_comment.setHasFixedSize(true);
        this.recycle_view_comment.setAdapter(commentAdapter);
        this.recycle_view_comment.setLayoutManager(linearLayoutManagerCOmment);
        this.imageView_empty_comment=(ImageView) view.findViewById(R.id.imageView_empty_comment);
        this.like_button_comments_wallpaper=(LikeButton) view.findViewById(R.id.like_botton_comment_activity_gif);
        this.card_view_wallpaper_comments=(CardView) view.findViewById(R.id.card_view_wallpaper_comments);
        image_button_comment_add.setEnabled(false);


        this.button_follow_user_activity=(Button) view.findViewById(R.id.button_follow_user_activity);
        this.relative_layout_dialog_top=(RelativeLayout) view.findViewById(R.id.relative_layout_dialog_top);


        final FavoritesStorage storageFavorites= new FavoritesStorage(getActivity().getApplicationContext());

        List<Gif> favorites_list = storageFavorites.loadImagesFavorites();
        Boolean exist = false;
        if (favorites_list==null){
            favorites_list= new ArrayList<>();
        }
        for (int i = 0; i <favorites_list.size() ; i++) {
            if (favorites_list.get(i).getId().equals(id)){
                exist = true;
            }
        }
        if (exist  == false) {
            like_button_fav_fragement_image.setLiked(false);
        }else{
            like_button_fav_fragement_image.setLiked(true);
        }

    }

    private void initAction() {
        this.like_button_like_fragement_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addLike(id);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteLike(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
            }
        });

        this.like_button_love_fragement_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addLove(id);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteLove(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
            }
        });
        this.like_button_woow_fragement_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addWoow(id);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteWoow(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
            }
        });

        this.like_button_angry_fragement_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addAngry(id);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteAngry(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
            }
        });
        this.like_button_sad_fragement_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addSad(id);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteSad(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
            }
        });
        this.like_button_haha_fragement_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addHaha(id);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteHaha(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
            }
        });
        this.image_view_comment_box_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentBox();
            }
        });
        this.button_follow_user_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow();
            }
        });
        this.relative_layout_dialog_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UserActivity.class);
                intent.putExtra("id",userid);
                intent.putExtra("name",user);
                intent.putExtra("image",userimage);
                startActivity(intent);
            }
        });
        this.like_button_whatsapp_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_whatsapp_fragement_image.setLiked(false);


                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5001;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                            else
                                new DownloadFileFromURL().execute(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                        else
                            new DownloadFileFromURL().execute(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                    }
                }

            }
        });
        this.like_button_whatsapp_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_whatsapp_fragement_image.setLiked(false);


                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5001;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                            else
                                new DownloadFileFromURL().execute(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                        else
                            new DownloadFileFromURL().execute(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                    }
                }
            }
        });
        this.like_button_messenger_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_messenger_fragement_image.setLiked(false);





                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5002;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,MESSENGER_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,MESSENGER_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,MESSENGER_ID);
                        else
                            new DownloadFileFromURL().execute(original, title, extension, 0,MESSENGER_ID);
                    }
                }
            }
        });
        this.like_button_facebook_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_facebook_fragement_image.setLiked(false);


                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5003;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,FACEBOOK_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,FACEBOOK_ID);
                        }

                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,FACEBOOK_ID);
                        else
                            new DownloadFileFromURL().execute(original, title, extension, 0,FACEBOOK_ID);
                    }

                }
            }
        });
        this.like_button_instagram_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_instagram_fragement_image.setLiked(false);



                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5004;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,INSTAGRAM_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,INSTAGRAM_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,INSTAGRAM_ID);
                        else
                            new DownloadFileFromURL().execute(original, title, extension, 0,INSTAGRAM_ID);
                    }
                }
            }
        });
        this.like_button_twitter_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_twitter_fragement_image.setLiked(false);



                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5005;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,TWITTER_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,TWITTER_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,TWITTER_ID);
                        else
                            new DownloadFileFromURL().execute(original, title, extension, 0,TWITTER_ID);
                    }
                }
            }
        });
        this.like_botton_share_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_botton_share_fragement_image.setLiked(false);



                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5006;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,SHARE_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,SHARE_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,SHARE_ID);
                        else
                            new DownloadFileFromURL().execute(original, title, extension, 0,SHARE_ID);
                    }
                }
            }
        });
        this.like_button_download_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_download_fragement_image.setLiked(false);




                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5007;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,DOWNLOAD_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,DOWNLOAD_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,DOWNLOAD_ID);
                        else
                            new DownloadFileFromURL().execute(original, title, extension, 0,DOWNLOAD_ID);
                    }
                }
            }
        });

        this.like_button_hike_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_hike_fragement_image.setLiked(false);




                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5008;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,HIKE_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,HIKE_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,HIKE_ID);
                        else
                            new DownloadFileFromURL().execute(original, title, extension, 0,HIKE_ID);
                    }
                }
            }
        });
        this.like_button_snapshat_fragement_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_snapshat_fragement_image.setLiked(false);



                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5009;
                    } else {
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,SNAPSHAT_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,SNAPSHAT_ID);
                        }
                    }
                }else{
                    if (!downloading) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,SNAPSHAT_ID);
                        else
                            new DownloadFileFromURL().execute(original, title, extension, 0,SNAPSHAT_ID);
                    }
                }
            }
        });
        this.like_button_fav_fragement_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5010;
                    } else {
                        favorite();
                    }
                }else{
                    favorite();
                }
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                if (mInterstitialAdDownload.isLoaded()) {
                    if (check()) {
                        mInterstitialAdDownload.show();
                        open_action = 5010;
                    } else {
                        favorite();
                    }
                }else{
                    favorite();
                }
            }
        });


        this.image_button_comment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });

        this.edit_text_comment_add.addTextChangedListener(new CommentTextWatcher(this.edit_text_comment_add));



        this.like_button_comments_wallpaper.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                like_button_comments_wallpaper.setLiked(false);
                showCommentBox();
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                like_button_comments_wallpaper.setLiked(false);
                showCommentBox();
            }
        });

    }
    private class CommentTextWatcher implements TextWatcher {
        private View view;
        private CommentTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edit_text_comment_add:
                    ValidateComment();
                    break;
            }
        }
    }

    private boolean ValidateComment() {
        String email = edit_text_comment_add.getText().toString().trim();
        if (email.isEmpty()) {
            image_button_comment_add.setEnabled(false);
            return false;
        }else{
            image_button_comment_add.setEnabled(true);
        }
        return true;
    }
    public void getComments(){
        progress_bar_comment_list.setVisibility(View.VISIBLE);
        recycle_view_comment.setVisibility(View.GONE);
        imageView_empty_comment.setVisibility(View.GONE);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Comment>> call = service.getComments(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()) {
                    commentList.clear();
                    comments = response.body().size();
                    text_view_wallpaper_comments_count.setText(comments+"");
                    text_view_comment_box_count.setText(comments+" "+getActivity().getResources().getString(R.string.comments));
                    if (response.body().size() != 0) {
                        for (int i = 0; i < response.body().size(); i++) {
                            commentList.add(response.body().get(i));
                        }
                        commentAdapter.notifyDataSetChanged();

                        progress_bar_comment_list.setVisibility(View.GONE);
                        recycle_view_comment.setVisibility(View.VISIBLE);
                        imageView_empty_comment.setVisibility(View.GONE);


                    } else {
                        progress_bar_comment_list.setVisibility(View.GONE);
                        recycle_view_comment.setVisibility(View.GONE);
                        imageView_empty_comment.setVisibility(View.VISIBLE);

                    }
                }else{

                }
                recycle_view_comment.setNestedScrollingEnabled(false);

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {


            }
        });
    }
    public void showCommentBox(){
        getComments();
        if (card_view_wallpaper_comments.getVisibility() == View.VISIBLE)
        {
            Animation c= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                    R.anim.slide_down);
            c.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    card_view_wallpaper_comments.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            card_view_wallpaper_comments.startAnimation(c);


        }else{
            Animation c= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                    R.anim.slide_up);
            c.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    card_view_wallpaper_comments.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            card_view_wallpaper_comments.startAnimation(c);

        }

    }

    public void favorite(){
        final FavoritesStorage storageFavorites= new FavoritesStorage(getActivity().getApplicationContext());

        List<Gif> favorites_list = storageFavorites.loadImagesFavorites();
        Boolean exist = false;
        if (favorites_list==null){
            favorites_list= new ArrayList<>();
        }
        for (int i = 0; i <favorites_list.size() ; i++) {
            if (favorites_list.get(i).getId().equals(id)){
                exist = true;
            }
        }
        if (exist  == false) {
            ArrayList<Gif> audios= new ArrayList<Gif>();
            for (int i = 0; i < favorites_list.size(); i++) {
                audios.add(favorites_list.get(i));
            }




            Gif fragement_gif_joke = new Gif();
            fragement_gif_joke.setId(id);
            fragement_gif_joke.setTitle(title);
            fragement_gif_joke.setDownloads(downloads);
            fragement_gif_joke.setThumbnail(thumbnail);
            fragement_gif_joke.setOriginal(original);
            fragement_gif_joke.setImage(image);
            fragement_gif_joke.setUserid(userid);
            fragement_gif_joke.setTags(tags);
            fragement_gif_joke.setReview(review);
            fragement_gif_joke.setUserimage(userimage);
            fragement_gif_joke.setComments(comments);
            fragement_gif_joke.setTrusted(trusted);
            fragement_gif_joke.setComment(comment);
            fragement_gif_joke.setCreated(created);
            fragement_gif_joke.setDownloads(downloads);

            fragement_gif_joke.setLike(like);
            fragement_gif_joke.setLove(love);
            fragement_gif_joke.setAngry(angry);
            fragement_gif_joke.setSad(sad);
            fragement_gif_joke.setHaha(haha);
            fragement_gif_joke.setWoow(woow);



            audios.add(fragement_gif_joke);
            storageFavorites.storeImage(audios);
        }else{
            ArrayList<Gif> new_favorites= new ArrayList<Gif>();
            for (int i = 0; i < favorites_list.size(); i++) {
                if (!favorites_list.get(i).getId().equals(id)){
                    new_favorites.add(favorites_list.get(i));

                }
            }
            storageFavorites.storeImage(new_favorites);
        }
    }


    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),   Manifest.permission.READ_CONTACTS)) {
                    Intent intent_image  =  new Intent(getActivity().getApplicationContext(), PermissionActivity.class);
                    startActivity(intent_image);
                    getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                    getActivity().finish();
                } else {
                    Intent intent_image  =  new Intent(getActivity().getApplicationContext(), PermissionActivity.class);
                    startActivity(intent_image);
                    getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                    getActivity().finish();
                }
            }

        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAdDownload.loadAd(adRequest);
    }

    private void initInterstitialAdPrepare() {
        mInterstitialAdDownload = new InterstitialAd(getActivity());
        mInterstitialAdDownload.setAdUnitId(getResources().getString(R.string.ad_unit_id_interstitial));

        mInterstitialAdDownload.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                switch (open_action){

                    case 5001:{
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                            else
                                new DownloadFileFromURL().execute(AsyncTask.THREAD_POOL_EXECUTOR,original, title,extension,0,WHATSAPP_ID);
                        }
                        break;
                    }
                    case 5002:{
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,MESSENGER_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,MESSENGER_ID);
                        }
                        break;
                    }
                    case 5003:{
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,FACEBOOK_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,FACEBOOK_ID);
                        }
                        break;
                    }
                    case 5004:{

                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,INSTAGRAM_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,INSTAGRAM_ID);
                        }
                        break;
                    }
                    case 5005:{
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,TWITTER_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,TWITTER_ID);
                        }
                        break;
                    }
                    case 5006:{
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,SHARE_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,SHARE_ID);
                        }
                        break;
                    }
                    case 5007:{
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,DOWNLOAD_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,DOWNLOAD_ID);
                        }
                        break;
                    }
                    case 5008:{
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,HIKE_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,HIKE_ID);
                        }
                        break;
                    }
                    case 5009:{
                        if (!downloading) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                                new DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,original, title, extension, 0,SNAPSHAT_ID);
                            else
                                new DownloadFileFromURL().execute(original, title, extension, 0,SNAPSHAT_ID);
                        }
                        break;
                    }
                    case 5010:{
                        favorite();
                        break;
                    }

                }

                requestNewInterstitial();


            }
        });

        requestNewInterstitial();
    }

    public boolean check(){
        PrefManager prf = new PrefManager(getActivity().getApplicationContext());
        if (!prf.getString("SUBSCRIBED").equals("FALSE")) {
            return false;
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        if (prf.getString("LAST_DATE_ADS").equals("")) {
            prf.setString("LAST_DATE_ADS", strDate);
        } else {
            String toyBornTime = prf.getString("LAST_DATE_ADS");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date oldDate = dateFormat.parse(toyBornTime);
                System.out.println(oldDate);

                Date currentDate = new Date();

                long diff = currentDate.getTime() - oldDate.getTime();
                long seconds = diff / 1000;

                if (seconds > Integer.parseInt(getResources().getString(R.string.AD_MOB_TIME))) {
                    prf.setString("LAST_DATE_ADS", strDate);
                    return  true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return  false;
    }
    public void loadLang(){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Language>> call = service.languageAll();
        call.enqueue(new Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, final Response<List<Language>> response) {

            }
            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
            }
        });
    }
    public void addComment(){


        PrefManager prf= new PrefManager(getActivity().getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")){

            byte[] data = new byte[0];
            String comment_final ="";
            try {
                data = edit_text_comment_add.getText().toString().getBytes("UTF-8");
                comment_final = Base64.encodeToString(data, Base64.DEFAULT);

            } catch (UnsupportedEncodingException e) {
                comment_final = edit_text_comment_add.getText().toString();
                e.printStackTrace();
            }
            progress_bar_comment_add.setVisibility(View.VISIBLE);
            image_button_comment_add.setVisibility(View.GONE);
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.addCommentImage(prf.getString("ID_USER").toString(),id,comment_final);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode()==200){
                            comments++ ;
                            text_view_wallpaper_comments_count.setText(comments +"");
                            text_view_comment_box_count.setText(text_view_wallpaper_comments_count.getText()+" "+getActivity().getResources().getString(R.string.comments));
                            recycle_view_comment.setVisibility(View.VISIBLE);
                            imageView_empty_comment.setVisibility(View.GONE);
                            Toasty.success(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            edit_text_comment_add.setText("");
                            String id="";
                            String content="";
                            String user="";
                            String image="";
                            Boolean trusted=false;

                            for (int i=0;i<response.body().getValues().size();i++){
                                if (response.body().getValues().get(i).getName().equals("id")){
                                    id=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("content")){
                                    content=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("user")){
                                    user=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("trusted")){
                                    if (response.body().getValues().get(i).getValue().equals("true")){
                                        trusted=true;
                                    }
                                }
                                if (response.body().getValues().get(i).getName().equals("image")){
                                    image=response.body().getValues().get(i).getValue();
                                }
                            }
                            Comment comment= new Comment();
                            comment.setId(Integer.parseInt(id));
                            comment.setUser(user);
                            comment.setTrusted(trusted);
                            comment.setContent(content);
                            comment.setImage(image);
                            comment.setEnabled(true);
                            comment.setCreated(getResources().getString(R.string.now_time));
                            commentList.add(comment);
                            commentAdapter.notifyDataSetChanged();

                        }else{
                            Toasty.error(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    recycle_view_comment.scrollToPosition(recycle_view_comment.getAdapter().getItemCount()-1);
                    recycle_view_comment.scrollToPosition(recycle_view_comment.getAdapter().getItemCount()-1);
                    commentAdapter.notifyDataSetChanged();
                    progress_bar_comment_add.setVisibility(View.GONE);
                    image_button_comment_add.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    progress_bar_comment_add.setVisibility(View.VISIBLE);
                    image_button_comment_add.setVisibility(View.GONE);
                }
            });
        }else{
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }

    }
    public void follow(){

        PrefManager prf= new PrefManager(getActivity().getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            button_follow_user_activity.setText(getResources().getString(R.string.loading));
            button_follow_user_activity.setEnabled(false);
            String follower = prf.getString("ID_USER");
            String key = prf.getString("TOKEN_USER");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.follow(userid, Integer.parseInt(follower), key);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode().equals(200)){
                            button_follow_user_activity.setText("UnFollow");
                        }else if (response.body().getCode().equals(202)) {
                            button_follow_user_activity.setText("Follow");

                        }
                    }
                    button_follow_user_activity.setEnabled(true);

                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    button_follow_user_activity.setEnabled(true);
                }
            });
        }else{
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }
    }
    private void getUser() {
        PrefManager prf= new PrefManager(getActivity().getApplicationContext());
        Integer follower= -1;
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            button_follow_user_activity.setEnabled(false);
            follower = Integer.parseInt(prf.getString("ID_USER"));
        }
        if (follower!=userid){
            button_follow_user_activity.setVisibility(View.VISIBLE);
        }
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.getUser(userid,follower);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){

                    for (int i=0;i<response.body().getValues().size();i++){

                        if (response.body().getValues().get(i).getName().equals("follow")){
                            if (response.body().getValues().get(i).getValue().equals("true"))
                                button_follow_user_activity.setText("UnFollow");
                            else
                                button_follow_user_activity.setText("Follow");
                        }
                    }

                }else{


                }
                button_follow_user_activity.setEnabled(true);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                button_follow_user_activity.setEnabled(true);
            }
        });
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }
    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public void addLike(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddLike(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    like=response.body();
                    text_view_like_fragement_image.setText(format(like));
                    setReaction("like");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addLove(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddLove(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    love=response.body();
                    text_view_love_fragement_image.setText(format(love));
                    setReaction("love");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addSad(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddSad(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    sad=response.body();
                    text_view_sad_fragement_image.setText(format(sad));
                    setReaction("sad");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addAngry(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddAngry(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    angry=response.body();
                    text_view_angry_fragement_image.setText(format(angry));
                    setReaction("angry");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addHaha(Integer id){
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        removeReaction(prefManager.getString("reaction_"+id));
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddHaha(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    haha=response.body();
                    text_view_haha_fragement_image.setText(format(haha));
                    setReaction("haha");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addWoow(Integer id){
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        removeReaction(prefManager.getString("reaction_"+id));
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddWoow(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    woow=response.body();
                    text_view_woow_fragement_image.setText(format(woow));
                    setReaction("woow");

                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }

    public void deleteWoow(Integer id){

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteWoow(id);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    woow=response.body();
                    text_view_woow_fragement_image.setText(format(woow));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteLike(Integer id){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteLike(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    like=response.body();
                    text_view_like_fragement_image.setText(format(like));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteAngry(Integer id){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteAngry(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    angry=response.body();
                    text_view_angry_fragement_image.setText(format(angry));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteHaha(Integer id){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteHaha(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    haha=response.body();
                    text_view_haha_fragement_image.setText(format(haha));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteSad(Integer id){
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteSad(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    sad=response.body();
                    text_view_sad_fragement_image.setText(format(sad));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteLove(Integer id){
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteLove(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    love=response.body();
                    text_view_love_fragement_image.setText(format(love));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void setReaction(String reaction){
        text_view_like_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_love_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_angry_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_sad_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_haha_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_woow_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));

        text_view_like_fragement_image.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_woow_fragement_image.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_love_fragement_image.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_sad_fragement_image.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_angry_fragement_image.setTextColor(getResources().getColor(R.color.primary_text));
        text_view_haha_fragement_image.setTextColor(getResources().getColor(R.color.primary_text));

        like_button_like_fragement_image.setLiked(false);
        like_button_love_fragement_image.setLiked(false);
        like_button_angry_fragement_image.setLiked(false);
        like_button_haha_fragement_image.setLiked(false);
        like_button_sad_fragement_image.setLiked(false);
        like_button_woow_fragement_image.setLiked(false);


        if (reaction.equals("like")){
            prefManager.setString("reaction_"+id,"like");
            text_view_like_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_like_fragement_image.setTextColor(getResources().getColor(R.color.white));
            like_button_like_fragement_image.setLiked(true);

        }else if (reaction.equals("woow")){
            prefManager.setString("reaction_"+id,"woow");
            text_view_woow_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_woow_fragement_image.setTextColor(getResources().getColor(R.color.white));
            like_button_woow_fragement_image.setLiked(true);

        }else if (reaction.equals("love")){
            prefManager.setString("reaction_"+id,"love");
            text_view_love_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_love_fragement_image.setTextColor(getResources().getColor(R.color.white));
            like_button_love_fragement_image.setLiked(true);

        }else if (reaction.equals("angry")){
            prefManager.setString("reaction_"+id,"angry");
            text_view_angry_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_angry_fragement_image.setTextColor(getResources().getColor(R.color.white));
            like_button_angry_fragement_image.setLiked(true);

        }else if (reaction.equals("sad")){
            prefManager.setString("reaction_"+id,"sad");
            text_view_sad_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_sad_fragement_image.setTextColor(getResources().getColor(R.color.white));
            like_button_sad_fragement_image.setLiked(true);

        }else if (reaction.equals("haha")){
            prefManager.setString("reaction_"+id,"haha");
            text_view_haha_fragement_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_haha_fragement_image.setTextColor(getResources().getColor(R.color.white));
            like_button_haha_fragement_image.setLiked(true);
        }

    }
    public void removeReaction(String  reaction){
        if (reaction.equals("like")){
            deleteLike(id);
        }else if (reaction.equals("woow")){
            deleteWoow(id);
        }else if (reaction.equals("love")){
            deleteLove(id);
        }else if (reaction.equals("angry")){
            deleteAngry(id);
        }else if (reaction.equals("sad")){
            deleteSad(id);
        }else if (reaction.equals("haha")){
            deleteHaha(id);
        }
    }



    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<Object, String, String> {

        private int position;
        private String old = "-100";
        private boolean runing = true;
        private String share_app;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setDownloading(true);
            Log.v("prepost","ok");
        }
        public boolean dir_exists(String dir_path)
        {
            boolean ret = false;
            File dir = new File(dir_path);
            if(dir.exists() && dir.isDirectory())
                ret = true;
            return ret;
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            runing = false;
        }
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(Object... f_url) {
            int count;
            try {
                URL url = new URL((String) f_url[0]);
                String title = (String) f_url[1];
                String extension = (String) f_url[2];

                Log.e("MAIN", "doInBackground: "+extension);

                extension = ".gif";

                
                this.position = (int) f_url[3];
                this.share_app = (String) f_url[4];
                Log.v("v",(String) f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);



                String dir_path = Environment.getExternalStorageDirectory().toString() + "/StatusAppImages/";

                if (!dir_exists(dir_path)){
                    File directory = new File(dir_path);
                    if(directory.mkdirs()){
                        Log.v("dir","is created 1");
                    }else{
                        Log.v("dir","not created 1");

                    }
                    if(directory.mkdir()){
                        Log.v("dir","is created 2");
                    }else{
                        Log.v("dir","not created 2");

                    }
                }else{
                    Log.v("dir","is exist");
                }
                File file= new File(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension);
                if(!file.exists()){
                    Log.v("dir","file is exist");
                    OutputStream output = new FileOutputStream(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension);


                    byte data[] = new byte[1024];

                    long total = 0;


                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress(""+(int)((total*100)/lenghtOfFile));
                        // writing data to file
                        output.write(data, 0, count);
                        if (!runing){
                            Log.v("v","not rurning");
                        }
                    }

                    output.flush();

                    output.close();
                    input.close();

                }
                MediaScannerConnection.scanFile(getActivity().getApplicationContext(), new String[] {dir_path+title.toString().replace("/","_")+"_"+id+"."+extension},
                        null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    final Uri contentUri = Uri.fromFile(new File(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension));
                    scanIntent.setData(contentUri);
                    getActivity().sendBroadcast(scanIntent);
                } else {
                    final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
                    getActivity().sendBroadcast(intent);
                }
                path = dir_path+title.toString().replace("/","_")+"_"+id+"."+extension;
            } catch (Exception e) {
                //Log.v("ex",e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            try {
                if(!progress[0].equals(old)){
                    old=progress[0];
                    Log.v("download",progress[0]+"%");
                    setDownloading(true);
                    setProgressValue(Integer.parseInt(progress[0]));
                }
            }catch (Exception e){

            }

        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {

            setDownloading(false);
            if (path==null){
                Toasty.error(getActivity().getApplicationContext(), getResources().getString(R.string.download_failed), Toast.LENGTH_SHORT, true).show();
            }else {
                addDownload(id);
                switch (share_app) {
                    case WHATSAPP_ID:
                        shareWhatsapp(path);
                        break;
                    case FACEBOOK_ID:
                        shareFacebook(path);
                        break;
                    case MESSENGER_ID:
                        shareMessenger(path);
                        break;
                    case INSTAGRAM_ID:
                        shareInstagram(path);
                        break;
                    case SHARE_ID:
                        share(path);
                        break;
                    case TWITTER_ID:
                        shareTwitter(path);
                        break;
                    case SNAPSHAT_ID:
                        shareSnapshat(path);
                        break;
                    case HIKE_ID:
                        shareHike(path);
                        break;
                    case DOWNLOAD_ID:
                        download();
                        break;
                }
            }
        }

        private void download() {
            Toasty.success(getActivity().getApplicationContext(), getResources().getString(R.string.images_downloaded), Toast.LENGTH_SHORT, true).show();
        }
        public void shareWhatsapp(String path){

            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(WHATSAPP_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(),getResources().getString(R.string.whatsapp_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareFacebook(String path){
            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(FACEBOOK_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(),getResources().getString(R.string.facebook_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareMessenger(String path){
            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(MESSENGER_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(),getResources().getString(R.string.messenger_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareSnapshat(String path){
            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(SNAPSHAT_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(),getResources().getString(R.string.snapchat_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareHike(String path){
            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(HIKE_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);


            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(),getResources().getString(R.string.hike_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareInstagram(String path){
            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(INSTAGRAM_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(), getResources().getString(R.string.instagram_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareTwitter(String path){
            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(TWITTER_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(), getResources().getString(R.string.twitter_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }
        public void share(String path){
            Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(Intent.createChooser(shareIntent,getResources().getString(R.string.share_via)+ " " + getResources().getString(R.string.app_name) ));
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(getActivity().getApplicationContext(),getResources().getString(R.string.app_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
    }
    public void setDownloading(Boolean downloading){
        if (downloading){
            relative_layout_progress_fragement_image.setVisibility(View.VISIBLE);
        }else{
            relative_layout_progress_fragement_image.setVisibility(View.GONE);
        }
        this.downloading = downloading;
    }
    public void setProgressValue(int progress){
        this.progress_bar_fragement_image.setProgress(progress);
        this.text_view_progress_fragement_image.setText(getResources().getString(R.string.downloading)+" "+progress+" %");
    }
    public void addDownload(final Integer id){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddDownload(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private void loadMore() {
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Gif>> call = service.ImageByRandom(language);
        call.enqueue(new Callback<List<Gif>>() {
            @Override
            public void onResponse(Call<List<Gif>> call, Response<List<Gif>> response) {
                    if(response.isSuccessful()){
                        if (response.body().size()!=0){
                            gifList.clear();
                            for (int i=0;i<response.body().size();i++){
                                    gifList.add(response.body().get(i));
                            }
                            gifAdapter.notifyDataSetChanged();
                            recycler_view_status_load_more.setNestedScrollingEnabled(false);
                        }
                    }
            }
            @Override
            public void onFailure(Call<List<Gif>> call, Throwable t) {

            }
        });
    }
    private final String TAG = GifActivity.class.getSimpleName();
    private RelativeLayout nativeBannerAdContainer;
    private LinearLayout adView;
    private NativeBannerAd nativeBannerAd;

    public void initAds(){
            if (prefManager.getString("SUBSCRIBED").equals("TRUE"))
                 return;
            if (!getResources().getString(R.string.FACEBOOK_ADS_ENABLED_BANNER).equals("true"))
            return;
        // Instantiate a NativeBannerAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeBannerAd = new NativeBannerAd(getActivity(), getResources().getString(R.string.FACEBOOK_ADS_NATIVE_BANNER_PLACEMENT_ID));
        nativeBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeBannerAd == null || nativeBannerAd != ad) {
                    return;
                }
                // Inflate Native Banner Ad into Container
                inflateAd(nativeBannerAd);
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        });
        // load the ad
        nativeBannerAd.loadAd();
    }
    private void inflateAd(NativeBannerAd nativeBannerAd) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeBannerAdContainer = view.findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_layout, nativeBannerAdContainer, false);
        nativeBannerAdContainer.addView(adView);

        // Add the AdChoices icon (NativeBannerAdActivity.this, nativeBannerAd, true);
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdChoicesView adChoicesView = new AdChoicesView(getActivity().getApplicationContext(),nativeBannerAd,true);
        adChoicesContainer.addView(adChoicesView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }
}
