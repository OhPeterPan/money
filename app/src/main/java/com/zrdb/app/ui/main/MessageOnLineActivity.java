package com.zrdb.app.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.zrdb.app.R;
import com.zrdb.app.adapter.MessageOnLineAdapter;
import com.zrdb.app.ui.BaseActivity;
import com.zrdb.app.ui.bean.LoginBean;
import com.zrdb.app.ui.bean.MessageOnLineBean;
import com.zrdb.app.ui.presenter.MessageOnLinePresenter;
import com.zrdb.app.ui.response.MessageOnLineResponse;
import com.zrdb.app.ui.viewImpl.IMessageOnLineView;
import com.zrdb.app.util.Convert;
import com.zrdb.app.util.LogUtil;
import com.zrdb.app.util.SpUtil;
import com.zrdb.app.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class MessageOnLineActivity extends BaseActivity<MessageOnLinePresenter> implements IMessageOnLineView {
    @BindView(R.id.tvActTitle)
    TextView tvActTitle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tvActRightTitle)
    TextView tvActRightTitle;
    @BindView(R.id.ivToolbarRight)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.etMessageOnLine)
    EditText etMessageOnLine;
    @BindView(R.id.tvMessageSend)
    TextView tvMessageSend;
    private LoginBean account;
    private MessageOnLineAdapter adapter;
    private String avatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_on_line;
    }

    @Override
    protected void initPresenter() {
        presenter = new MessageOnLinePresenter(this);
    }

    private void initToolbar() {
        tvActTitle.setText("在线咨询");
        tvActTitle.setVisibility(View.VISIBLE);
        setBackRes(R.drawable.ic_back);
        setBackVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        account = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        initToolbar();
        initAdapter();
        sendNet();
    }

    private void initAdapter() {
        adapter = new MessageOnLineAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void sendNet() {
        presenter.sendNetGetMessage(account.token, account.uid);
    }

    @Override
    protected void initListener() {
        tvMessageSend.setOnClickListener(this);
    }

    @Override
    protected void innerListener(View v) {
        switch (v.getId()) {
            case R.id.tvMessageSend:
                sendNetSendMessage();
                break;
        }
    }

    private void sendNetSendMessage() {
        String content = etMessageOnLine.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            ToastUtil.showMessage("发送内容不能为空！", Toast.LENGTH_SHORT);
            return;
        }
        presenter.sendNetSendMessage(account.token, account.uid, content);
    }

    @Override
    public void getMessageResultSuccess(String result) {
        LogUtil.LogI("返回：" + result);
        MessageOnLineResponse response = Convert.fromJson(result, MessageOnLineResponse.class);
        List<MessageOnLineBean> list = response.data;
        parseList(list);
    }

    private void parseList(List<MessageOnLineBean> list) {
        if (list != null && !list.isEmpty()) {
            for (MessageOnLineBean bean :
                    list) {
                if (!StringUtils.equals("1", bean.fromid)) {
                    bean.type = 2;
                    avatar = bean.fromavatar;
                } else {
                    bean.type = 1;
                }
            }
        }
        adapter.setNewData(list);
        adapter.setEmptyView(getEmpty("暂无消息~"));
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void sendMessageSuccess(String result) {
        String content = etMessageOnLine.getText().toString().trim();
        MessageOnLineBean bean = new MessageOnLineBean();
        bean.type = 2;
        bean.content = content;
        bean.fromavatar = avatar;
        adapter.addData(bean);
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
        etMessageOnLine.setText("");
    }

    @Override
    public void showDataErrInfo(String result) {

    }
}
