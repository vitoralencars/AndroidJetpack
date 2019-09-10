package com.vitor.cursojetpack.view


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.vitor.cursojetpack.R
import com.vitor.cursojetpack.adapter.DogsAdapter
import com.vitor.cursojetpack.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val dogsAdapter = DogsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        rv_list.apply {
            rv_list.adapter = dogsAdapter
        }

        srl_list.setOnRefreshListener {
            viewModel.refreshBypassCache()
            srl_list.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.dogs.observe(this, Observer {dogs ->
            dogs?.let{
                rv_list.visibility = View.VISIBLE
                dogsAdapter.updateList(dogs)
            }
        })

        viewModel.dogsLoadError.observe(this, Observer { isError ->
            isError.let{
                tv_error_message.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading.let {
                pb_loading.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    rv_list.visibility = View.GONE
                    tv_error_message.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionSettings -> {
                view?.let{
                    Navigation.findNavController(it).navigate(ListFragmentDirections.actionSettings())
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
