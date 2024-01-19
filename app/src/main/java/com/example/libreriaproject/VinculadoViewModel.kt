package com.example.libreriaproject

import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class VinculadoViewModel : ViewModel() {
    private var new_item: Boolean = false
    private var _libros: MutableLiveData<MutableList<Libro>> = MutableLiveData(mutableListOf())

    val libros: LiveData<MutableList<Libro>> get() = _libros
    private var _selected = MutableLiveData<Libro>(Libro("", "", "", "", false))
    var selected = MutableLiveData<Libro>(Libro("", "", "", "", false))

    val actionbutton: LiveData<String> = selected.map { libro ->
        when (libro.titulo) {
            "" -> "Nuevo Libro"
            else -> "Modificar"
        }
    }

    init {
        this._libros.value?.add(
            Libro(
                "Pocoyo",
                "Pepe",
                "350",
                "Inditex",
                true
            )
        )
        this._libros.value?.add(
            Libro(
                "El camino de los reyes",
                "Brandon Sanderson",
                "1200",
                "DeBolsillo",
                true
            )
        )
        this._libros.value?.add(
            Libro(
                "Alonso Leyenda",
                "Fernando Alonso",
                "333",
                "Alonsistas",
                true
            )
        )
    }

    private fun updateList() {
        var values = this._libros.value
        this._libros.value = values
    }

    private fun updateItem() {
        this._selected.value = this._selected.value?.copy()
    }

    fun settSelected(item: Libro) {
        this._selected.value = item
        this.selected.value = item.copy()
        this.new_item = false
    }

    fun settSelected(index: Int) {
        this._libros.value?.let {
            this._selected.value = it.get(index)
            this.selected.value = it.get(index).copy()
            this.new_item = false
        }
    }

    fun create_new() {
        this._selected.value = Libro("", "", "", "", false)
        this.selected.value = this._selected.value
        this.new_item = true
    }

    fun update() {
        if (new_item) {
            this.new_item = false
            this.selected.value?.let {
                this._libros.value?.add(it)
                this.updateList()
            }
        } else {
            this._selected.value?.let {
                it.activo = selected.value?.let { it.activo } ?: it.activo
                it.titulo = selected.value?.let { it.titulo } ?: it.titulo
                it.autor = selected.value?.let { it.autor } ?: it.autor
                it.paginas = selected.value?.let { it.paginas } ?: it.paginas
                it.editorial = selected.value?.let { it.editorial } ?: it.editorial

                this.updateList()
                this.updateItem()
            }
        }
    }

    fun remove(p: Libro) {
        if (!this.new_item)
            this._libros.value
        this._libros.value?.toMutableList().apply { remove(p) }
    }
}