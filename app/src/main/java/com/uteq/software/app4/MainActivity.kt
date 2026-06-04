package com.uteq.software.app4

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.uteq.software.app4.Models.Alumno
import com.uteq.software.app4.services.SupabaseManager
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtAlumnos = findViewById<EditText>(R.id.txtListado)
        val progressMaterias =
            findViewById<CircularProgressIndicator>(R.id.progressMaterias)

        lifecycleScope.launch {
            progressMaterias.visibility = View.VISIBLE
            try {
                val alumnos = SupabaseManager.client
                    .from("alumnos")
                    .select {
                        order("nombres", Order.ASCENDING)
                    }
                    .decodeList<Alumno>()

                var texto = ""
                for (alumno in alumnos) {
                    texto += "Nombres: " + alumno.nombres + "\n"
                    texto += "Correo: " + alumno.correo + "\n"
                    texto += "Teléfono: " + alumno.telefono + "\n"
                    texto += "Paralelo: " + alumno.paralelo + "\n\n"
                }

                txtAlumnos.setText(texto)

            } catch (e: RestException) {
                //SupabaseErrorHandler.show(this@MainActivity,e)
                txtAlumnos.setText(e.description)

            } finally {
                progressMaterias.visibility = View.INVISIBLE
            }
        }
    }
}