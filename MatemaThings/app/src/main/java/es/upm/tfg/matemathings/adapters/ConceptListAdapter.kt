package es.upm.tfg.matemathings.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.upm.tfg.matemathings.R
import es.upm.tfg.matemathings.databinding.ListItemBinding
import es.upm.tfg.matemathings.models.concept.Concept

class ConceptListAdapter(private val context : Context, private val navController: NavController, private val fromDetail : Boolean) : ListAdapter<Concept, ConceptListAdapter.ConceptViewHolder>(DiffCallback) {

	companion object DiffCallback : DiffUtil.ItemCallback<Concept>() {
		override fun areItemsTheSame(oldItem: Concept, newItem: Concept): Boolean {
			return oldItem.concept.id == newItem.concept.id
		}

		override fun areContentsTheSame(oldItem: Concept, newItem: Concept): Boolean {
			return oldItem.concept.id == newItem.concept.id
		}
	}

	class ConceptViewHolder(
		private var binding : ListItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(concept : Concept) {
			binding.concept = concept.concept
			binding.executePendingBindings()
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConceptViewHolder {
		return ConceptViewHolder(
			ListItemBinding.inflate(LayoutInflater.from(parent.context))
		)
	}

	override fun onBindViewHolder(holder: ConceptViewHolder, position: Int) {
		val concept = getItem(position)
		holder.bind(concept)
		val  seeMoreButton = holder.itemView.findViewById<Button>(R.id.learn_more_btn)
		val locationButton = holder.itemView.findViewById<Button>(R.id.location_btn)

		seeMoreButton.setOnClickListener {
			val bundle = bundleOf()
			bundle.putInt("id", concept.concept.id)
			bundle.putString("category", concept.concept.category)
			if (fromDetail) {
				navController.navigate(R.id.action_conceptDetailFragment_self,bundle)
			} else {
				navController.navigate(R.id.action_sectionsSlidePageFragment_to_conceptDetailFragment,bundle)
			}
		}
		if (concept.concept.location.isNullOrBlank()) {
			locationButton.visibility = View.GONE
		} else {
			locationButton.visibility = View.VISIBLE
		}
	}
}