package net.lzzy.practicesonline.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.Nullable;


import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.models.view.QuestionResult;
import net.lzzy.practicesonline.utils.AppUtils;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by lzzy_gxy on 2019/5/13.
 * Description:
 */
public class GridFragment extends BaseFragment {
    private static final String ARG_PRACTICES_ID = "argPracticesId";
    public static final String ARG_RESULT = "argResult";
    private String practicesId;
    private List<QuestionResult> results;
    GenericAdapter<QuestionResult> adapter;

    @Override
    protected void populate() {
        GridView gridView=find(R.id.fragment_grid_f);
        adapter= new GenericAdapter<QuestionResult>(AppUtils.getContext(), R.layout.grid_item, results) {
            @Override
            public void populate(ViewHolder viewHolder, QuestionResult result) {
                String a=results.lastIndexOf(result)+1+"";
                viewHolder.setTextView(R.id.grid_item_tv,a);
                Button btn=viewHolder.getView(R.id.grid_item_tv);
                if (result.isRight()){
                    btn.setBackgroundResource(R.drawable.grid_green);
                }else {
                    btn.setBackgroundResource(R.drawable.grid_red);
                }
            }

            @Override
            public boolean persistInsert(QuestionResult questionResult) {
                return false;
            }

            @Override
            public boolean persistDelete(QuestionResult questionResult) {
                return false;
            }
        };
        gridView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            practicesId=getArguments().getString(ARG_PRACTICES_ID);
            results=getArguments().getParcelableArrayList(ARG_RESULT);
        }
    }

    public static GridFragment newInstance(String practicesId, List<QuestionResult> results){
        GridFragment Fragment=new GridFragment();
        Bundle args=new Bundle();
        args.putString(ARG_PRACTICES_ID,practicesId);
        args.putParcelableArrayList(ARG_RESULT,(ArrayList<? extends Parcelable>) results);
        Fragment.setArguments(args);
        return Fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragments_grid;
    }

    @Override
    public void search(String kw) {

    }
}
