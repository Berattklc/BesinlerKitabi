package com.beratklc.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.beratklc.besinlerkitabi.R
import com.beratklc.besinlerkitabi.databinding.BesinRecyclerRowBinding
import com.beratklc.besinlerkitabi.model.Besin
import com.beratklc.besinlerkitabi.util.gorselIndir
import com.beratklc.besinlerkitabi.util.placeHolderYap
import com.beratklc.besinlerkitabi.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*

class BesinRecyclerAdapter(val besinListesi: ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>(),BesinClickListener {
    class BesinViewHolder(var view: BesinRecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        //Row u bağlıcaz
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.besin_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflater,R.layout.besin_recycler_row,parent,false)
        return  BesinViewHolder(view)

    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {

        holder.view.besin = besinListesi[position]
        holder.view.listener = this


        /*
               holder.itemView.isim.text = besinListesi[position].besinIsim
        holder.itemView.kalori.text = besinListesi[position].besinKalori
        //Görsel kısım eklenecek

        holder.itemView.setOnClickListener{
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)

        }

        holder.itemView.imageView.gorselIndir(besinListesi[position].besinGorsel, placeHolderYap(holder.itemView.context))

         */


    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiniGuncelle(yeniBesinListesi: List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {
        val uuid = view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }

}