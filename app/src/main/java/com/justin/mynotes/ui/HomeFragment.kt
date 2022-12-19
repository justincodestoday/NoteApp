package com.justin.mynotes.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.justin.mynotes.MyApplication
import com.justin.mynotes.R
import com.justin.mynotes.adapters.NoteAdapter
import com.justin.mynotes.databinding.FragmentHomeBinding
import com.justin.mynotes.viewModels.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NoteAdapter
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Provider((requireContext().applicationContext as MyApplication).noteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setupRecyclerView()

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            binding.llEmpty.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
            adapter.setNotes(notes)
        }
        binding.fabAdd.setOnClickListener {
            val addNote = HomeFragmentDirections.actionHomeToAddNote()
            NavHostFragment.findNavController(this).navigate(addNote)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(this.activity, 2)
        adapter = NoteAdapter(emptyList(),
            {
                val viewDetails = HomeFragmentDirections.actionHomeToDetails(it.id!!)
                NavHostFragment.findNavController(this).navigate(viewDetails)
                Log.d("Init details", "${it.id}")
            }, {
                val alert = AlertDialog.Builder(requireContext())
                alert.setIcon(R.drawable.ic_baseline_delete_24)
                alert.setTitle("Are you sure you want to delete this note?")
                alert.setPositiveButton("Confirm") { _, _ ->
                    val bundle = Bundle()
                    bundle.putBoolean("refresh", true)
                    setFragmentResult("from_delete_note", bundle)
                    viewModel.deleteNote(it.id!!)
                    val action = HomeFragmentDirections.toHome()
                    NavHostFragment.findNavController(requireParentFragment()).navigate(action)
                    Log.d("Deleted note", "${it.id}")
                }
                alert.setNegativeButton("Cancel") { _, _ ->
                }
                alert.create().show()
            })
        binding.rvNotes.adapter = adapter
        binding.rvNotes.layoutManager = layoutManager
    }

    private fun setupRecyclerView() {
        setFragmentResultListener("from_add_note") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.getNotes()
            }
        }

        setFragmentResultListener("from_edit_note") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.getNotes()
            }
        }

        setFragmentResultListener("from_delete_note") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.getNotes()
            }
        }
    }
}