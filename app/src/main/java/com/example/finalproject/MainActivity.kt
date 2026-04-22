package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.adapter.ProductAdapter
import com.example.finalproject.model.Product

/**
 * W2026 MAD302-01 Android Development
 * Final Project
 *
 * Name: Nithin Amin
 * Student ID: A00194322
 * Date: April 2026
 *
 * Description:
 * MainActivity displays the list of products in a RecyclerView.
 * It allows users to add a new product and open product details.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddProduct: Button
    private lateinit var adapter: ProductAdapter

    private val productList = mutableListOf(
        Product(1, "Laptop", 999.99, "High performance laptop"),
        Product(2, "Phone", 699.99, "Latest Android smartphone"),
        Product(3, "Headphones", 149.99, "Wireless noise-cancelling headphones"),
        Product(4, "Keyboard", 59.99, "Mechanical gaming keyboard"),
        Product(5, "Mouse", 39.99, "Ergonomic wireless mouse"),
        Product(6, "Monitor", 249.99, "24-inch Full HD monitor")
    )

    private val addEditLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val returnedProduct = result.data?.getSerializableExtra("product") as? Product
                val isEdit = result.data?.getBooleanExtra("isEdit", false) ?: false

                returnedProduct?.let { product ->
                    if (isEdit) {
                        val index = productList.indexOfFirst { it.id == product.id }
                        if (index != -1) {
                            productList[index] = product
                        }
                    } else {
                        productList.add(product)
                    }
                    refreshRecyclerView()
                }
            }
        }

    private val detailLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val deleteId = result.data?.getIntExtra("deleteProductId", -1) ?: -1

                if (deleteId != -1) {
                    productList.removeAll { it.id == deleteId }
                    refreshRecyclerView()
                }
            }
        }

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState Previous saved state if available.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewProducts)
        btnAddProduct = findViewById(R.id.btnAddProduct)

        recyclerView.layoutManager = LinearLayoutManager(this)
        refreshRecyclerView()

        btnAddProduct.setOnClickListener {
            val intent = Intent(this, AddEditProductActivity::class.java)
            intent.putExtra("isEdit", false)
            addEditLauncher.launch(intent)
        }
    }

    /**
     * Refreshes the RecyclerView by creating a new adapter instance.
     */
    private fun refreshRecyclerView() {
        adapter = ProductAdapter(productList) { selectedProduct ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("product", selectedProduct)
            detailLauncher.launch(intent)
        }
        recyclerView.adapter = adapter
    }
}