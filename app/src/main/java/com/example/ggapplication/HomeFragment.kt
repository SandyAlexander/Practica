package com.example.ggapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var rgSexo: RadioGroup
    private lateinit var btnAgregar: Button
    private lateinit var cbMoto: CheckBox
    private lateinit var cbCarro: CheckBox
    private lateinit var cbTriciclo: CheckBox
    private lateinit var spinnerEdad: Spinner
    private lateinit var tvListaUsuarios: TextView

    private lateinit var conexionSql: ConexionSql

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conexionSql = ConexionSql(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        etNombre = view.findViewById(R.id.etNombre)
        etApellido = view.findViewById(R.id.etApellido)
        rgSexo = view.findViewById(R.id.rgSexo)
        cbMoto = view.findViewById(R.id.cbMoto)
        cbCarro = view.findViewById(R.id.cbCarro)
        cbTriciclo = view.findViewById(R.id.cbTriciclo)
        spinnerEdad = view.findViewById(R.id.spinnerEdad)
        btnAgregar = view.findViewById(R.id.btnAgregar)
        tvListaUsuarios = view.findViewById(R.id.tvListaUsuarios)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.age_ranges,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEdad.adapter = adapter

        btnAgregar.setOnClickListener {
            agregarUsuario()
        }

        mostrarUsuarios()

        return view
    }

    private fun agregarUsuario() {
        val nombre = etNombre.text.toString()
        val apellido = etApellido.text.toString()
        val sexo = when (rgSexo.checkedRadioButtonId) {
            R.id.rbMasculino -> "Masculino"
            R.id.rbFemenino -> "Femenino"
            else -> ""
        }
        val transporte = mutableListOf<String>()
        if (cbMoto.isChecked) transporte.add("Moto")
        if (cbCarro.isChecked) transporte.add("Carro")
        if (cbTriciclo.isChecked) transporte.add("Triciclo")
        val rangoEdad = spinnerEdad.selectedItem.toString()

        if (nombre.isNotEmpty() && apellido.isNotEmpty() && sexo.isNotEmpty() && transporte.isNotEmpty() && rangoEdad.isNotEmpty()) {
            val transporteString = transporte.joinToString()
            conexionSql.insertUsuario(nombre, apellido, sexo, transporteString, rangoEdad)
            mostrarUsuarios()
            limpiarCampos()
        }
    }

    private fun mostrarUsuarios() {
        val usuarios = conexionSql.getAllUsuarios()
        tvListaUsuarios.text = usuarios.joinToString(separator = "\n")
    }

    private fun limpiarCampos() {
        etNombre.text.clear()
        etApellido.text.clear()
        rgSexo.clearCheck()
        cbMoto.isChecked = false
        cbCarro.isChecked = false
        cbTriciclo.isChecked = false
        spinnerEdad.setSelection(0)
    }
}
