package com.socket.recruitmenttest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.socket.recruitmenttest.R
import com.socket.recruitmenttest.databinding.MainPageBinding
import com.socket.recruitmenttest.util.utils.NetworkStatusHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainPage : Fragment() {
    private var _binding: MainPageBinding? = null
    private val binding get() = _binding!!
    private val adapter = SocketAdapter()
    private val viewModel: SocketViewModel by viewModels()
    private lateinit var networkStatusHelper: NetworkStatusHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData(requireContext())
        networkStatusHelper = NetworkStatusHelper(requireContext())
        initRecyclerView()
        observe()
    }
    private fun observe() {
        viewModel.allData.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter.socketList = list
            }
        }
        viewModel.socketIsConnected.observe(viewLifecycleOwner) { boolean ->
            boolean?.let {
                if (it) {
                    binding.connectionIndicator.text = "Connected"
                    binding.indicatorImage.setBackgroundResource(R.drawable.indicator_connected)
                } else {
                    binding.connectionIndicator.text = "Not connected"
                    binding.indicatorImage.setBackgroundResource(R.drawable.indicator_not_connected)
                }
            }
        }
        networkStatusHelper.observe(viewLifecycleOwner) { boolean ->
            boolean?.let {
                    println("111")
                    viewModel.getData(requireContext())
                if (it) {
                    binding.connectionIndicator.text = "Connected"
                    binding.indicatorImage.setBackgroundResource(R.drawable.indicator_connected)
                } else {
                    binding.connectionIndicator.text = "Not connected"
                    binding.indicatorImage.setBackgroundResource(R.drawable.indicator_not_connected)
                }
            }
        }
    }
    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}