package com.uteq.software.app4

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.uteq.software.app4.Adapters.AlumnoAdapter
import com.uteq.software.app4.Models.Alumno
import com.uteq.software.app4.Models.Materia
import com.uteq.software.app4.services.SupabaseErrorHandler
import com.uteq.software.app4.services.SupabaseManager
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class MainActivityContenedoresImagen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_contenedores_imagen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val actvNiveles  = findViewById<AutoCompleteTextView>(R.id.actvListaNiveles)
        val actvMaterias = findViewById<AutoCompleteTextView>(R.id.actvListaMaterias)
        val lvAlumnos    = findViewById<ListView>(R.id.lvAlumnos)

        actvNiveles.setOnItemClickListener { _, _, position, _ ->
            actvMaterias.setText("")
            val lstMaterias = ArrayList<String>()
            lifecycleScope.launch {
                try {
                    val listaMaterias = ArrayList(
                        SupabaseManager.client
                            .from("materias")
                            .select {
                                filter { eq("nivel", position + 1) }
                                order("nombre", Order.ASCENDING)
                            }
                            .decodeList<Materia>()
                    )
                    for (materia in listaMaterias) {
                        lstMaterias.add(materia.nombre ?: "")
                    }
                } catch (e: RestException) {
                    SupabaseErrorHandler.show(this@MainActivityContenedoresImagen, e)
                    lstMaterias.clear()
                } finally {
                    val adapter = ArrayAdapter(
                        this@MainActivityContenedoresImagen,
                        android.R.layout.simple_spinner_dropdown_item,
                        lstMaterias
                    )
                    actvMaterias.setAdapter(adapter)
                }
            }
        }

        actvMaterias.setOnItemClickListener { _, _, _, _ ->
            var lstAlumnos = ArrayList<Alumno>()
            lifecycleScope.launch {
                try {
                    lstAlumnos = ArrayList(
                        SupabaseManager.client
                            .from("alumnos")
                            .select {
                                order("nombres", Order.ASCENDING)
                            }
                            .decodeList<Alumno>()
                    )
                } catch (e: RestException) {
                    SupabaseErrorHandler.show(this@MainActivityContenedoresImagen, e)
                } finally {
                    val adapter = AlumnoAdapter(this@MainActivityContenedoresImagen, lstAlumnos)
                    lvAlumnos.adapter = adapter
                }
            }
        }
    }
}