package es.upm.tfg.matemathings.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.upm.tfg.matemathings.R
import es.upm.tfg.matemathings.databinding.FragmentSectionsSlidePageBinding
import es.upm.tfg.matemathings.adapters.SectionsSlidePagerAdapter
import es.upm.tfg.matemathings.viewmodels.ConceptsViewModel
import es.upm.tfg.matemathings.viewmodels.VideoViewModel


class SectionsSlidePageFragment : Fragment() {
	private var _binding : FragmentSectionsSlidePageBinding? = null
	private val binding get() = _binding!!
	private lateinit var pagerAdapter: SectionsSlidePagerAdapter
	private lateinit var sectionTabLayout : TabLayout
	private lateinit var viewPager : ViewPager2
	private lateinit var natureIcon : Drawable
	private lateinit var artIcon : Drawable
	private lateinit var archIcon : Drawable

	private val videoSharedViewModel : VideoViewModel by activityViewModels ()
	private val conceptsSharedViewModel : ConceptsViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentSectionsSlidePageBinding.inflate(inflater, container, false)
		natureIcon = ResourcesCompat.getDrawable(
			requireActivity().resources,
			R.drawable.nature_icon,
			null)!!
		artIcon = ResourcesCompat.getDrawable(
			requireActivity().resources,
			R.drawable.art_icon,
			null)!!
		archIcon = ResourcesCompat.getDrawable(
			requireActivity().resources,
			R.drawable.arch_icon,
			null)!!
		conceptsSharedViewModel.getConcepts()
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		pagerAdapter = SectionsSlidePagerAdapter(this)

		sectionTabLayout = binding.sectionsTabLayout
		viewPager = binding.sectionViewPager

		binding.apply {
			viewPager.adapter = pagerAdapter
			TabLayoutMediator(sectionsTabLayout, viewPager) { tab, position ->
				tab.text = getText(
					when(position) {
						0 -> R.string.nature_section_title
						1 -> R.string.art_section_title
						else -> R.string.architecture_section_title
					}
				)
				tab.icon = (when(position) {
							0 -> natureIcon
							1 -> artIcon
							else -> archIcon
						})
			}.attach()
		}
	}
}