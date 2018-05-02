package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Bank;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class AddBankCardActivity extends BaseActivity {
    public static final String TAG = "AddBankCardActivity";
    @InjectView(R.id.common_back)
    private ImageView imgBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.add_card_next)
    private TextView tvNext;
    @InjectView(R.id.card_number)
    private EditText etNumber;
    @InjectView(R.id.card_name)
    private EditText etName;
    @InjectView(R.id.root_view)
    private View rootView;
    private Bank selectBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        Injector.get(this).inject();
        tvTitle.setText(getString(R.string.add_card));

        selectBank = (Bank) getIntent().getSerializableExtra("bank");
        initView();
    }

    private void initView() {
        imgBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        rootView.setOnClickListener(this);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkState();
            }
        });
        etNumber.addTextChangedListener(new TextWatcher() {

            private int oldLength = 0;
            private boolean isChange = true;
            private int curLength = 0;
            private int emptyNumB = 0;
            private int emptyNumA = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                oldLength = s.length();
                emptyNumB = 0;
                for (int i = 0; i < s.toString().length(); i++) {
                    if (s.charAt(i) == ' ') emptyNumB++;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                curLength = s.length();
                isChange = !(curLength == oldLength || curLength <= 3);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChange) {
                    int selectIndex = etNumber.getSelectionEnd();//获取光标位置
                    String content = s.toString().replaceAll(" ", "");
                    StringBuilder sb = new StringBuilder(content);
                    //遍历加空格
                    int index = 1;
                    emptyNumA = 0;
                    for (int i = 0; i < content.length(); i++) {
                        if ((i + 1) % 4 == 0) {
                            sb.insert(i + index, " ");
                            index++;
                            emptyNumA++;
                        }
                    }
                    String result = sb.toString();
                    if (result.endsWith(" ")) {
                        result = result.substring(0, result.length() - 1);
                    }
                    etNumber.setText(result);
                    if (emptyNumA > emptyNumB)
                        selectIndex = selectIndex + (emptyNumA - emptyNumB);
                    //处理光标位置
                    if (selectIndex > result.length() || selectIndex + 1 == result.length()) {
                        selectIndex = result.length();
                    } else if (selectIndex < 0) {
                        selectIndex = 0;
                    }
                    etNumber.setSelection(selectIndex);
                    isChange = false;
                }
                checkState();
            }
        });

        CommonUtils.showKeyBoard(etNumber);
    }

    public void checkState() {
        String str = etNumber.getText().toString().trim();
        String name = etName.getText().toString().trim();
        str = str.replace(" ", "");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(name) || str.length() < 16 || str.length() > 19) {
            tvNext.setEnabled(false);
        } else {
            tvNext.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_card_next:
                String str = etNumber.getText().toString().trim();
                str = str.replace(" ", "");
                Intent intent = new Intent(this, WithdrawWayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("cardNumber", str);
                intent.putExtra("userName", etName.getText().toString().trim());
                intent.putExtra("bank", selectBank);
                startActivity(intent);
                break;
            case R.id.root_view:
                //关闭输入框
                CommonUtils.hideKeyBoard2(etNumber);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard2(etNumber);
        return super.onTouchEvent(event);
    }
}
