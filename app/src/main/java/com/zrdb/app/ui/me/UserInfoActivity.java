package com.zrdb.app.ui.me;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.model.HttpParams;
import com.zrdb.app.R;
import com.zrdb.app.albumconfig.AlbumConfig;
import com.zrdb.app.image_loader.ImageLoader;
import com.zrdb.app.imagecompress.ImageCompress;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.UserInfoBean;
import com.zrdb.app.ui.presenter.UserInfoPresenter;
import com.zrdb.app.ui.response.StrResponse;
import com.zrdb.app.ui.response.UserResponse;
import com.zrdb.app.ui.viewImpl.IUserInfoView;
import com.zrdb.app.util.ApiUtils;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements IUserInfoView, RadioGroup.OnCheckedChangeListener, AlbumConfig.IUploadListener, ImageCompress.CompressImageListener {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.civUserPicture)
    CircleImageView civUserPicture;
    @BindView(R.id.etUserNickName)
    EditText etUserNickName;
    @BindView(R.id.rbGenderMan)
    RadioButton rbGenderMan;
    @BindView(R.id.rbGenderMen)
    RadioButton rbGenderMen;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.etUserAddress)
    EditText etUserAddress;
    @BindView(R.id.btnChangeUserInfo)
    Button btnChangeUserInfo;
    private LoginBean account;
    private String picture = "";
    private String name = "";
    private String gender = "";
    private String address = "";
    private ImageCompress imageCompress;
    private AlbumConfig albumConfig;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initPresenter() {
        presenter = new UserInfoPresenter(this);
    }

    private void initToolbar() {
        tvActTitle.setText("编辑资料");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
        //ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        sendNet();
    }

    private void sendNet() {
        if (presenter == null) initPresenter();
        presenter.sendNet(account.token, account.uid);
    }

    @Override
    public void getUserInfoSuccess(String result) {
        LogUtil.logResult("用户信息", result);
        UserResponse response = Convert.fromJson(result, UserResponse.class);
        UserInfoBean userInfo = response.data.userinfo;
        if (!StringUtils.isEmpty(userInfo.thumb)) {
            picture = userInfo.thumb;
            ImageLoader.with(this)
                    .load(ApiUtils.Config.getImageDimen() + userInfo.thumb)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(civUserPicture);
        }
        etUserNickName.setText(StringUtils.isEmpty(userInfo.username) ? "" : userInfo.username);
        name = userInfo.username;
        etUserAddress.setText(StringUtils.isEmpty(userInfo.city) ? "" : userInfo.city);
        address = userInfo.city;
        if (!StringUtils.isEmpty(userInfo.sex)) {
            rbGenderMan.setChecked(StringUtils.equals("1", userInfo.sex));
            rbGenderMen.setChecked(StringUtils.equals("2", userInfo.sex));
            gender = userInfo.sex;
        }
    }

    @Override
    public void showDataErrInfo(Object result) {

    }


    @Override
    protected void initListener() {
        civUserPicture.setOnClickListener(this);
        btnChangeUserInfo.setOnClickListener(this);
        rgGender.setOnCheckedChangeListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.civUserPicture:
                chooseImage(true);
                break;
            case R.id.btnChangeUserInfo://保存
                changeUserInfo();
                break;
        }
    }

    private void changeUserInfo() {
        String name = etUserNickName.getText().toString().trim();
        String address = etUserAddress.getText().toString().trim();
        if (presenter.checkInfo(name, gender, address)) {
            presenter.sendNetSaveInfo(account.token, account.uid, name, picture, gender, address);
        }
    }

    @Override
    public void changeUserInfoSuccess(String result) {
        ToastUtil.showMessage("保存成功！", Toast.LENGTH_SHORT);
        setResult(0x002);
    }

    private void chooseImage(boolean isSingle) {
        initAlbumConfig();
        if (isSingle)
            albumConfig.singleImage(true);
    }

    /**
     * 选择图片的配置信息
     */
    private void initAlbumConfig() {
        if (albumConfig == null) {
            albumConfig = new AlbumConfig(this);
            albumConfig.setILoadImageListener(this);
        }
    }

    @Override
    public void getImagePathList(List<String> imagePathList) {
        imageCompress(imagePathList.get(0));
    }

    @Override
    public void getImagePath(String imagePath) {
        imageCompress(imagePath);
    }

    private void imageCompress(String imagePath) {
        if (imageCompress == null) {
            imageCompress = new ImageCompress(this);
            imageCompress.setOnCompleteCompressListener(this);
        }
        imageCompress.setFile(new File(imagePath));
        imageCompress.startCompress();
    }

    @Override
    public void onCompleteCompress(File file) {
        List<HttpParams.FileWrapper> list = new LinkedList<>();
        list.add(new HttpParams.FileWrapper(file, file.getName(), HttpParams.MEDIA_TYPE_JSON));
        presenter.sendNetUploadPic(account.token, account.uid, file);
    }

    @Override
    public void uploadPictureSuccess(String result) {
        StrResponse strResponse = Convert.fromJson(result, StrResponse.class);
        ImageLoader.with(this).load(String.format("%1$s%2$s", ApiUtils.Config.getImageDimen(), strResponse.data)).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(civUserPicture);
        picture = strResponse.data;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbGenderMan:
                gender = "1";
                break;
            case R.id.rbGenderMen:
                gender = "2";
                break;
        }
    }
}
