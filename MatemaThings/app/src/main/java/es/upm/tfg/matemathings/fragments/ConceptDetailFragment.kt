package es.upm.tfg.matemathings.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.upm.tfg.matemathings.R
import es.upm.tfg.matemathings.adapters.ConceptListAdapter
import es.upm.tfg.matemathings.adapters.VideoListAdapter
import es.upm.tfg.matemathings.databinding.FragmentConceptDetailBinding
import es.upm.tfg.matemathings.viewmodels.ConceptsViewModel
import es.upm.tfg.matemathings.viewmodels.VideoViewModel

class ConceptDetailFragment : Fragment() {

	private var _binding : FragmentConceptDetailBinding? = null
	private val binding get() = _binding!!

	private val conceptsSharedViewModel : ConceptsViewModel by activityViewModels()
	private val videoSharedViewModel : VideoViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentConceptDetailBinding.inflate(inflater, container, false)
		binding.videosList.adapter = VideoListAdapter(videoSharedViewModel)
		binding.suggestedConceptsList.adapter = ConceptListAdapter(requireContext(), findNavController(), fromDetail = true)
		binding.suggestedConceptsList.layoutManager = object : LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false) {
			override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
				lp.width = resources.displayMetrics.widthPixels - (resources.displayMetrics.widthPixels/100 *10)
				return true
			}
		}

		val conceptId = requireArguments().getInt("id", -1)
		val category = requireArguments().getString("category", "")
		videoSharedViewModel.reset()
		videoSharedViewModel.getVideos(conceptId)
		conceptsSharedViewModel.generateSuggestedList(conceptId, category)
		//Revisar
		binding.scrollViewDetail.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
			if(scrollX != oldScrollX) {
				binding.videosList.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
					override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
						rv.requestDisallowInterceptTouchEvent(true)
						if(e.action == MotionEvent.ACTION_UP) {
							rv.requestDisallowInterceptTouchEvent(false)
						}
						return true
					}
				})
			}
		}
		//
		return  binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.apply {
			conceptsViewModel = conceptsSharedViewModel
			videoViewModel = videoSharedViewModel
			videoSharedViewModel.videoIds.observe(viewLifecycleOwner) {
				if (it.isNullOrEmpty()) {
					videosList.visibility = View.GONE
				} else {
					videosList.visibility = View.VISIBLE
					(videosList.adapter as VideoListAdapter).submitList(it.subList(0,1))
				}
			}
			conceptsSharedViewModel.conceptDetail.observe(viewLifecycleOwner) {
				if (it == null) {
					scrollViewDetail.visibility = View.GONE
					loadingLayout.visibility = View.VISIBLE
				} else {
					scrollViewDetail.visibility = View.VISIBLE
					loadingLayout.visibility = View.GONE
				}
			}
			seeText.setOnClickListener{
				val adapter = videosList.adapter as VideoListAdapter
				when (adapter.itemCount) {
					1 -> {
						seeText.text = getString(R.string.see_less)
						seeText.alpha = 0.7F
						adapter.submitList(videoSharedViewModel.videoIds.value)
						adapter.notifyDataSetChanged()
					}
					else -> {
						seeText.text = getString(R.string.see_more)
						adapter.submitList(videoSharedViewModel.videoIds.value?.subList(0,1))
						seeText.alpha = 1.0F
						adapter.notifyDataSetChanged()
					}
				}
			}

			conceptsSharedViewModel.suggestedConcepts.observe(viewLifecycleOwner) {
					suggestedConceptsList.isVisible = !it.isNullOrEmpty()
					suggestedConceptsTitle.isVisible = !it.isNullOrEmpty()
			}
		}
	}

}