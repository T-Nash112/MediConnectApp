package com.example.mediconnectapk


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class TreatmentAdapter(private val mContext: Context, private val mUploads: List<Upload>) : RecyclerView.Adapter<TreatmentAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.treatment_items, parent, false)
        return ImageViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uploadCurrent = mUploads[position]
        holder.txtViewName.text = uploadCurrent.name
        Picasso.get().load(uploadCurrent.imgUrl)
            .fit()
            .centerCrop()
            .into(holder.imgViewUp)
    }

    override fun getItemCount(): Int {
        return mUploads.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewName: TextView = itemView.findViewById(R.id.txtTreatment)
        val imgViewUp: ImageView = itemView.findViewById(R.id.imgViewUpload)
    }
}