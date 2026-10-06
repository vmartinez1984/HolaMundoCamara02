package xyz.vmartinez.holamundocamara02

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    var uri : Uri?  = null;

    //Evento que da acceso a la camara y obtiene la foto previa
    private val tomarFotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){
        isSuccess ->
        if(isSuccess){
            uri?.let{
                uri -> imageView.setImageURI(uri)
            }
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById<ImageView>(R.id.imageView)
    }

    fun crearArchivo(): File?{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        var almacen = getExternalFilesDir(
            android.os.Environment.DIRECTORY_PICTURES
        )

        return try {
            File.createTempFile("${timeStamp}_",".jpg", almacen)
        }catch (e: Exception){
            Toast.makeText(this, "Error al crear archivo",Toast.LENGTH_SHORT).show()
            null
        }
    }

    fun tomarFoto(view: View){
        val archivo = crearArchivo()
        archivo?.let { file ->
            var uri = FileProvider.getUriForFile(this,
                "${applicationContext.packageName}.fileprovider",
                file
            )
            this.uri = uri
            tomarFotoLauncher.launch(uri)
        }
    }

    fun irALaListaDeFotos(view: View){
        val intent = Intent(this, ListaDeFotosActivity::class.java)
        startActivity(intent)
    }
}