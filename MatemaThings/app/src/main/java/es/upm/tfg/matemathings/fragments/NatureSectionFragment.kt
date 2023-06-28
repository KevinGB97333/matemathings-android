package es.upm.tfg.matemathings.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import es.upm.tfg.matemathings.adapters.ConceptListAdapter
import es.upm.tfg.matemathings.adapters.VideoListAdapter
import es.upm.tfg.matemathings.databinding.FragmentNatureSectionBinding
import es.upm.tfg.matemathings.viewmodels.ConceptsViewModel
import es.upm.tfg.matemathings.viewmodels.VideoViewModel
import es.upm.tfg.matemathings.viewmodels.videosGetApiStatus


class NatureSectionFragment : Fragment() {
	private var _binding : FragmentNatureSectionBinding? = null
	private val binding get() = _binding!!

	//VideoList test
	//private val videoSharedViewModel : VideoViewModel by activityViewModels ()
	private val conceptsSharedViewModel : ConceptsViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentNatureSectionBinding.inflate(inflater,container,false)
		//VideoList test
		//binding.natureList.adapter = VideoListAdapter(viewLifecycleOwner.lifecycle)
		binding.natureList.adapter = ConceptListAdapter(requireContext(), findNavController(), fromDetail = false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.apply {
			conceptsViewModel = conceptsSharedViewModel

			conceptsSharedViewModel.natureConcepts.observe(viewLifecycleOwner) {
				if (it.isNullOrEmpty()) {
					natureList.visibility = View.GONE
					loadingLayout.visibility = View.VISIBLE
				} else {
					natureList.visibility = View.VISIBLE
					loadingLayout.visibility = View.GONE
					(natureList.adapter as ConceptListAdapter).submitList(it)
			}
			}
			/* VideoList test
			videoViewModel = videoSharedViewModel
			videoSharedViewModel.videoIds.observe(viewLifecycleOwner) {
				if (it.isNullOrEmpty()) {
					natureList.visibility = View.GONE
					loadingLayout.visibility = View.VISIBLE
				} else {
					natureList.visibility = View.VISIBLE
					loadingLayout.visibility = View.GONE
					(natureList.adapter as VideoListAdapter).submitList(it)
				}
			}*/
		}
	}
}