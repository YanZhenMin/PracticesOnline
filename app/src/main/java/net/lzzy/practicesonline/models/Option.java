package net.lzzy.practicesonline.models;

import net.lzzy.practicesonline.constants.ApiConstants;
import net.lzzy.sqllib.Ignored;
import net.lzzy.sqllib.Jsonable;
import net.lzzy.sqllib.Sqlitable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by lzzy_gxy on 2019/4/17.
 * Description:
 */
public class Option extends BaseEntity implements Sqlitable, Jsonable {
    @Ignored
    public static final String COL_QUESTION_ID="questionId";
    private String content;
    private String label;
    private boolean isAnswer;
    private int apiId;
    private UUID questionId;

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    @Override
    public boolean needUpdate() {
        return false;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return null;
    }

    @Override
    public void fromJson(JSONObject json) throws JSONException {
        content=json.getString(ApiConstants.JSON_OPTION_CONTENT);
        label=json.getString(ApiConstants.JSON_OPTION_LABEL);
        apiId=json.getInt(ApiConstants.JSON_OPTION_API_ID);
    }
}
