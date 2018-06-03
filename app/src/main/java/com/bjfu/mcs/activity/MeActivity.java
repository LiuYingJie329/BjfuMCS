package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bjfu.mcs.R;
import com.bjfu.mcs.bean.PersonInfo;
import com.bjfu.mcs.greendao.DataBaseHandler;
import com.bjfu.mcs.loginSign.LoginActivity;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxDataTool;
import com.bjfu.mcs.utils.Rx.RxPhotoTool;
import com.bjfu.mcs.utils.Rx.RxSPTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.bjfu.mcs.utils.Rx.dialog.RxDialogChooseImage;
import com.bjfu.mcs.utils.Rx.dialog.RxDialogScaleView;
import com.bjfu.mcs.utils.other.ConvertUtils;
import com.bjfu.mcs.utils.picker.AddressPickTask;
import com.bjfu.mcs.utils.picker.DatePicker;
import com.bjfu.mcs.utils.picker.DateTimePicker;
import com.bjfu.mcs.utils.picker.OptionPicker;
import com.bjfu.mcs.utils.picker.WheelView;
import com.bjfu.mcs.utils.picker.entity.City;
import com.bjfu.mcs.utils.picker.entity.County;
import com.bjfu.mcs.utils.picker.entity.Province;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.OkHttpClient;

import static cn.bmob.v3.BmobUser.getCurrentUser;
import static com.bjfu.mcs.application.MCSApplication.appcache;
import static com.bjfu.mcs.utils.Rx.RxToast.showToast;
import static com.bjfu.mcs.utils.Rx.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class MeActivity extends AppCompatActivity {

    @BindView(R.id.tv_bg)
    TextView mTvBg;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.ll_anchor_left)
    LinearLayout mLlAnchorLeft;
    @BindView(R.id.rl_avatar)
    RelativeLayout mRlAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_constellation)
    TextView mTvConstellation;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_lables)
    TextView mTvLables;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.editText2)
    TextView mEditText2;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.btn_exit)
    Button mBtnExit;
    @BindView(R.id.activity_user)
    LinearLayout mActivityUser;
    private Uri resultUri;
    private MeActivity mContext;
    private String headerimgurl;
    private static final int updateImage = 1;
    private static final int updateEmail = 2;
    private static final int updateBirthday = 3;
    private static final int updateCons = 4;
    private static final int updateSex = 5;
    private static final int updateLoc = 6;
    private static final int updateTag = 7;
    private static final int updateSign = 8;
    private static final int updateNick = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人资料");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        initData();
    }

    private void initData() {

        PersonInfo personInfo = DataBaseHandler.getCurrPesonInfo();
        if (null != personInfo) {
            Glide.with(mContext).
                    load(personInfo.getUserAvatar()).
                    bitmapTransform(new CropCircleTransformation(mContext)).
                    thumbnail(0.5f).
                    placeholder(R.drawable.circle_elves_ball).
                    priority(Priority.LOW).
                    error(R.drawable.circle_elves_ball).
                    fallback(R.drawable.circle_elves_ball).
                    into(mIvAvatar);

            Log.i("initData---","更新头像成功");
            if(!RxDataTool.isNullString(personInfo.getSqlemail())){
                mTvEmail.setText(personInfo.getSqlemail());
            }
            if(!RxDataTool.isNullString(personInfo.getUserNickname())){
                mTvName.setText(personInfo.getUserNickname());
            }
            if(!RxDataTool.isNullString(personInfo.getUserBirthday())){
                mTvBirthday.setText(personInfo.getUserBirthday());
            }
            if(!RxDataTool.isNullString(personInfo.getUserConstellation())){
                mTvConstellation.setText(personInfo.getUserConstellation());
            }
            if(!RxDataTool.isNullString(personInfo.getUsersex())){
                mTvSex.setText(personInfo.getUsersex());
            }
            if(!RxDataTool.isNullString(personInfo.getUserAddress())){
                mTvAddress.setText(personInfo.getUserAddress());
            }
            if(!RxDataTool.isNullString(personInfo.getUserTag())){
                mTvLables.setText(personInfo.getUserTag());
            }
            if(!RxDataTool.isNullString(personInfo.getUserSign())){
                mEditText2.setText(personInfo.getUserSign());
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case updateImage:
                    final BmobFile bmobFile = new BmobFile(new File(RxPhotoTool.getImageAbsolutePath(mContext, (Uri) msg.obj)));
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                headerimgurl= bmobFile.getFileUrl();
                                PersonInfo personInfo = getCurrentUser(PersonInfo.class);
                                personInfo.setUserAvatar(headerimgurl);
                                personInfo.update(personInfo.getObjectId(),new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e == null){
                                            PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                            if(null != piInfo){
                                                piInfo.setUserAvatar(headerimgurl);
                                            }
                                            DataBaseHandler.updatePesonInfo(piInfo);
                                            RxToast.success("更新头像成功");
                                        }else {
                                            if(e.getErrorCode() == 206){
                                                RxToast.error("您的账号在其他地方登录，请重新登录");
                                                appcache.put("has_login", "no");
                                                PersonInfo.logOut();
                                                RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                            }else{
                                                RxToast.error("更新头像失败");
                                            }
                                        }
                                    }
                                });
                            }else{
                                RxToast.error("头像上传失败");
                            }
                        }

                        @Override
                        public void onProgress(Integer value) {
                            //返回上传进度
                            RxToast.info("上传" + value + "%");
                            super.onProgress(value);
                        }
                    });
                    break;
                case updateEmail:
                    String email = (String) msg.obj;
                    PersonInfo personInfoEmail = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfoEmail.setSqlemail(email);
                    personInfoEmail.update(personInfoEmail.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setSqlemail(email);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvEmail.setText(email);
                                    }
                                });
                                RxToast.success("邮箱信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("邮箱信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updateBirthday:
                    String birthday = (String) msg.obj;
                    PersonInfo personInfoBir = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfoBir.setUserBirthday(birthday);
                    personInfoBir.update(personInfoBir.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserBirthday(birthday);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvBirthday.setText(birthday);
                                    }
                                });
                                RxToast.success("生日信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                }else{
                                    Log.i("生日信息更新失败",e.getMessage());
                                    RxToast.error("生日信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updateCons:
                    String constellation = (String) msg.obj;
                    PersonInfo personInfoCons = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfoCons.setUserConstellation(constellation);
                    personInfoCons.update(personInfoCons.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserConstellation(constellation);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvConstellation.setText(constellation);
                                    }
                                });
                                RxToast.success("星座信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("星座信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updateSex:
                    String sex = (String) msg.obj;
                    PersonInfo personInfoSex = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfoSex.setUsersex(sex);
                    personInfoSex.update(personInfoSex.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUsersex(sex);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvSex.setText(sex);
                                    }
                                });
                                RxToast.success("性别信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("性别信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updateLoc:
                    String address = (String) msg.obj;
                    PersonInfo personInfoLoc = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfoLoc.setUserAddress(address);
                    personInfoLoc.update(personInfoLoc.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserAddress(address);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvAddress.setText(address);
                                    }
                                });
                                RxToast.success("所在地信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("所在地信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updateTag:
                    String usertag = (String) msg.obj;
                    PersonInfo personInfotag = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfotag.setUserTag(usertag);
                    personInfotag.update(personInfotag.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserTag(usertag);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvLables.setText(usertag);
                                    }
                                });
                                RxToast.success("标签信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("标签信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updateSign:
                    String sign = (String) msg.obj;
                    PersonInfo personInfoSign = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfoSign.setUserSign(sign);
                    personInfoSign.update(personInfoSign.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserSign(sign);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mEditText2.setText(sign);
                                    }
                                });
                                RxToast.success("个性签名信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("个性签名信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                case updateNick:
                    String Nickname = (String) msg.obj;
                    PersonInfo personInfoNick = BmobUser.getCurrentUser(PersonInfo.class);
                    personInfoNick.setUserNickname(Nickname);
                    personInfoNick.update(personInfoNick.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                PersonInfo piInfo = DataBaseHandler.getCurrPesonInfo();
                                if(null != piInfo){
                                    piInfo.setUserNickname(Nickname);
                                }
                                DataBaseHandler.updatePesonInfo(piInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvName.setText(Nickname);
                                    }
                                });
                                RxToast.success("昵称信息更新成功");
                            }else {
                                if(e.getErrorCode() == 206){
                                    RxToast.error("您的账号在其他地方登录，请重新登录");
                                    appcache.put("has_login", "no");
                                    PersonInfo.logOut();
                                    RxActivityTool.skipActivityAndFinish(MeActivity.this, LoginActivity.class);
                                }else{
                                    RxToast.error("昵称信息更新失败");
                                }
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    protected void initView() {
        Resources r = mContext.getResources();
        resultUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.circle_elves_ball) + "/"
                + r.getResourceTypeName(R.drawable.circle_elves_ball) + "/"
                + r.getResourceEntryName(R.drawable.circle_elves_ball));


        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialogChooseImage();
            }
        });
        mIvAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                RxImageTool.showBigImageView(mContext, resultUri);
                String resUri = RxSPTool.getContent(mContext,"AVATAR");
                Uri uri = Uri.parse(resUri);
                Log.i("resUri----",resUri);
                RxDialogScaleView rxDialogScaleView = new RxDialogScaleView(mContext);
                rxDialogScaleView.setImageUri(uri);
                rxDialogScaleView.show();
                return false;
            }
        });
    }

    private void initDialogChooseImage() {
        RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mContext, TITLE);
        dialogChooseImage.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
//                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
                    initUCrop(data.getData());
                }

                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                   /* data.getExtras().get("data");*/
//                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                Glide.with(mContext).
                        load(RxPhotoTool.cropImageUri).
                        diskCacheStrategy(DiskCacheStrategy.RESULT).
                        bitmapTransform(new CropCircleTransformation(mContext)).
                        thumbnail(0.5f).
                        placeholder(R.drawable.circle_elves_ball).
                        priority(Priority.LOW).
                        error(R.drawable.circle_elves_ball).
                        fallback(R.drawable.circle_elves_ball).
                        into(mIvAvatar);
//                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    //上传头像文件
                    updatePersonInfoImage(updateImage,resultUri);
                    roadImageView(resultUri, mIvAvatar);
                    RxSPTool.putContent(mContext, "AVATAR", resultUri.toString());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void updatePersonInfoImage(int type,Uri content){
        Message msg = new Message();
        msg.what = type;
        msg.obj =  content;
        mHandler.sendMessage(msg);
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        Glide.with(mContext).
                load(uri).
                diskCacheStrategy(DiskCacheStrategy.RESULT).
                bitmapTransform(new CropCircleTransformation(mContext)).
                thumbnail(0.5f).
                placeholder(R.drawable.circle_elves_ball).
                priority(Priority.LOW).
                error(R.drawable.circle_elves_ball).
                fallback(R.drawable.circle_elves_ball).
                into(imageView);

        Log.i("选中的照片",RxPhotoTool.getImageAbsolutePath(this, uri));
        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    private void initUCrop(Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(mContext);
    }

    @OnClick({R.id.tv_email,R.id.tv_birthday,R.id.tv_constellation,R.id.tv_address,
            R.id.tv_name,R.id.editText2,R.id.tv_lables,R.id.tv_sex})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_birthday:
                final DatePicker picker = new DatePicker(this);
                picker.setCanceledOnTouchOutside(true);
                picker.setUseWeight(true);
                picker.setTopPadding(ConvertUtils.toPx(this, 10));
                picker.setRangeEnd(1990, 1, 11);
                picker.setRangeStart(2000, 8, 29);
                picker.setSelectedItem(2050, 10, 14);
                picker.setResetWhileWheel(false);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        updatePersonInfoMsg(updateBirthday,year + "/" + month + "/" + day);
                    }
                });
                picker.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
                    }
                });
                picker.show();
                break;

            case R.id.tv_email:
                new MaterialDialog.Builder(this)
                        .title("邮箱")
                        .content("输入内容")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 30)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "*********@163.com",
                                "*********@163.com",
                                false,
                                (dialog, input) -> updatePersonInfoMsg(updateEmail,input.toString()))
                        .show();
                break;
            case R.id.tv_constellation:
                boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
                OptionPicker optionPicker = new OptionPicker(this,
                        isChinese ? new String[]{
                                "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
                                "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"
                        } : new String[]{
                                "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
                                "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
                        });
                optionPicker.setCycleDisable(false);//不禁用循环
                optionPicker.setTopBackgroundColor(0xFFEEEEEE);
                optionPicker.setTopHeight(35);
                optionPicker.setTopLineColor(Color.BLUE);
                optionPicker.setTopLineHeight(1);
                optionPicker.setTitleText(isChinese ? "请选择" : "Please pick");
                optionPicker.setTitleTextColor(0xFF999999);
                optionPicker.setTitleTextSize(15);
                optionPicker.setCancelTextColor(getResources().getColor(R.color.pickercancel));
                optionPicker.setCancelTextSize(16);
                optionPicker.setSubmitTextColor(getResources().getColor(R.color.pickersure));
                optionPicker.setSubmitTextSize(16);
                optionPicker.setTextColor(Color.BLACK, 0xFF999999);
                WheelView.DividerConfig config = new WheelView.DividerConfig();
                config.setColor(0xFFEE0000);//线颜色
                config.setAlpha(140);//线透明度
                config.setRatio((float) (1.0 / 8.0));//线比率
                optionPicker.setDividerConfig(config);
                optionPicker.setBackgroundColor(0xFFE1E1E1);
                //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
                optionPicker.setSelectedIndex(7);
                optionPicker.setCanceledOnTouchOutside(true);
                optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        Toast.makeText(mContext,"index=" + index + ", item=" + item,Toast.LENGTH_LONG).show();
                        updatePersonInfoMsg(updateCons,item);
                    }
                });
                optionPicker.show();
                break;
            case R.id.tv_address:
                AddressPickTask task = new AddressPickTask(this);
                task.setHideProvince(false);
                task.setHideCounty(false);
                task.setCallback(new AddressPickTask.Callback() {
                    @Override
                    public void onAddressInitFailed() {
                        showToast("数据初始化失败");
                    }

                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        if (county == null) {
                            updatePersonInfoMsg(updateLoc,province.getAreaName() + city.getAreaName());
                        } else {
                            updatePersonInfoMsg(updateLoc,province.getAreaName() + city.getAreaName() + county.getAreaName());
                        }
                    }
                });
                task.execute("贵州", "毕节", "纳雍");
                break;
            case R.id.tv_name:
                new MaterialDialog.Builder(this)
                        .title("昵称")
                        .content("修改昵称")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "昵称",
                                "昵称",
                                false,
                                (dialog, input) -> updatePersonInfoMsg(updateNick,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.editText2:
                new MaterialDialog.Builder(this)
                        .title("个性签名")
                        .content("修改个性签名")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 20)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "长按头像显示大图",
                                "长按头像显示大图",
                                false,
                                (dialog, input) -> updatePersonInfoMsg(updateSign,input.toString()))
                        .checkBoxPromptRes(R.string.extra20, true, null)
                        .show();
                break;
            case R.id.tv_lables:
                new MaterialDialog.Builder(this)
                        .title("标签")
                        .content("输入内容")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 16)
                        .positiveText("提交")
                        .negativeText("取消")
                        .input(
                                "北京林业大学",
                                "北京林业大学",
                                false,
                                (dialog, input) -> updatePersonInfoMsg(updateTag,input.toString()))
                        .show();
                break;
            case R.id.tv_sex:
                OptionPicker sexpicker = new OptionPicker(this, new String[]{
                        "男", "女"
                });
                sexpicker.setCanceledOnTouchOutside(false);
                sexpicker.setDividerRatio(WheelView.DividerConfig.FILL);
                sexpicker.setShadowColor(Color.WHITE, 40);
                sexpicker.setTextColor(Color.BLACK);
                sexpicker.setSelectedIndex(1);
                sexpicker.setSubmitTextSize(16);
                sexpicker.setCancelTextSize(16);
                sexpicker.setCycleDisable(true);
                sexpicker.setCancelTextColor(getResources().getColor(R.color.pickercancel));
                sexpicker.setSubmitTextColor(getResources().getColor(R.color.pickersure));
                sexpicker.setDividerColor(getResources().getColor(R.color.pickerdivid));
                sexpicker.setTopLineColor(getResources().getColor(R.color.pickerdivid));
                sexpicker.setTextSize(18);
                sexpicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        //showToast("index=" + index + ", item=" + item);
                        updatePersonInfoMsg(updateSex,item);
                    }
                });
                sexpicker.show();
                break;
            default:
                break;
        }
    }

    private void updatePersonInfoMsg(int type,String content){
        Message msg = new Message();
        msg.what = type;
        msg.obj =  content;
        mHandler.sendMessage(msg);
    }

    public void updatepersoninfo(){
        PersonInfo user = BmobUser.getCurrentUser(PersonInfo.class);

        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    //保存成功
                }else{
                    //保存失败
                }
            }
        });
    }
}
