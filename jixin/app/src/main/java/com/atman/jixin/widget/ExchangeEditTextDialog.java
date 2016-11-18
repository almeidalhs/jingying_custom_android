package com.atman.jixin.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
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

public class ExchangeEditTextDialog extends Dialog {

    private MyCleanEditText exchangeEdittextDialogLocationEt;
    private MyCleanEditText exchangeEdittextDialogNumEt;
    private TextView exchangeEdittextDialogCancelTx;
    private TextView exchangeEdittextDialogOkTx;

    private Context context;;
    private String content;
    private String location;
    private ETOnClick mETOnClick;

    public ExchangeEditTextDialog(Context context, String content, String location, ETOnClick mETOnClick) {
        super(context, R.style.edittextDialog);
        this.context = context;
        this.content = content;
        this.location = location;
        this.mETOnClick = mETOnClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_exchangeedittext_view);

        exchangeEdittextDialogNumEt = (MyCleanEditText) findViewById(R.id.exchangeedittext_dialog_num_et);
        exchangeEdittextDialogLocationEt = (MyCleanEditText) findViewById(R.id.exchangeedittext_dialog_location_et);
        exchangeEdittextDialogCancelTx = (TextView) findViewById(R.id.exchangeedittext_dialog_cancel_tx);
        exchangeEdittextDialogOkTx = (TextView) findViewById(R.id.exchangeedittext_dialog_ok_tx);

        exchangeEdittextDialogNumEt.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (!content.isEmpty()) {
            exchangeEdittextDialogNumEt.setHint(content);
        }

        if (!location.isEmpty()) {
            exchangeEdittextDialogLocationEt.setText(location);
        }

        if (!exchangeEdittextDialogNumEt.getText().toString().isEmpty()) {
            exchangeEdittextDialogNumEt.setSelection(exchangeEdittextDialogNumEt.getText().toString().length());
        }

        if (!exchangeEdittextDialogLocationEt.getText().toString().isEmpty()) {
            exchangeEdittextDialogLocationEt.setSelection(exchangeEdittextDialogLocationEt.getText().toString().length());
        }

        exchangeEdittextDialogCancelTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mETOnClick.onItemClick(v, exchangeEdittextDialogNumEt.getText().toString().trim()
                        , exchangeEdittextDialogLocationEt.getText().toString().trim());
            }
        });

        exchangeEdittextDialogOkTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mETOnClick.onItemClick(v, exchangeEdittextDialogNumEt.getText().toString().trim()
                        , exchangeEdittextDialogLocationEt.getText().toString().trim());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 触摸外部弹窗 */
        if (isOutOfBounds(getContext(), event)) {
            mETOnClick.onTouchOutside(exchangeEdittextDialogNumEt);
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
        void onItemClick(View view, String str1, String str2);

        void onTouchOutside(EditText edittextDialogEt);
    }
}
