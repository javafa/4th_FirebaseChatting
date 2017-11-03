package net.flow9.firebasechatting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.flow9.firebasechatting.model.User;
import net.flow9.firebasechatting.util.VerificationUtil;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference userRef;

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
        // 파이어베이스 모듈 사용하기
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        // 데이터베이스 user 레퍼런스 생성
        userRef = database.getReference("user");

        initView();
    }
    // 등록처리
    public void signup(View view){
        final String email = editEmail.getText().toString();
        final String password = editPassword.getText().toString();
        final String name = editName.getText().toString();
        final String phone = editPhone.getText().toString();
        final String birthday = editBirthday.getText().toString();
        int selectedId = gender.getCheckedRadioButtonId();
        final String gender = selectedId == R.id.radioMale ? "Male" : "Female";

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // 1. 정상등록시 안내 메일 발송
                        FirebaseUser fUser = auth.getCurrentUser();
                        fUser.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SignupActivity.this);
                                    dialogBuilder.setTitle("Notice");
                                    dialogBuilder.setMessage("이메일을 발송하였습니다. 확인해주세요!");
                                    dialogBuilder.setCancelable(false);
                                    dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            finish();
                                        }
                                    });
                                    AlertDialog dialog = dialogBuilder.create();
                                    dialog.show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //
                                }
                        });
                        // 2. 사용자 등록
                        User user = new User(fUser.getUid(), name, email, "", birthday, gender, phone);
                        userRef.child(fUser.getUid()).setValue(user);
                    }else{
                        Log.e("Auth","creation is not success");
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Auth",e.getMessage());
                }
            });
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
