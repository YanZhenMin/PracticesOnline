package net.lzzy.practicesonline.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.SparseArray;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.constants.ApiConstants;
import net.lzzy.practicesonline.fragments.SplashFragment;
import net.lzzy.practicesonline.utils.AbstractStaticHandler;
import net.lzzy.practicesonline.utils.AppUtils;
import net.lzzy.practicesonline.utils.ViewUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

public class SplashActivity extends BaseActivity implements SplashFragment.OnSplashFinishedListener {
    private int seconds=10;
    private static final int WHAT_COUNTING=0;
    private static final int WHAT_EXCEPTION=1;
    private static final int WHAT_COUNT_DONE=2;
    private static final int WHAT_SERVER_OFF=3;
    private boolean isServerOn=true;
    private TextView tvCount;
    private SplashHandler handler=new SplashHandler(this);

    private static class SplashHandler extends AbstractStaticHandler<SplashActivity>{

        SplashHandler(SplashActivity context) {
            super(context);
        }

        @Override
        public void handleMessage(Message msg, SplashActivity activity) {
            switch (msg.what){
                case WHAT_COUNTING:
                    String display=msg.obj+"秒";
                    activity.tvCount.setText(display);
                    break;
                case WHAT_COUNT_DONE:
                    if (activity.isServerOn) {
                        activity.gotoMain();
                    }
                    break;
                case WHAT_EXCEPTION:
                    new AlertDialog.Builder(activity)
                            .setMessage(msg.obj.toString())
                            .setPositiveButton("继续",(dialog, which) -> activity.gotoMain())
                            .setNegativeButton("退出",(dialog, which) -> AppUtils.exit())
                            .show();
                    break;
                case WHAT_SERVER_OFF:
                    Activity context=AppUtils.getRunningActivity();
                    new AlertDialog.Builder(context)
                            .setMessage("服务器没有响应，是否继续？\n"+msg.obj)
                            .setPositiveButton("确定",(dialog, which) -> {
                                if (context instanceof SplashActivity){
                                    ((SplashActivity)context).gotoMain();
                                }
                            })
                            .setNeutralButton("退出",(dialog, which) -> AppUtils.exit())
                            .setNeutralButton("设置",(dialog, which) -> ViewUtils.gotoSetting(context))
                            .show();
                    break;
                    default:
                        break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!AppUtils.isNetworkAvailable()){
            new AlertDialog.Builder(this)
                    .setMessage("网络不可用，是否继续")
                    .setPositiveButton("退出",(dialog, which) -> AppUtils.exit())
                    .setNegativeButton("确定",(dialog, which) -> gotoMain())
                    .show();
        }else {
           ThreadPoolExecutor executor=AppUtils.getExecutor();
           executor.execute(this::countDown);
           executor.execute(this::detectServerStatus);
        }
        tvCount=findViewById(R.id.activity_splash_count_down);
    }

    private void countDown(){
        while (seconds>=0){
            handler.sendMessage(handler.obtainMessage(WHAT_COUNTING,seconds));
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                handler.sendMessage(handler.obtainMessage(WHAT_EXCEPTION,e.getMessage()));
            }
            seconds--;
        }
        handler.sendEmptyMessage(WHAT_COUNT_DONE);
    }

    private void detectServerStatus(){
        try {
            AppUtils.tryConnectServer(ApiConstants.URL_API);
        } catch (IOException e) {
            isServerOn=false;
            handler.sendMessage(handler.obtainMessage(WHAT_SERVER_OFF,e.getMessage()));
        }
    }
    public void gotoMain() {
        startActivity(new Intent(this,PracticesActivity.class));
        finish();
    }

    @Override
    public void cancelCount() {
        seconds=0;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getContainerId() {
        return R.id.fragment_splash_container;
    }

    @Override
    protected Fragment createFragment() {
        return new SplashFragment();
    }
}
