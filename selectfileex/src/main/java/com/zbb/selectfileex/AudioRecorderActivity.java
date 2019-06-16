package com.zbb.selectfileex;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.zbb.selectfileex.utils.PermissionUtils;
import com.zbb.selectfileex.utils.Utils;
import com.zbb.selectfileex.utils.VoiceUtils;
import com.zbb.selectfileex.view.DialogPromptPermission;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


public class AudioRecorderActivity extends AppCompatActivity {

    private static final String TAG = "AudioRecorderActivity";

    private RelativeLayout rlBack;
    private TextView tvToolbarText;
    private RelativeLayout rlSelectItem;
    private Toolbar toolbar;
    private TextView tvTime;
    private ImageView ivVoiceRecorder;
    private TextView tvIsRecorder;
    private LinearLayout llAudioRecorder;
    private RelativeLayout activityAudioEncoder;

    //录音权限
    private String[] permission_voice = new String[]{android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final int REQUEST_CODE_VOICE = 105;// 是调用录音机
    private Timer timer;
    private TimerTask task;
    private VoiceUtils voiceUtils;
    private int isRecorder = 0;//0没有录音，1正在录音
    int i = 0;//计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);
        initUI();
        setSupportActionBar(toolbar);
        tvToolbarText.setText(getResources().getString(R.string.audio_recorder));
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (PermissionUtils.checkSelfPermission(this, permission_voice, REQUEST_CODE_VOICE)) {
            init();
        }
    }

    private void init(){
        voiceUtils = new VoiceUtils();
        llAudioRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecorder == 0) {
                    isRecorder = 1;
                    tvIsRecorder.setText(getResources().getString(R.string.finish));
                    ivVoiceRecorder.setImageResource(R.mipmap.luyin_finish);
                    voiceUtils.startRecorder();
                    startTask();
                } else {
                    isRecorder = 0;
                    tvIsRecorder.setText(getResources().getString(R.string.audio_recorder));
                    ivVoiceRecorder.setImageResource(R.mipmap.luyin_kaishi);
                    stopTask();
                    File fileVoice = voiceUtils.stopRecorder();
                    Intent intent = new Intent();
                    intent.putExtra("voice", fileVoice);
                    setResult(Activity.RESULT_OK, intent);
                    AudioRecorderActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        voiceUtils.stopRecorder();
        super.onBackPressed();
    }

    private void startTask() {
        i = 0;
        timer = new Timer();
        task = new Task();
        timer.schedule(task, 1000, 1000);// 开始3秒后执行，之后每隔4秒执行一次
    }

    private void stopTask() {
        timer.cancel();
    }

    public <T extends View> T $(@IdRes int resId) {
        return (T) super.findViewById(resId);
    }

    private void initUI() {
        rlBack = $(R.id.rl_back);
        tvToolbarText = $(R.id.tv_toolbarText);
        rlSelectItem = $(R.id.rl_selectItem);
        toolbar = $(R.id.toolbar);
        tvTime = $(R.id.tv_time);
        ivVoiceRecorder = $(R.id.iv_voiceRecorder);
        tvIsRecorder = $(R.id.tv_isRecorder);
        llAudioRecorder = $(R.id.ll_audioRecorder);
        activityAudioEncoder = $(R.id.activity_audio_encoder);
    }

    class Task extends TimerTask {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {
                public void run() {
                    i++;
                    tvTime.setText(Utils.getTimeFormatByS(i));
                }
            });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_VOICE&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            //用户授权拒绝之后，友情提示一下就可以了
            Log.e(TAG, "权限被拒绝");
            //这里应该弹出dialog详细说明一下
//            Toast.makeText(this, "您拒绝了所需录音权限的申请，将不能进行操作，请在设置或安全中心开启该项权限后重新操作", Toast.LENGTH_LONG).show();
            DialogPromptPermission dialogPromptPermission= new DialogPromptPermission(this);
            dialogPromptPermission.setPromptText("您拒绝了录音所需权限的申请，将不能进行录音，请在设置或安全中心开启录音和读写手机储存的权限后重新操作");
            dialogPromptPermission.show();
        }
    }
}
