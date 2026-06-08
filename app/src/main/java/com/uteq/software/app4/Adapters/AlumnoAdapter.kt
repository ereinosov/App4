package com.uteq.software.app4.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.uteq.software.app4.Models.Alumno
import com.uteq.software.app4.R

class AlumnoAdapter(context: Context, var alumnos: ArrayList<Alumno>) : ArrayAdapter<Alumno>(
    context,
    R.layout.item_alumno,
    alumnos
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.item_alumno, parent, false)

        val alumno = alumnos[position]

        val txtNombre   = view.findViewById<TextView>(R.id.txtNombre)
        val txtCorreo   = view.findViewById<TextView>(R.id.txtCorreo)
        val txtTelefono = view.findViewById<TextView>(R.id.txtTelefono)
        val imgAlumno   = view.findViewById<ImageView>(R.id.imgAlumno)

        txtNombre.text   = alumno.nombres
        txtCorreo.text   = alumno.correo
        txtTelefono.text = alumno.telefono

        Glide.with(context)
            .load("https://sga.uteq.edu.ec" + alumno.foto)
            .circleCrop()
            .placeholder(android.R.drawable.ic_menu_myplaces)
            .error(android.R.drawable.ic_menu_myplaces)
            .into(imgAlumno)

        return view
    }
}