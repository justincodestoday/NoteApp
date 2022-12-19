package com.justin.mynotes.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.justin.mynotes.MyApplication
import com.justin.mynotes.R
import com.justin.mynotes.databinding.FragmentDetailsBinding
import com.justin.mynotes.viewModels.DetailsViewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Provider((requireContext().applicationContext as MyApplication).noteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navArgs: DetailsFragmentArgs by navArgs()
        viewModel.getNoteById(navArgs.id)
        viewModel.note.observe(viewLifecycleOwner) {
            binding.apply {
                tvHeader.text = it.title
                tvDetails.text = it.details
                cvDetails.setCardBackgroundColor(Color.parseColor(it.color))
            }
        }

        binding.apply {
            btnEdit.setOnClickListener {
                val editNote = DetailsFragmentDirections.actionDetailsToEditNote(navArgs.id)
                NavHostFragment.findNavController(requireParentFragment()).navigate(editNote)
            }
            btnDelete.setOnClickListener {
                val alert = AlertDialog.Builder(requireContext())
                alert.setIcon(R.drawable.ic_baseline_delete_24)
                alert.setTitle("Are you sure you want to delete this note?")
                alert.setPositiveButton("Confirm") { _, _ ->
                    val bundle = Bundle()
                    bundle.putBoolean("refresh", true)
                    setFragmentResult("from_delete_note", bundle)
                    viewModel.deleteNote(navArgs.id)
                    Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
                    NavHostFragment.findNavController(requireParentFragment()).popBackStack()
                    Log.d("Deleted note", "${navArgs.id}")
                }
                alert.setNegativeButton("Cancel") { _, _ ->
                }
                alert.create().show()
            }
        }
    }
}