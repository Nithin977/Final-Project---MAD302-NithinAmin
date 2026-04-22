package com.example.finalproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.model.Product

/**
 * DetailActivity shows full information about a selected product.
 * It also allows the user to edit or delete the product.
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var product: Product

    private lateinit var tvName: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    private val editLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedProduct = result.data?.getSerializableExtra("product") as? Product
                updatedProduct?.let {
                    val intent = Intent()
                    intent.putExtra("product", it)
                    intent.putExtra("isEdit", true)
                    setResult(RESULT_OK, intent)
                    finish()
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
        setContentView(R.layout.activity_detail)

        tvName = findViewById(R.id.tvDetailName)
        tvPrice = findViewById(R.id.tvDetailPrice)
        tvDescription = findViewById(R.id.tvDetailDescription)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

        product = intent.getSerializableExtra("product") as Product

        tvName.text = product.name
        tvPrice.text = "Price: $%.2f".format(product.price)
        tvDescription.text = product.description

        btnEdit.setOnClickListener {
            val intent = Intent(this, AddEditProductActivity::class.java)
            intent.putExtra("isEdit", true)
            intent.putExtra("product", product)
            editLauncher.launch(intent)
        }

        btnDelete.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    /**
     * Displays a confirmation dialog before deleting the product.
     */
    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Delete Product")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent()
                intent.putExtra("deleteProductId", product.id)
                setResult(RESULT_OK, intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }
}