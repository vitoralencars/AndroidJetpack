package com.vitor.cursojetpack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.vitor.cursojetpack.R
import com.vitor.cursojetpack.databinding.ItemDogBinding
import com.vitor.cursojetpack.model.Dog
import com.vitor.cursojetpack.util.getProgressDrawable
import com.vitor.cursojetpack.util.loadImage
import com.vitor.cursojetpack.view.ListFragmentDirections
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsAdapter(): RecyclerView.Adapter<DogsAdapter.ViewHolder>() {

    private val dogs = ArrayList<Dog>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dogs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(dogs[position])

    fun updateList(updatedList: List<Dog>){
        dogs.clear()
        dogs.addAll(updatedList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var view: ItemDogBinding): RecyclerView.ViewHolder(view.root), View.OnClickListener{

        fun bind(dog: Dog){
            view.dog = dog
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val action = ListFragmentDirections.actionDetailFragment()
            action.dogUuid = dogs[adapterPosition].uuid
            Navigation.findNavController(view!!).navigate(action)
        }

    }
}