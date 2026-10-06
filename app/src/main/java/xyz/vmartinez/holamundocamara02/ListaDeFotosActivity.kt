package xyz.vmartinez.holamundocamara02

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListaDeFotosActivity : AppCompatActivity() {
    //Declaración de variables
    lateinit var textView: TextView
    lateinit var spinner: Spinner
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_de_fotos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Enlazar con la vista
        textView = findViewById<TextView>(R.id.textView2)
        spinner = findViewById<Spinner>(R.id.spinner)
        imageView = findViewById<ImageView>(R.id.imageView2)
        listarLasFotos()
    }

    private fun listarLasFotos() {
        val almacen = getExternalFilesDir(
            android.os.Environment.DIRECTORY_PICTURES
        )
        val lista = almacen?.listFiles{ file -> file.isFile && file.name.endsWith(".jpg")}
        var listaDeNombre = ArrayList<String>()
        //si se agrega la instrucción seleccione debemos de validar la posicion 0
        //listaDeNombre.add("Seleccione")
        for (indice in lista?.indices!!){
            val archivo = lista[indice]
            listaDeNombre.add(archivo.name)
        }

        var adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            listaDeNombre
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var archivo = lista[position]
                imageView.setImageURI(archivo.toUri())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}