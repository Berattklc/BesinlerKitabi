package com.beratklc.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.beratklc.besinlerkitabi.R
import com.beratklc.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.beratklc.besinlerkitabi.util.gorselIndir
import com.beratklc.besinlerkitabi.util.placeHolderYap
import com.beratklc.besinlerkitabi.viewModel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {

    private lateinit var  viewModel : BesinDetayiViewModel
    private lateinit var dataBinding : FragmentBesinDetayiBinding
    private var besinId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Databinding initalize ettik
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detayi,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId

        }

        viewModel = ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)


        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer {besin ->

            besin?.let {

                dataBinding.secilenBesin = it


                /*
                    besinIsim.text = it.besinIsim
                besinKalori.text = it.besinKalori
                besinKarbonhidrat.text = it.besinKarbonhidrat
                besinProtein.text = it.besinProtein
                besinYag.text = it.besinYag
                context?.let {
                    besinImage.gorselIndir(besin.besinGorsel, placeHolderYap(it))
                }
                 */
                


            }

        })
    }


}