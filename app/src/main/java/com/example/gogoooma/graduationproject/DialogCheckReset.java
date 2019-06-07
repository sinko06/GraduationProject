package com.example.gogoooma.graduationproject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class DialogCheckReset {
    Context context;
    Button yes, no;

    public DialogCheckReset(Context context) {
        this.context = context;
    }

    public void callFunction(){
        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.activity_dialog_check_reset);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        yes = (Button) dlg.findViewById(R.id.setting_reset_yes);
        no = (Button) dlg.findViewById(R.id.setting_reset_no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File cache = context.getCacheDir();
                File appDir = new File(cache.getParent());
                if (appDir.exists()) {
                    String[] children = appDir.list();
                    for (String s : children) {
                        if (!s.equals("lib") && !s.equals("files")) {
                            deleteDir(new File(appDir, s));
                        }
                    }
                }
                Toast.makeText(context, "데이터 삭제 완료", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소했습니다", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });

        dlg.show();
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


}
