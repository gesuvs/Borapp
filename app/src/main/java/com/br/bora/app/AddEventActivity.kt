package com.br.bora.app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.br.bora.app.model.Event
import com.br.bora.app.model.viewmodel.EventoViewModel
import com.br.bora.app.request.CreateEvent
import com.br.bora.app.services.config.RetrofitInitializer
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.radiobutton.MaterialRadioButton
import kotlinx.android.synthetic.main.activity_add_event.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEventActivity : AppCompatActivity() {


    private val PERMISSION_CODE = 1000;
    var isPublic = true;
    var isPago = false;
    var preferencias: SharedPreferences? = null;
    var isAlterar = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setSupportActionBar(action_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        action_bar.title = getString(R.string.add_event)
        action_bar.setTitleTextColor(resources.getColor(R.color.colorText))

        type_private.setOnClickListener {
            type_public.isChecked = false
            type_private.isChecked = true
            label_password_event.isVisible = true
            password_event.isVisible = true
        }

        type_public.setOnClickListener {
            type_private.isChecked = false
            type_public.isChecked = true
            label_password_event.isVisible = false
            password_event.isVisible = false
        }

        event_isFree.setOnCheckedChangeListener { _, isChecked ->
            price_event.isVisible = !isChecked
        }

        btn_create_event.setOnClickListener {
            val titleInput = findViewById<EditText>(R.id.title_event).text.toString()
            val cepInput = findViewById<EditText>(R.id.cep_event).text.toString()
            val numberStreetInput = findViewById<EditText>(R.id.number_street_event).text.toString()
            val dateStartInput = findViewById<EditText>(R.id.date_event_start).text.toString()
            val scheduleStartInput =
                findViewById<EditText>(R.id.schedule_start_event).text.toString()
            val dateEndInput = findViewById<EditText>(R.id.date_event_end).text.toString()
            val scheduleEndInput = findViewById<EditText>(R.id.schedule_end_event).text.toString()
            val typePublicInput = findViewById<MaterialRadioButton>(R.id.type_public).isChecked
            val typePrivateInput = findViewById<MaterialRadioButton>(R.id.type_private).isChecked
            val passwordInput = findViewById<EditText>(R.id.password_event).text.toString()
            val isFreeInput = findViewById<MaterialCheckBox>(R.id.event_isFree).text.toString()
            val priceInput = findViewById<EditText>(R.id.price_event).text.toString()
            val event = CreateEvent(
                Event.Create(
                    titleInput,
                    "gesuvs",
                    dateStartInput,
                    dateEndInput,
                    0.0,
                    null,
                    passwordInput,
                    typePrivateInput
                )
            )
            createEvent(event, it)
        }


        //evento_etCep.setText("02042010");
        /* val idEvento = intent.extras?.getInt("idEvento", 0)
         if (idEvento != 0) {
             getEvento(idEvento);
             isAlterar = true;
         }*/
    }

    private fun createEvent(event: CreateEvent, v: View) {
        EventoViewModel().createEvent(event, v)
    }


    fun visibleValor(v: View) {
        /*  if (evento_swIsPago.isChecked) {
              evento_tvLabelValor.visibility = View.VISIBLE;
              evento_etValor.visibility = View.VISIBLE;
              isPago = true;
          } else {
              evento_tvLabelValor.visibility = View.GONE;
              evento_etValor.visibility = View.GONE;
              isPago = true;
          }*/
    }

    fun preencheCamposEvento(event: Event?) {
//        evento_etNomeEvento.setText(event?.name)
        //evento_etCep.setText(evento.cep)
        //evento_etNumero.setText(evento.number);
        //evento_etQuantidade.setText(evento.quantity)
        //evento_etValor.setText(evento.valor);
        //evento_etData.setText(evento.startDay.toString().substring(0, 9))
        //evento_etHorario.setText(evento.startDay.toString().substring(11, 15))


//        isPublic = event!!.isPublic;
//        if (event!!.isPublic)
//            evento_btnPublic.callOnClick()
//        else
//            evento_btnPrivate.callOnClick()
    }

    fun openCamera() {
        ImagePicker.with(this)
            //.crop(16f, 9f)    //Crop image with 16:9 aspect ratio
            .maxResultSize(1024, 1024)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data
            icon_upload_image.setImageURI(fileUri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Operação Cancelada", Toast.LENGTH_SHORT).show()
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

    fun inializaTela() {
        /*
        evento_swIsPago.setOnCheckedChangeListener { componente, ligado ->
            visibleValor(componente)
        }
        evento_btnPublic.setOnClickListener {
            evento_btnPublic.setBackgroundColor(getColor(R.color.colorAccent))
            evento_btnPrivate.setBackgroundColor(getColor(R.color.colorPrimary))
            evento_etSenha.visibility = (View.GONE)
            evento_tvLabelSenha.visibility = (View.GONE)
            isPublic = true
        }
        evento_btnPrivate.setOnClickListener {
            evento_btnPublic.setBackgroundColor(getColor(R.color.colorAccent))
            evento_btnPrivate.setBackgroundColor(getColor(R.color.colorPrimary))
            evento_etSenha.visibility = (View.VISIBLE)
            evento_tvLabelSenha.visibility = (View.VISIBLE)
            isPublic = false
        }
        icon_upload_image.setOnClickListener {
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
        evento_btCadastrar.setOnClickListener {
            if (validacoes()) {
                if (isAlterar) {
                    //Pegar todos os campos e transformar em um obj de evento
                    //alterarEvento()
                } else {
                    //Pegar todos os campos e transformar em um obj de evento
                    //cadastrarEvento();
                }
            }
        }
        evento_btBuscaEndereco.setOnClickListener {
            if (!evento_etCep.text.toString().isEmpty()) {
                buscaEndereco(evento_etCep.text.toString())
            } else {
                evento_etCep.requestFocus();
                evento_etCep.error = getString(R.string.preencherCep);
            }
        }
    }*/
    }

    fun validacoes(): Boolean {
        /*
    if (evento_etNomeEvento.text.isEmpty()) {
        evento_etNomeEvento.requestFocus();
        evento_etNomeEvento.error = getString(R.string.campoObrigatorio);
        return false;
    }
    if (evento_etCep.text!!.isEmpty()) {
        evento_etCep.requestFocus();
        evento_etCep.error = getString(R.string.campoObrigatorio);
        return false;
    }
    if (evento_etCep.text!!.length < 8) {
        evento_etCep.requestFocus();
        evento_etCep.error = getString(R.string.preenchecep);
        return false;
    }
    if (evento_etNumero.text.isEmpty()) {
        evento_etNumero.requestFocus();
        evento_etNumero.error = getString(R.string.campoObrigatorio);
        return false;
    }
    if (evento_etDataInicio.text.toString().isEmpty()) {
        evento_etDataInicio.requestFocus();
        evento_etDataInicio.error = getString(R.string.campoObrigatorio);
        return false;
    }
    if (evento_etHorarioTermino.text.toString().isEmpty()) {
        evento_etHorarioTermino.requestFocus();
        evento_etHorarioTermino.error = getString(R.string.campoObrigatorio);
        return false;
    }
    if (evento_etDataTermino.text.toString().isEmpty()) {
        evento_etDataTermino.requestFocus();
        evento_etDataTermino.error = getString(R.string.campoObrigatorio);
        return false;
    }
    if (evento_etHorarioInicio.text.toString().isEmpty()) {
        evento_etHorarioInicio.requestFocus();
        evento_etHorarioInicio.error = getString(R.string.campoObrigatorio);
        return false;
    }
    if (isPago) {
        evento_etValor.requestFocus();
        evento_etValor.error = getString(R.string.campoObrigatorio);
        return false;
    }
    if (!isPublic) {
        evento_etSenha.requestFocus();
        evento_etSenha.error = getString(R.string.campoObrigatorio);
        return false;
    }
    */

        return true;
    }


    fun buscaEndereco(cep: String) {
//        val retIn = RetrofitInitializer.eventService getRetrofitInstance().create(CepService::class.java)
//        retIn.getEndereco(cep.replace("-", "")).enqueue(object : Callback<Cep> {
//            override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
//                if (response.code() == 200) {
//                    if (response.body()?.logradouro != null) {
//                        evento_tvEndereco.text =
//                            response.body()?.logradouro + " - " + response.body()?.bairro + " - " + response.body()?.localidade
//                        evento_tvLabelEndereco.visibility = (View.VISIBLE);
//                        evento_tvEndereco.visibility = (View.VISIBLE);
//                        evento_tvLabelNumero.visibility = (View.VISIBLE);
//                        evento_etNumero.visibility = (View.VISIBLE);
//                    } else {
//                        evento_etCep.requestFocus();
//                        evento_etCep.error = getString(R.string.enderecoNaoEncontra);
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<Cep>, t: Throwable) {
//                Log.i("STATE", t.message.toString())
//            }
//        })
    }

    fun alterarEvento(event: Event) {
        val retIn = RetrofitInitializer.eventService.changeEvento(event)

        retIn.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i("STATE", t.message.toString())
            }
        })
    }

    fun getEvento(idEvento: Int?) {
        val retIn = RetrofitInitializer.eventService.getEvent(idEvento)

        retIn.enqueue(object : Callback<Event?> {
            override fun onResponse(call: Call<Event?>, response: Response<Event?>) {
                if (response.code() == 200) {
                    preencheCamposEvento(response.body());
                }
            }

            override fun onFailure(call: Call<Event?>, t: Throwable) {
                Log.i("STATE", t.message.toString())
            }
        })
    }

}
