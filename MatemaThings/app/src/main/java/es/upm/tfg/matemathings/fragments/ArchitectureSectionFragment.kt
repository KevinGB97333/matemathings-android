package es.upm.tfg.matemathings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import es.upm.tfg.matemathings.R
import es.upm.tfg.matemathings.adapters.ConceptListAdapter
import es.upm.tfg.matemathings.databinding.FragmentArchitectureSectionBinding
import es.upm.tfg.matemathings.databinding.FragmentArtSectionBinding
import es.upm.tfg.matemathings.viewmodels.ConceptsViewModel


class ArchitectureSectionFragment : Fragment() {
	private var _binding : FragmentArchitectureSectionBinding? = null
	private val binding get() = _binding!!

	private val conceptsSharedViewModel : ConceptsViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentArchitectureSectionBinding.inflate(inflater,container , false)
		binding.archList.adapter = ConceptListAdapter(requireContext(), findNavController(), fromDetail = false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.apply {
			conceptsViewModel = conceptsSharedViewModel

			conceptsSharedViewModel.natureConcepts.observe(viewLifecycleOwner) {
				if (it.isNullOrEmpty()) {
					archList.visibility = View.GONE
					loadingLayout.visibility = View.VISIBLE
				} else {
					archList.visibility = View.VISIBLE
					loadingLayout.visibility = View.GONE
				}
			}
		}
	}
}