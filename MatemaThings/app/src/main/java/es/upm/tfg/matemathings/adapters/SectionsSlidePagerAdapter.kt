package es.upm.tfg.matemathings.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.upm.tfg.matemathings.fragments.ArchitectureSectionFragment
import es.upm.tfg.matemathings.fragments.ArtSectionFragment
import es.upm.tfg.matemathings.fragments.NatureSectionFragment

private const val NUM_PAGES = 3

class SectionsSlidePagerAdapter(fm : Fragment) : FragmentStateAdapter(fm) {
	override fun getItemCount(): Int  =  NUM_PAGES

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> NatureSectionFragment()
			1 -> ArtSectionFragment()
			else -> ArchitectureSectionFragment()
		}
	}
}