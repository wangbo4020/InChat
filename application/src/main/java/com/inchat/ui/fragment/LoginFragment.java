package com.inchat.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.inchat.Constants;
import com.inchat.R;
import com.inchat.content.AppPreferences;
import com.inchat.server.ServerClient;
import com.inchat.ui.BaseFragment;
import com.inchat.utils.RegexHelper;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static cn.smssdk.SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE;

/**
 * Created by Dylan on 2017/12/2.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "LoginFragment";

    public interface OnLoginListener {
        /**
         * 登录成功
         * @param o 预留参数
         */
        void onLoginSuccess(Object o);

        /**
         * 登录失败
         * @param errcode 错误码
         * @param reason 原因
         */
        void onLoginFailed(int errcode, String reason);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private TextView tvCountryCode;
    private EditText etTelephone;
    private EditText etVerifyCode;
    private TextView tvSendVerifyCode;
    private ContentLoadingProgressBar progressWait;

    private VerifyCodeTimer mVerifyCodeTimer;
    private ProgressDialog mProgressDialog;

    private OnLoginListener mListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobSDK.init(getContext());
        SMSSDK.registerEventHandler(mSmsSdkEvent); //注册短信回调
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnLoginListener) {
            mListener = (OnLoginListener) getParentFragment();
        } else if (getActivity() instanceof OnLoginListener) {
            mListener = (OnLoginListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCountryCode = view.findViewById(R.id.tv_country_code);
        etTelephone = view.findViewById(R.id.et_tel);
        etVerifyCode = view.findViewById(R.id.et_code);
        tvSendVerifyCode = view.findViewById(R.id.tv_send_code);
        progressWait = view.findViewById(R.id.progress_wait);
        tvSendVerifyCode.setOnClickListener(this);
        view.findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        SMSSDK.unregisterEventHandler(mSmsSdkEvent);
        if (mVerifyCodeTimer != null) mVerifyCodeTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        String countryCode = tvCountryCode.getText().toString().substring(1);
        String telephone = etTelephone.getText().toString();
        tvCountryCode.setTag(countryCode);
        etTelephone.setTag(telephone);
        switch (v.getId()) {
            case R.id.tv_send_code:
                // substring去掉第一位的+号
                sendVerifyCode(countryCode, telephone);
                break;
            case R.id.btn_login:
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(getContext());
                }
                mProgressDialog.show();
                String verifyCode = etVerifyCode.getText().toString();
                etVerifyCode.setTag(verifyCode);
                SMSSDK.submitVerificationCode(countryCode, telephone, verifyCode);
                break;
        }
    }

    public void sendVerifyCode(String countryCode, String telephone) {

        //判断非空
        if (TextUtils.isEmpty(telephone)) {
            return;
        }

        if (!RegexHelper.matchMobileInChain(telephone)) {
            return;
        }

        progressWait.show();
        tvSendVerifyCode.setVisibility(View.INVISIBLE);
        tvSendVerifyCode.setClickable(false);
        SMSSDK.getVerificationCode(countryCode, telephone);
        if (mVerifyCodeTimer == null) {
            mVerifyCodeTimer = new VerifyCodeTimer(getResources(), getResources().getInteger(R.integer.login_verify_code_interval), 1000);
        }
    }


//    private void getLocationLogin(String zone, String telephone) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("zone", zone);
//        map.put("phone", telephone);
//        new HttpUtils().postMap(APIConstant.USER_LOGIN, map, new HttpUtils.HttpCallback() {
//            @Override
//            public void onStart() {
//                super.onStart();
////                dialog.show(mContext);
//            }
//
//            @Override
//            public void onSuccess(String data) {
////                dialog.close();
//                Gson gson = new Gson();
//                final User user = gson.fromJson(data, User.class);
//                if(RequestCode.REQUEST_CODE_SUCCESS==user.sendVerifyCode()){
//                    mContext.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //登录成功
//                            SPUtils.saveObject(mContext,Str.USER,user);
//                        }
//                    });
//
////                    SPUtils.put(mContext,"user",user);
//                    //环信登录
//                    EMCUserManager.EMCLogin(mContext,user.getResult().getId()+"","123456");
//                }
//                L.e(TAG ,"服务器登录接口成功！" + data);
//            }
//
//            @Override
//            public void onError(String msg) {
//                super.onError(msg);
////                dialog.close();
//                L.e(TAG ,"服务器登录接口失败！" + msg);
//            }
//        });
//    }


    private EventHandler mSmsSdkEvent = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (data instanceof Throwable) {
                Log.e(TAG, "afterEvent: ", ((Throwable) data));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else if (result == SMSSDK.RESULT_COMPLETE) {
                Log.e(TAG, "回调完成！event==" + event + "--result--" + result + "--data--" + data.toString());
                //回调完成
                if (event == EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.e(TAG, "提交验证码成功！");
                    try {
                        JSONObject json = new JSONObject(data.toString());
                        Log.d(TAG, "afterEvent: " + json.toString());
                        json.getString("country");
                        json.getString("phone");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (etTelephone.getTag() != null && etVerifyCode.getTag() != null)
                        Constants.SERVER_CLIENT.login(etVerifyCode.getTag().toString(), etTelephone.getTag().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map((body) -> body.string())
                                .doOnNext(s -> Log.d(TAG, "accept: " + s))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        JSONObject json = new JSONObject(s);
                                        final JSONObject result = json.getJSONObject(ServerClient.KEY_RESULT_MODEL);
                                        EMClient.getInstance().login(result.getInt(ServerClient.KEY_ID) + "", "123456", new EMCallBack() {
                                            @Override
                                            public void onSuccess() {
                                                AppPreferences pref = AppPreferences.getInstance();
                                                try {
                                                    pref.setId(result.getInt(ServerClient.KEY_ID));
                                                    pref.setNickame(result.getString(ServerClient.KEY_NICKNAME));
                                                    pref.setUserName(result.getString(ServerClient.KEY_USERNAME));
                                                    pref.setPortraitUrl(result.getString(ServerClient.KEY_PORTRAIT_URL));
                                                    pref.setTelephone(result.getString(ServerClient.KEY_TELEPHONE));
                                                    pref.setToken(result.getString(ServerClient.KEY_TOKEN));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                runOnUiThread(() -> {
                                                    if (mProgressDialog != null) {
                                                        mProgressDialog.dismiss();
                                                    }
                                                    if (mListener != null) {
                                                        mListener.onLoginSuccess(result);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onError(int i, String s) {
                                                runOnUiThread(() -> {
                                                    if (mProgressDialog != null) {
                                                        mProgressDialog.dismiss();
                                                    }
                                                    if (mListener != null) {
                                                        mListener.onLoginFailed(i, s);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onProgress(int i, String s) {

                                            }
                                        });
                                    }
                                });
                    // login
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.e(TAG, "获取验证码成功！");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVerifyCodeTimer.start();
                            progressWait.hide();
                            tvSendVerifyCode.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    Log.e(TAG, "返回支持发送验证码的国家列表！");
                }
            }
        }
    };


    private class VerifyCodeTimer extends CountDownTimer {

        private Resources res;
        private String mTimerTemplate;

        public VerifyCodeTimer(Resources res, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.res = res;
            this.mTimerTemplate = res.getString(R.string.login_send_verify_timer);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvSendVerifyCode.setText(String.format(mTimerTemplate, (millisUntilFinished / 1000) + ""));
        }

        @Override
        public void onFinish() {
            // set tvSendVerifyCode enabled
            tvSendVerifyCode.setClickable(true);
            tvSendVerifyCode.setText(res.getString(R.string.login_send_verify_code));
        }
    }
}
