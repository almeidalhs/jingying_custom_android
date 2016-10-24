package com.atman.jixin.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.TextView;

import com.atman.jixin.R;
import com.base.baselibs.widget.MyCleanEditText;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class EditTextDialog extends Dialog {

    private TextView edittextDialogContentTx;
    private MyCleanEditText edittextDialogNumEt;
    private TextView edittextDialogCancelTx;
    private TextView edittextDialogOkTx;

    private Context context;;
    private String str;
    private ETOnClick mETOnClick;

    public EditTextDialog(Context context, String str, ETOnClick mETOnClick) {
        super(context, R.style.edittextDialog);
        this.context = context;
        this.str = str;
        this.mETOnClick = mETOnClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_edittext_view);

        edittextDialogContentTx = (TextView) findViewById(R.id.edittext_dialog_content_tx);
        edittextDialogNumEt = (MyCleanEditText) findViewById(R.id.edittext_dialog_num_et);
        edittextDialogCancelTx = (TextView) findViewById(R.id.edittext_dialog_cancel_tx);
        edittextDialogOkTx = (TextView) findViewById(R.id.edittext_dialog_ok_tx);

        edittextDialogContentTx.setText(str);

        edittextDialogCancelTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mETOnClick.onItemClick(v, edittextDialogNumEt.getText().toString().trim());
            }
        });

        edittextDialogOkTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mETOnClick.onItemClick(v, edittextDialogNumEt.getText().toString().trim());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 触摸外部弹窗 */
        if (isOutOfBounds(getContext(), event)) {
            mETOnClick.onTouchOutside(edittextDialogNumEt);
        }
        return true;
    }

    private boolean isOutOfBounds(Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }

    public interface ETOnClick {
        void onItemClick(View view, String str);

        void onTouchOutside(EditText edittextDialogEt);
    }
}
