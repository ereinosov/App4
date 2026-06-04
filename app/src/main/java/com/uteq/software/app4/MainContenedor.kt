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
import com.uteq.software.app4.Models.Alumno
import com.uteq.software.app4.Models.Materia
import com.uteq.software.app4.services.SupabaseErrorHandler
import com.uteq.software.app4.services.SupabaseManager
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class MainContenedor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_contenedor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val actvMaterias = findViewById<AutoCompleteTextView>(R.id.actvListaMaterias)
        actvMaterias.setText("")
        val lstMaterias = ArrayList<String>()
        lifecycleScope.launch {
            try {
                val listaMaterias = ArrayList(
                    SupabaseManager.client
                        .from("materias")
                        .select {
                            filter {
                                eq("nivel", 6)
                            }
                            order("nombre", Order.ASCENDING)
                        }
                        .decodeList<Materia>()
                )
                for (materia in listaMaterias) {
                    lstMaterias.add(materia.nombre ?: "")
                }
            }
            catch (e: RestException) {
                SupabaseErrorHandler.show(this@MainContenedor, e)
                lstMaterias.clear()
            } finally {
                val adapter = ArrayAdapter(
                    this@MainContenedor,
                    android.R.layout.simple_spinner_dropdown_item,
                    lstMaterias
                )
                actvMaterias.setAdapter(adapter)
//
//                val listV = findViewById<ListView>(R.id.lvMaterias)
//                val adapter2 = ArrayAdapter(
//                    this@MainContenedor,
//                    android.R.layout.simple_spinner_dropdown_item,
//                    lstMaterias
//                )
//                listV.setAdapter(adapter)
            }
        }
        val lstAlumnos = ArrayList<String>()

        lifecycleScope.launch {
            try {
                val listaAlumnos = ArrayList(
                    SupabaseManager.client
                        .from("alumnos")
                        .select {
                            order("nombres", Order.ASCENDING)
                        }
                        .decodeList<Alumno>()
                )
                for (alumno in listaAlumnos) {
                    lstAlumnos.add(alumno.nombres ?: "")
                }

            }
            catch (e: RestException) {
                SupabaseErrorHandler.show(this@MainContenedor, e)
                lstAlumnos.clear()
            } finally {

                val listV = findViewById<ListView>(R.id.lvMaterias)
                val adapter2 = ArrayAdapter(
                    this@MainContenedor,
                    android.R.layout.simple_spinner_dropdown_item,
                    lstAlumnos
                )
                listV.setAdapter(adapter2)
            }
        }
    }
}