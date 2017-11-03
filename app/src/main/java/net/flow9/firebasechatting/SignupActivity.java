package net.flow9.firebasechatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import net.flow9.firebasechatting.util.VerificationUtil;

public class SignupActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private EditText editPasswordRe;
    private EditText editName;
    private EditText editPhone;
    private EditText editBirthday;
    private Button btnSignup;
    private RadioGroup gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
    }
    // 등록처리
    public void signup(View view){
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String password_re = editPasswordRe.getText().toString();
        String name = editName.getText().toString();
        String phone = editPhone.getText().toString();
        String birthday = editBirthday.getText().toString();
        int selectedId = gender.getCheckedRadioButtonId();
        String gender = selectedId == R.id.radioMale ? "Male" : "Female";

        if(email == null || !VerificationUtil.isValidEmail(email)){

            return;
        }

        if(password == null || !VerificationUtil.isValidPassword(password)){

            return;
        }

        if(!password.equals(password_re)){

            return;
        }

        if(name == null || !VerificationUtil.isValidName(name)){

            return;
        }
    }

    boolean checkEmail = false;
    boolean checkPassword = false;
    boolean checkRepeat = false;
    boolean checkName = false;
    private void enableSignupButton(){
        if(checkEmail && checkPassword && checkRepeat && checkName){
            btnSignup.setEnabled(true);
        }else{
            btnSignup.setEnabled(false);
        }
    }

    private void initView() {
        editEmail = (EditText) findViewById(R.id.editEmail);
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEmail = VerificationUtil.isValidEmail(charSequence.toString());
                enableSignupButton();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editPassword = (EditText) findViewById(R.id.editPassword);
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPassword = VerificationUtil.isValidPassword(charSequence.toString());
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editPasswordRe = (EditText) findViewById(R.id.editPasswordRe);
        editPasswordRe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = editPassword.getText().toString();
                checkRepeat = password.equals(charSequence.toString());
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editName = (EditText) findViewById(R.id.editName);
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkName = VerificationUtil.isValidName(charSequence.toString());
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        editPhone = (EditText) findViewById(R.id.editPhone);
        editBirthday = (EditText) findViewById(R.id.editBirthday);
        gender = (RadioGroup) findViewById(R.id.gender);
        btnSignup = findViewById(R.id.btnSignup);
    }
}
