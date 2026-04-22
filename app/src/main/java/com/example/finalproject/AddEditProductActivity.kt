package com.example.finalproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.model.Product
import android.content.Intent

/**
 * AddEditProductActivity allows users to add a new product
 * or edit an existing product.
 */
class AddEditProductActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSave: Button

    private var isEdit = false
    private var existingProduct: Product? = null

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState Previous saved state if available.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_product)

        etName = findViewById(R.id.etProductName)
        etPrice = findViewById(R.id.etProductPrice)
        etDescription = findViewById(R.id.etProductDescription)
        btnSave = findViewById(R.id.btnSaveProduct)

        isEdit = intent.getBooleanExtra("isEdit", false)

        if (isEdit) {
            existingProduct = intent.getSerializableExtra("product") as? Product
            existingProduct?.let {
                etName.setText(it.name)
                etPrice.setText(it.price.toString())
                etDescription.setText(it.description)
            }
        }

        btnSave.setOnClickListener {
            saveProduct()
        }
    }

    /**
     * Validates user input and returns the product result to previous activity.
     */
    private fun saveProduct() {
        val name = etName.text.toString().trim()
        val priceText = etPrice.text.toString().trim()
        val description = etDescription.text.toString().trim()

        if (name.isEmpty() || priceText.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceText.toDoubleOrNull()
        if (price == null) {
            Toast.makeText(this, "Enter a valid price", Toast.LENGTH_SHORT).show()
            return
        }

        val product = if (isEdit && existingProduct != null) {
            Product(existingProduct!!.id, name, price, description)
        } else {
            Product((1000..9999).random(), name, price, description)
        }

        val intent = Intent()
        intent.putExtra("product", product)
        intent.putExtra("isEdit", isEdit)
        setResult(RESULT_OK, intent)
        finish()
    }
}