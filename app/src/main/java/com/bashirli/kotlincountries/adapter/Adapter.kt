package com.bashirli.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.kotlincountries.databinding.RecyclerBinding
import com.bashirli.kotlincountries.model.Model
import com.bashirli.kotlincountries.util.placeHolderProgressBar
import com.bashirli.kotlincountries.util.setImageUrl
import com.bashirli.kotlincountries.view.FirstFragmentDirections
import kotlinx.android.synthetic.main.recycler.view.*

class Adapter(var arrayList: ArrayList<Model>) : RecyclerView.Adapter<Adapter.AdapterHolder>() {
    class AdapterHolder(var binding:RecyclerBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        var recyclerBinding=RecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdapterHolder(recyclerBinding)
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {

        holder.binding.country=arrayList.get(position)


        holder.itemView.setOnClickListener {
            val action=FirstFragmentDirections.actionFirstFragmentToSecondFragment(arrayList.get(position).id)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun updateData(list:List<Model>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

}