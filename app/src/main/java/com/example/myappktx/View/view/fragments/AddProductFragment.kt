package com.example.myappktx.View.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ProductModel
import com.example.myappktx.R
import com.example.myappktx.ViewModels.MyViewModel
import kotlinx.android.synthetic.main.fragment_add_to_base.*

class AddProductFragment : Fragment() {

    private lateinit var viewModel: MyViewModel
    private var itemSelected:String = ""
    private lateinit var array: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_to_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                itemSelected = array[position]
            }

        }
        button_create_Order.setOnClickListener {
            viewModel.addNewProduct(onClickAdd())
            onClickAdd()
        }
    }

    private fun initialization() {
        viewModel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        array = arrayListOf()
    }

    private fun subscribe(){
        viewModel
                .getListAllSubCategory()
                .observe(viewLifecycleOwner, Observer {
            it.forEach { array.add(it.productCategory.toString()) }

            spinner.adapter = ArrayAdapter<String>(view!!.context, android.R.layout.simple_list_item_1, array)

        })
    }

    private fun onClickAdd(): ProductModel {
        var newProduct = ProductModel()
        newProduct.name = userName_creteOrder.text.toString()
        newProduct.picture = editPicture.text.toString()
        newProduct.fullName = address_creteOrder.text.toString()
        newProduct.description = comment_creteOrder.text.toString()
        newProduct.price = tel_creteOrder.text.toString().toDouble()
        newProduct.category = itemSelected
        Toast.makeText(context, newProduct.toString(), Toast.LENGTH_LONG).show()
        return newProduct
        }



}