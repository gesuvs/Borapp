package com.br.bora.app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_register_user.*
import kotlinx.android.synthetic.main.activity_register_user.action_bar

class RegisterUserActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1000;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        setSupportActionBar(action_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        action_bar.title = getString(R.string.register)
        action_bar.setTitleTextColor(resources.getColor(R.color.colorText))

        upload_pic.setImageResource(R.drawable.ic_cloud_upload);
        upload_pic.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(
                        android.Manifest.permission.CAMERA
                    )
                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    openCamera();
                }
            } else {
                openCamera();
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permissão Negada", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    fun openCamera() {
        ImagePicker.with(this)
            .crop(16f, 9f)    //Crop image with 16:9 aspect ratio
            .maxResultSize(520, 520)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data
            upload_pic.setImageURI(fileUri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Operação Cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    fun cadastrarUsuario(v: View) {
        if (validaCampos()) {
            startActivity(Intent(this, AuthActivity::class.java));
        }
    }

    fun validaCampos(): Boolean {
        if (mail_user.text.toString().isEmpty()) {
            mail_user.requestFocus();
            mail_user.error = getString(R.string.emailError)
            return false;
        }
        if (phone_user.text.toString().isEmpty()) {
            phone_user.requestFocus();
            phone_user.error = getString(R.string.celularError);
            return false;
        }
        if (username.text.toString().isEmpty()) {
            username.requestFocus();
            username.error = getString(R.string.loginError);
            return false;
        }
        return true;
    }
}
