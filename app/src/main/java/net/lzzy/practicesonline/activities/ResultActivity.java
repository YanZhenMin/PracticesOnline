package net.lzzy.practicesonline.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.fragments.GridFragment;
import net.lzzy.practicesonline.models.view.QuestionResult;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/5/13.
 * Description:
 */
public class ResultActivity extends BaseActivity {
    private List<QuestionResult> results;
    private String practiceId;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        practiceId=getIntent().getStringExtra(QuestionActivity.EXTRA_PRACTICE_ID);
    }

    @Override
    protected int getContainerId() {
        return R.id.activity_result_container;
    }

    @Override
    protected Fragment createFragment() {
        results=getIntent().getParcelableArrayListExtra(QuestionActivity.EXTRA_RESULT);
        return GridFragment.newInstance(practiceId,results);
    }
}