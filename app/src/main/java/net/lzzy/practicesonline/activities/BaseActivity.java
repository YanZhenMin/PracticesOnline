package net.lzzy.practicesonline.activities;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.lzzy.practicesonline.utils.AppUtils;

/**
 * Created by lzzy_gxy on 2019/4/11.
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppUtils.addActivity(this);
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutRes());
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment =manager.findFragmentById(getContainerId());
        if (fragment==null){
            fragment= createFragment();
            manager.beginTransaction().add(getContainerId(),fragment).commit();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.removeActivity(this);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        AppUtils.setRunning(getLocalClassName());
//    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUtils.setRunning(getLocalClassName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppUtils.setStopped(getLocalClassName());
    }

    /**
     * Activity的布局文件id
     * @return 布局资源id
     */
    protected abstract int getLayoutRes();

    /**
     * fragment容器id
     * @return id
     */
    protected abstract int getContainerId();

    /**
     * 生成托管的Fragment对象
     * @return Fragment
     */
    protected abstract Fragment createFragment();
}
