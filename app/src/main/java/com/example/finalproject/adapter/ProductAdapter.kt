package com.example.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.Product

/**
 * ProductAdapter displays product items inside a RecyclerView.
 *
 * @property productList The list of products to display.
 * @property onItemClick Lambda function triggered when a product is clicked.
 */
class ProductAdapter(
    private val productList: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    /**
     * ViewHolder class holds references to the views of each product item.
     *
     * @param itemView The item layout view.
     */
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardProduct)
        val tvName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val tvDescription: TextView = itemView.findViewById(R.id.tvProductDescription)
    }

    /**
     * Creates a new ViewHolder for product items.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    /**
     * Binds product data to the views at the specified position.
     *
     * @param holder The ViewHolder for the current item.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.tvName.text = product.name
        holder.tvPrice.text = "Price: $%.2f".format(product.price)
        holder.tvDescription.text = product.description

        holder.cardView.setOnClickListener {
            onItemClick(product)
        }
    }

    /**
     * Returns the total number of items in the product list.
     *
     * @return Product list size.
     */
    override fun getItemCount(): Int = productList.size
}