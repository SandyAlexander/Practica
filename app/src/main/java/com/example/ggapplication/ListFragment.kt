package com.example.ggapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class ListFragment : Fragment() {
    private lateinit var lvUsuarios: ListView
    private lateinit var conexionSql: ConexionSql

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conexionSql = ConexionSql(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        lvUsuarios = view.findViewById(R.id.lvUsuarios)
        mostrarUsuarios()
        return view
    }

    private fun mostrarUsuarios() {
        val usuarios = conexionSql.getAllUsuarios()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, usuarios)
        lvUsuarios.adapter = adapter
    }
}
